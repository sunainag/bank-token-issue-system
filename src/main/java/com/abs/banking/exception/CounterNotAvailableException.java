package com.abs.banking.exception;

public class CounterNotAvailableException extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public CounterNotAvailableException() {
		super();
	}

	public CounterNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public CounterNotAvailableException(String message) {
		super(message);
	}

	public CounterNotAvailableException(Throwable cause) {
		super(cause);
	}
}
