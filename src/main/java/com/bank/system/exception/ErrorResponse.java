package com.bank.system.exception;

public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		// super();
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
