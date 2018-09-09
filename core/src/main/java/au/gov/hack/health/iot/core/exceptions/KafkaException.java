package au.gov.hack.health.iot.core.exceptions;

public class KafkaException extends Exception {

	private static final long serialVersionUID = 1L;

	public KafkaException(String message, Throwable cause) {
		super(message, cause);
	}

	public KafkaException(String message) {
		super(message);
	}

	
	
}
