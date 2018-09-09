package au.gov.hack.health.iot.core.factory;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import au.gov.hack.health.iot.core.config.EnvironmentProperties;
import au.gov.hack.health.iot.core.config.HdfsProperties;
import au.gov.hack.health.iot.core.config.KafkaProperties;
import au.gov.hack.health.iot.core.config.KerberosProperties;
import au.gov.hack.health.iot.core.config.RuntimePropertyFactory;
import au.gov.hack.health.iot.core.config.SerializedPropertyFactory;
import au.gov.hack.health.iot.core.exceptions.InitializationException;

@Configuration
public class PropertySourcesConfiguration {

	private static final Logger logger = Logger.getLogger(PropertySourcesConfiguration.class);

	private EnvironmentProperties properties;

	@Autowired
	private ConfigurableEnvironment env;

	public PropertySourcesConfiguration() {
		logger.info("Finding correct property factory depending on context");
		// Find correct property factory for context.
		RuntimePropertyFactory runtimePropertyFactory = new RuntimePropertyFactory();
		SerializedPropertyFactory serializedPropertyFactory = new SerializedPropertyFactory();
		if (serializedPropertyFactory.isValid()) {
			logger.info("Using serialized properties");
			serializedPropertyFactory.initialize();
			properties = serializedPropertyFactory.getProperties();
		} else if (runtimePropertyFactory.isValid()) {
			logger.info("Using runtime property location");
			runtimePropertyFactory.init();
			properties = runtimePropertyFactory.getProperties();
		} else {
			throw new InitializationException("Unable to find valid property factory");
		}
	}
	
	@PostConstruct
	public void init() {
		CustomPropertySource source = new CustomPropertySource();
		source.setEnvironmentProperties(properties);
		env.getPropertySources().addFirst(source);
	}

	@Bean("kerberos")
	public KerberosProperties kerberos() {
		return properties.getKerberos();
	}

	@Bean("kafka")
	public KafkaProperties kafka() {
		return properties.getKafka();
	}

	@Bean("hdfs")
	public HdfsProperties hdfs() {
		return properties.getHdfs();
	}

	@Bean("environmentProperties")
	public EnvironmentProperties propertyFactory() {
		return properties;
	}

}
