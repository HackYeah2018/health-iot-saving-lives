package au.gov.hack.health.iot.web;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import au.gov.hack.health.iot.core.factory.CoreConfiguration;
import au.gov.hack.health.iot.core.factory.PropertySourcesConfiguration;
import au.gov.hack.health.iot.stub.StubConfig;

/**
 * @author davidlevy
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger logger = Logger.getLogger(WebAppInitializer.class);
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		String stub = System.getProperty("web.stub");
		logger.info("Value of system property web.stub is [" + stub + "]");
		if (Boolean.valueOf(stub) == true) {
			logger.info("Returning stub bean configuration");
			return new Class[] { StubConfig.class };
		} else {
			logger.info("Returning real bean configuration");
			return new Class[] { PropertySourcesConfiguration.class, CoreConfiguration.class };
		}
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
