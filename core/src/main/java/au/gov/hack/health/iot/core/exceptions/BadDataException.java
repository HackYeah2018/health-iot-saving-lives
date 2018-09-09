package au.gov.hack.health.iot.core.exceptions;

public class BadDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadDataException(String message) {
		super(message);
	}
	
	

}
