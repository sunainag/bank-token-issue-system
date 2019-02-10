package com.bank.system.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.system.exception.CounterNotAvailableException;
import com.bank.system.exception.ErrorResponse;
import com.bank.system.exception.InvalidTokenException;

@ControllerAdvice
public class BankOperationsExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String COUNTERS_UNAVAILABLE = "Counters are busy. Please wait in the queue";
	private static final String INVALID_TOKEN = "Please pass a valid token id";

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<Object> handleInvalidTokenId(InvalidTokenException ex, WebRequest request) {
		return handleExceptionInternal(ex, INVALID_TOKEN, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = CounterNotAvailableException.class)
	public @ResponseBody @ResponseStatus(HttpStatus.BAD_REQUEST) ErrorResponse handleAvailCounters(
			CounterNotAvailableException e) {
		logger.error(COUNTERS_UNAVAILABLE, e);
		return new ErrorResponse(COUNTERS_UNAVAILABLE);
	}

}
