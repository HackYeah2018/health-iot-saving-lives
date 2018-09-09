package au.gov.hack.health.iot.core.config;

public class HdfsProperties {

	private String basePath;

	private String defaultFs;
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void validate() {
		
	}

	public String getDefaultFs() {
		return defaultFs;
	}

	public void setDefaultFs(String defaultFs) {
		this.defaultFs = defaultFs;
	}


	
	
	
}
