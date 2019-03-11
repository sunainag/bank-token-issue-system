package com.abs.banking.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abs.banking.exception.CounterNotAvailableException;
import com.abs.banking.exception.InvalidCounterException;
import com.abs.banking.exception.InvalidTokenException;
import com.abs.banking.exception.InvalidTokenQueueException;
import com.abs.banking.exception.ServicesException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String COUNTERS_UNAVAILABLE = "Counters are unavailable due to technial issue";
	private static final String INVALID_TOKEN = "Please pass a valid token. Either the token is not active or the token number is not valid";
	private static final String SERVICE_NOT_FOUND = "Service requested is not supported";
	private static final String INVALID_COUNTER = "Please pass a valid counter id";
	private static final String INVALID_COUNTER_TOKEN_MAP = "This token is not assigned to the respective counter";

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<Object> handleInvalidTokenId(InvalidTokenException ex, WebRequest request) {
		return handleExceptionInternal(ex, INVALID_TOKEN, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = CounterNotAvailableException.class)
	public ResponseEntity<Object> handleAvailCounters(CounterNotAvailableException ex, WebRequest request) {
		return handleExceptionInternal(ex, COUNTERS_UNAVAILABLE, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	@ExceptionHandler(value = ServicesException.class)
	public ResponseEntity<Object> handleService(ServicesException ex, WebRequest request) {
		return handleExceptionInternal(ex, SERVICE_NOT_FOUND, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = InvalidCounterException.class)
	public ResponseEntity<Object> handleCounter(InvalidCounterException ex, WebRequest request) {
		return handleExceptionInternal(ex, INVALID_COUNTER, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = InvalidTokenQueueException.class)
	public ResponseEntity<Object> handleTokenQueue(InvalidTokenQueueException ex, WebRequest request) {
		return handleExceptionInternal(ex, INVALID_COUNTER_TOKEN_MAP, new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

}
