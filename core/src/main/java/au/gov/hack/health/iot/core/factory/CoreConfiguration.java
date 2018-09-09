package au.gov.hack.health.iot.core.factory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import au.gov.hack.health.iot.core.config.EnvironmentProperties;

@Configuration
@ComponentScan(basePackages = "au.gov.hack.health.iot.core")
@Import(PropertySourcesConfiguration.class)
public class CoreConfiguration {

	private static final Logger logger = Logger.getLogger(CoreConfiguration.class);
	
	@Autowired
	private EnvironmentProperties properties;
	
}
