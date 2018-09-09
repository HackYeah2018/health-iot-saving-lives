package au.gov.hack.health.iot.core.exceptions;

public class DuplicateException extends PersistenceException {

	public DuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateException(String message) {
		super(message);
	}

}
