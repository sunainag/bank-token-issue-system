package com.abs.banking.exception;

public class ServicesException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicesException() {
		super();
	}

	public ServicesException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ServicesException(final String message) {
		super(message);
	}

	public ServicesException(final Throwable cause) {
		super(cause);
	}

}
