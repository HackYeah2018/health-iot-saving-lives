package au.gov.hack.health.iot.core.config;

import org.apache.log4j.Logger;

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
public class SerializedPropertyFactory extends BasePropertyFactory {

	public static final String SERIALIZED_RUNTIME_CONFIG = "serialized.runtime.config";
	
	private static final Logger logger = Logger.getLogger(SerializedPropertyFactory.class);

	private EnvironmentProperties properties;

	private String serialized;

	@Override
	public boolean isValid() {
		return (System.getProperties().containsKey(SERIALIZED_RUNTIME_CONFIG));
	}
	
	/**
	 * Initialize the Environment properties.
	 */
	public void initialize() {
		String serializedRuntimeProperties = System.getProperty(SERIALIZED_RUNTIME_CONFIG);
		logger.info("Loading previously serialized runtime properties: " + serializedRuntimeProperties);
		serialized = serializedRuntimeProperties;

		EnvironmentProperties newProps = convertRuntimeProperties(serialized.getBytes());

		newProps.validate();
		properties = newProps;

		logger.info("Completed initialization of the runtime property factory");
	}

	/**
	 * Return the current runtime properties.
	 * 
	 * @return
	 */
	@Override
	public EnvironmentProperties getProperties() {  
		return properties;
	}

	public String getSerialized() {
		return this.serialized;
	}

}
