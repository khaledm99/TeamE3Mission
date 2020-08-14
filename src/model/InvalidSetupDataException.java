package model;

public class InvalidSetupDataException extends Exception{

	public InvalidSetupDataException() {
	}

	public InvalidSetupDataException(String message) {
		super(message);
	}

	public InvalidSetupDataException(Throwable cause) {
		super(cause);
	}

	public InvalidSetupDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSetupDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	
}
