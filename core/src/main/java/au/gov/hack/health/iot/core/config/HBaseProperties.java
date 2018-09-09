package au.gov.hack.health.iot.core.config;

public class HBaseProperties {

	private String znodeParent = "hbase";
	
	private String namespace = "cdcm";

	public String getZnodeParent() {
		return znodeParent;
	}

	public void setZnodeParent(String znodeParent) {
		this.znodeParent = znodeParent;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void validate() {
		// TODO Auto-generated method stub
		
	}
	
}
