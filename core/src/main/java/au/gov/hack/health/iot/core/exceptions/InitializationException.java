package au.gov.hack.health.iot.core.exceptions;

public class InitializationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message) {
		super(message);
	}
	
	
}
