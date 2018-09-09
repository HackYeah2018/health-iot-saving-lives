package au.gov.hack.health.iot.core.factory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.TypeUtils;

import au.gov.hack.health.iot.core.exceptions.InitializationException;
import au.gov.hack.health.iot.core.exceptions.RewiringException;


/**
 * A bean factory compatible with the Spark serialization/deserialization flow.
 * 
 * Note that this bean factory doesn't actually serialize but will be recreated
 * in driver and executors exactly the same - the advantage here is that any
 * code in here can change and it won't affect a checkpoint.
 * 
 * @author davidlevy
 *
 */
// TODO Switch to Spring to create the bean factory and inject the dependencies.
public class BeanFactory implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(BeanFactory.class);

	private static BeanFactory INSTANCE;

	private AbstractApplicationContext ctx;

	private Map<Type, Object> typeBeanCacheMap = new HashMap<>();

	protected BeanFactory() {

	}

	public synchronized static final boolean checkInitialized() {
		return INSTANCE != null;
	}

	public synchronized static final BeanFactory get() {
		if (INSTANCE == null) {
			logger.info("#######\n\nInitializing Bean Factory\n\n######");

			INSTANCE = new BeanFactory();
			INSTANCE.init();
			logger.info("#######\n\nInitialized Bean Factory\n\n######");
		}
		return INSTANCE;
	}

	protected void init() {
		try {

			// Ensure our timezone is set early in the application.
			DateTimeZone.setDefault(DateTimeZone.forID("Australia/Sydney"));

			if (logger.isDebugEnabled()) {
				for (Object key : System.getProperties().keySet()) {
					logger.info("Found system property [" + key + "] with value [" + System.getProperty((String) key));
				}
			}

			// AbstractApplicationContext preCtx = new
			// AnnotationConfigApplicationContext(PropertySourcesConfiguration.class);
			// preCtx.start();
			// EnvironmentProperties properties =
			// preCtx.getBean(EnvironmentProperties.class);

			AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(CoreConfiguration.class);
			// CustomPropertySource propertySource = new
			// CustomPropertySource(properties);
			// ctx.getEnvironment().getPropertySources().addLast(propertySource);
			ctx.start();
			ctx.registerShutdownHook();
			this.ctx = ctx;

		} catch (Exception e) {
			throw new InitializationException("Unable to initialize bean factory: " + e.getMessage(), e);
		}
	}

	public void injectDependencies(RewiringBean rewiringBean) {
		if (logger.isInfoEnabled()) {
			logger.info("Rewiring dependencies for [" + rewiringBean.getClass() + "]");
		}

		try {
			Class<?> current = rewiringBean.getClass();

			while (current != null) {
				if (logger.isInfoEnabled()) {
					logger.info("At class [" + current.getCanonicalName() + "]");
				}

				for (Field f : current.getDeclaredFields()) {
					if (logger.isInfoEnabled()) {
						logger.info("At field [" + f.getName() + "]");
					}

					Rewire r = f.getAnnotation(Rewire.class);
					if (r == null) {
						if (logger.isInfoEnabled()) {
							logger.info("Field [" + f.getName() + "] is missing Rewire annotation so will skip");
						}
						continue;
					}

					if (logger.isInfoEnabled()) {
						logger.info("Rewiring field [" + f.getName() + "]");
					}

					boolean isTransient = Modifier.isTransient(f.getModifiers());
					if (isTransient) {
						if (logger.isInfoEnabled()) {
							logger.info("Field [" + f.getName() + "] is transient");
						}
					} else {
						throw new RewiringException("Field [" + f.getName() + "] is not transient");
					}

					boolean explicit = false;

					// First try assign the name of the bean from the
					// annotation.
					String name = null;
					if (StringUtils.isNotBlank(r.value())) {
						name = r.value();
					} else if (StringUtils.isNotBlank(r.name())) {
						name = r.name();
					}

					// If not explicitly declared, try map via the name of the
					// field.
					if (StringUtils.isNotBlank(name)) {
						explicit = true;
					}

					if (logger.isDebugEnabled()) {
						logger.debug("Rewiring field name to lookup is [" + name + "]");
					}

					Object toAssign = null;
					if (explicit) {
						if (logger.isDebugEnabled()) {
							logger.debug("Using explicit name lookup");
						}
						if (ctx.containsBean(name)) {
							toAssign = ctx.getBean(name);
						} else {
							throw new RewiringException("Unable to rewire dependency for field [" + f.getName()
									+ "] in class [" + rewiringBean.getClass() + "] as no object with given name");
						}
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("Using autowire lookup by name then class");
						}
						name = f.getName();
						if (name.length() == 1) {
							name = name.toLowerCase();
						} else {
							name = name.substring(0, 1).toLowerCase() + name.substring(1);
						}

						if (ctx.containsBean(name)) {
							if (logger.isDebugEnabled()) {
								logger.debug("Rewiring on attribute name match [" + name + "]");
							}
							toAssign = ctx.getBean(name);
						} else {
							if (logger.isDebugEnabled()) {
								logger.debug("No match on name so will try class");
							}
							Type t = f.getType();

							if (logger.isDebugEnabled()) {
								logger.debug("Type is [" + t + "]");
							}
							if (typeBeanCacheMap.containsKey(t)) {
								if (logger.isDebugEnabled()) {
									logger.debug("Using cache entry");
								}
								toAssign = typeBeanCacheMap.get(t);
							} else {
								if (logger.isInfoEnabled()) {
									logger.info("Looking for bean of type [" + t + "] for field [" + f + "] on ["
										+ rewiringBean.getClass() + "]");
								}
								String[] beanNames = ctx.getBeanDefinitionNames();

								for (String beanName : beanNames) {

									Object bean = ctx.getBean(beanName);
									if (logger.isInfoEnabled()) {
										logger.info("Checking bean [" + beanName + "] to rewired [" + t + "]");
									}
									if (checkAssignable(t, bean)) {
										if (logger.isInfoEnabled()) {
											logger.info("Wiring bean [" + beanName + "] to [" + t + "]");
										}
										toAssign = bean;
										if (typeBeanCacheMap.containsKey(t)) {
											throw new RewiringException("Multiple beans declared of type [" + t + "]");
										}
										typeBeanCacheMap.put(t, bean);
										break;
									}
								}
							}

							if (toAssign == null) {
								String message = "Unable to rewire dependency for field [" + f.getName()
										+ "] in class [" + rewiringBean.getClass() + "] as no object with given name";
								logger.error(message);
								throw new RewiringException(message);
							}
						}
					}

					try {
						f.setAccessible(true);
						f.set(rewiringBean, toAssign);
					} catch (Exception e) {
						throw new RewiringException("Unable to rewire dependency for field [" + f.getName()
								+ "] in class [" + rewiringBean.getClass() + "]: " + e.getMessage());
					}

				}

				if (current.equals(RewiringBean.class)) {
					break;
				} else {
					current = current.getSuperclass();
				}

			}

		} catch (Exception e) {
			// Swallow exceptions as they seem to cause the Spark execution flow
			// to create backlog.
			// Still log them.
			logger.error("Unable to re-wired bean: " + e.getMessage(), e);
		}
	}

	protected boolean checkAssignable(Type t, Object bean) {
		Class<?> concrete = (Class<?>) t;
		return concrete.isAssignableFrom(bean.getClass());
	}

	public Object getBeanByName(String name) {
		return ctx.getBean(name);
	}

	public <T> T getBeanByClass(Class<T> clazz) {
		return ctx.getBean(clazz);
	}

}
