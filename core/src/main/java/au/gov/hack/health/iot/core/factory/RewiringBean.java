package au.gov.hack.health.iot.core.factory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;
import org.apache.spark.SerializableWritable;

import com.google.gson.Gson;

import au.gov.hack.health.iot.core.config.EnvironmentProperties;
import au.gov.hack.health.iot.core.config.SerializedPropertyFactory;
import au.gov.hack.health.iot.core.utils.SaslUtil;

/**
 * Any bean which needs to be serialized as part of the DAG with dependencies
 * which need to be rewired.
 * 
 * @author davidlevy
 *
 */
public class RewiringBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(RewiringBean.class);

	private String runtimeConfig;

	private SerializableWritable<Credentials> credentialsAsText;

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();

		setupRuntimeConfig();

		if (logger.isInfoEnabled()) {
			logger.info("Bean [" + this.getClass().getName() + "] is rewiring!!");
		}

		BeanFactory factory = BeanFactory.get();
		factory.injectDependencies(this);

		if (logger.isInfoEnabled()) {
			logger.info("Bean [" + this.getClass().getName() + "] has completed rewiring!!");
		}
	}

	protected void setupRuntimeConfig() {
		if (StringUtils.isBlank(runtimeConfig)) {
			logger.warn("Not setting runtime config as none was serialized with this object");
			return;
		}

		String existingRuntimeProperties = System.getProperty(SerializedPropertyFactory.SERIALIZED_RUNTIME_CONFIG);
		if (StringUtils.isBlank(existingRuntimeProperties)) {
			logger.info("Setting runtime properties from serialized value:\n" + runtimeConfig);
			System.setProperty(SerializedPropertyFactory.SERIALIZED_RUNTIME_CONFIG, runtimeConfig);

			if (this.credentialsAsText != null) {
				SaslUtil.setCredentials(this.credentialsAsText.value());
			} else {
				logger.error("\n\n\n\n\nNo credentials passed!!\n\n\n\n\n");
			}
		} else {
			logger.info("Runtime properties already set");
		}

	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		// Get the runtime config before write.
		EnvironmentProperties props = (EnvironmentProperties) BeanFactory.get().getBeanByName("environmentProperties");

		this.runtimeConfig = new Gson().toJson(props);

		Credentials credentials = UserGroupInformation.getCurrentUser().getCredentials();
		this.credentialsAsText = new SerializableWritable<Credentials>(credentials);
		s.defaultWriteObject();
	}

}
