package au.gov.hack.health.iot.core.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.gov.hack.health.iot.core.exceptions.InitializationException;


public class KafkaProperties {

	private String brokers;
	
	private boolean stubDetResponse;
	
	private String portalTopic;
	
	private String portalResponseTopic;
	
	private String portalErrorTopic;
		
	private Map<String, String> consumerProperties = new HashMap<>();

	private Map<String, String> producerProperties = new HashMap<>();
	
	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getPortalTopic() {
		return portalTopic;
	}

	public void setPortalTopic(String portalTopic) {
		this.portalTopic = portalTopic;
	}

	public String getPortalResponseTopic() {
		return portalResponseTopic;
	}

	public void setPortalResponseTopic(String portalResponseTopic) {
		this.portalResponseTopic = portalResponseTopic;
	}

	public String getPortalErrorTopic() {
		return portalErrorTopic;
	}

	public void setPortalErrorTopic(String portalErrorTopic) {
		this.portalErrorTopic = portalErrorTopic;
	}

	public Map<String, String> getConsumerProperties() {
		return consumerProperties;
	}

	public void setConsumerProperties(Map<String, String> consumerProperties) {
		this.consumerProperties = consumerProperties;
	}

	public Map<String, String> getProducerProperties() {
		return producerProperties;
	}

	public void setProducerProperties(Map<String, String> producerProperties) {
		this.producerProperties = producerProperties;
	}

	
	
	public boolean isStubDetResponse() {
		return stubDetResponse;
	}

	public void setStubDetResponse(boolean stubDetResponse) {
		this.stubDetResponse = stubDetResponse;
	}
	public void validate() {
		
		if (StringUtils.isBlank(portalTopic)) {
			throw new InitializationException("Topic [portalTopic] can not be blank");
		}

		if (StringUtils.isBlank(portalResponseTopic)) {
			throw new InitializationException("Topic [portalResponseTopic] can not be blank");
		}

		if (StringUtils.isBlank(portalErrorTopic)) {
			throw new InitializationException("Topic [portalErrorTopic] can not be blank");
		}
		
	}
	
	
	
}
