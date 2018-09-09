package au.gov.hack.health.iot.core.factory;

import org.apache.log4j.Logger;
import org.springframework.core.env.PropertySource;

import au.gov.hack.health.iot.core.config.EnvironmentProperties;
import ognl.DefaultMemberAccess;
import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlContext;

//@Component("customPropertySource")
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Lazy(false)
public class CustomPropertySource extends PropertySource<Object> {

	private static final Logger logger = Logger.getLogger(CustomPropertySource.class);

	private EnvironmentProperties environmentProperties;

	public CustomPropertySource() {
		super("hicCustomPropertySource");
	}

	@Override
	public Object getProperty(String name) {
		if (name.equals("spring.liveBeansView.mbeanDomain")) {
			logger.warn("Odd spring request, we will return dev here to avoid error. Should be harmless");
			return "dev";
		}

		try {
			OgnlContext context = new OgnlContext();
			context.setMemberAccess(new DefaultMemberAccess(true));

			Object result = Ognl.getValue(name, context, environmentProperties);
			logger.info("For property [" + name + "], found value [" + result + "]");
			return result;
		} catch (NoSuchPropertyException e) {
			logger.info("No such property in our custom properties [" + name + "]");
			return null;
		} catch (Exception e) {
			logger.info("Unable to source property [" + name + "] from environment properties", e);
			return null;
		}
	}

	public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
		this.environmentProperties = environmentProperties;
	}

}
