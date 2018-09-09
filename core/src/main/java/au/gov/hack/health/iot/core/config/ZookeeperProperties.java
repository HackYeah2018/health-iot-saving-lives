package au.gov.hack.health.iot.core.config;

public class ZookeeperProperties {

	private String clientPort;
	
	private String quorum;

	public String getQuorum() {
		return quorum;
	}

	public void setQuorum(String quorum) {
		this.quorum = quorum;
	}

	
	
	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public void validate() {
		// TODO Auto-generated method stub
		
	}	

	
	
}
