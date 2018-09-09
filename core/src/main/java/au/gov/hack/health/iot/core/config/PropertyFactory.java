package au.gov.hack.health.iot.core.config;

public interface PropertyFactory {

	public boolean isValid();
	
	public EnvironmentProperties getProperties();
	
}
