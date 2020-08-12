package application;

public class PollFullException extends Exception {
	
	public PollFullException() {
	}

	public PollFullException(String message) {
		super(message);
	}

	public PollFullException(Throwable cause) {
		super(cause);
	}

	public PollFullException(String message, Throwable cause) {
		super(message, cause);
	}

	public PollFullException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}