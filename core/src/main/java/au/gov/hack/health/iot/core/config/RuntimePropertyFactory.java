package au.gov.hack.health.iot.core.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import au.gov.hack.health.iot.core.exceptions.InitializationException;

/**
 * Factory which loads the runtime properties.
 * 
 * This is very useful for creating property configurations which can be read
 * both from the driver as well as the executor. The alternative is to pass all
 * configuration through the spark-submit job which undermines the principals of
 * dependency injection.
 * 
 * @author davidlevy
 *
 */
public class RuntimePropertyFactory extends BasePropertyFactory {

	public static final String RUNTIME_CONFIG_PATH = "runtime.config.path";

	private static final Logger logger = Logger.getLogger(RuntimePropertyFactory.class);

	@Override
	public boolean isValid() {
		String runtimeConfigPath = System.getProperty(RUNTIME_CONFIG_PATH);
		if (StringUtils.isBlank(runtimeConfigPath)) {
			logger.info("System property [" + RUNTIME_CONFIG_PATH +"] is blank");
			return false;
		} else {
			logger.info("Found system property [" + RUNTIME_CONFIG_PATH + "]: " + runtimeConfigPath);
			return true;
		}
	}

	private EnvironmentProperties properties;

	private String serialized;

	/**
	 * Initialize the Environment properties.
	 */
	public void init() {
		logger.info("Initializing the runtime property factory");

		logger.info("No existing serialized runtime properties, this is the first load");

		byte[] runtimeProps = loadRuntimeProperties();

		EnvironmentProperties newProps = convertRuntimeProperties(runtimeProps);

		newProps.validate();
		properties = newProps;
		serialized = new String(runtimeProps);

		logger.info("Completed initialization of the runtime property factory");
	}

	/**
	 * Return the current runtime properties.
	 * 
	 * @return
	 */
	@Override
	public EnvironmentProperties getProperties() {
		// TODO Clone before returning
		return properties;
	}

	protected byte[] loadRuntimeProperties() {
		String runtimeConfigPath = System.getProperty(RUNTIME_CONFIG_PATH);
		
		logger.info("Loading runtime properties from [" + runtimeConfigPath + "]");

		if (StringUtils.isBlank(runtimeConfigPath)) {
			throw new InitializationException("The runtimeConfigPath can not be blank");
		}

		byte[] runtimeProps = null;
		try {
			File runtimeConfig = new File(runtimeConfigPath);
			runtimeProps = FileUtils.readFileToByteArray(runtimeConfig);

			logger.info("Found runtime config:\n" + new String(runtimeProps));
		} catch (IOException e) {
			throw new RuntimeException("Runtime properties [" + runtimeConfigPath + "] are missing: " + e.getMessage(),
					e);
		}
		return runtimeProps;
	}

	public String getSerialized() {
		return this.serialized;
	}

}
