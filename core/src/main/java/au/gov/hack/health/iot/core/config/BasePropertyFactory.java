package au.gov.hack.health.iot.core.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public abstract class BasePropertyFactory implements PropertyFactory {

	private static final Logger logger = Logger.getLogger(BasePropertyFactory.class);
	
	protected EnvironmentProperties convertRuntimeProperties(byte[] runtimeProps) {
		logger.info("Converting runtime properties into domain object");
		Gson gson = new Gson();
		EnvironmentProperties newProps = null;
		try (ByteArrayInputStream inTemp = new ByteArrayInputStream(runtimeProps);
				InputStreamReader reader = new InputStreamReader(inTemp, "UTF8")) {
			newProps = gson.fromJson(reader, EnvironmentProperties.class);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read runtime properties into expected structure: " + e.getMessage(),
					e);
		}
		logger.info("Completed conversion of runtime properties into domain object");
		return newProps;
	}
	
}
