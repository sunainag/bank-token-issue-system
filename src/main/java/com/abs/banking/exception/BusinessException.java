package com.abs.banking.exception;

public class BusinessException extends RuntimeException {

	public enum ErrorCode {
		CUSTOMER_NOT_FOUND(1001, "Customer does not exist"), DUPLICATE_CUSTOMER(1002,
				"Customer already exists"), SERVICE_NOT_FOUND(2001, "Service not supported"), INVALID_TOKEN(3001,
						"Token is invalid"), INVALID_TOKEN_STATE(3002,
								"Token not active"), COUNTERS_NOT_INITIALIZED_CORRECTLY(3003,
										"Premium or Regular Service counters are not initialized correctly. This is a technical issue."), INVALID_COUNTER_ID(
												3004,
												"Please pass a valid counter id"), TOKEN_NOT_ASSIGNED_TO_THIS_COUNTER(
														3005, "No token assigned to this counter");

		private int code;
		private String message;

		ErrorCode(int code, String message) {
			this.code = code;
			this.message = message;
		}
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.message);
	}
}
