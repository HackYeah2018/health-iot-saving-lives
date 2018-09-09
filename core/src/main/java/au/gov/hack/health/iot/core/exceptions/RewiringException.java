package au.gov.hack.health.iot.core.exceptions;

public class RewiringException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RewiringException(String message, Throwable cause) {
		super(message, cause);
	}

	public RewiringException(String message) {
		super(message);
	}

}
