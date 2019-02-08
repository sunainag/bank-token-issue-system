package com.bank.system.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.system.exception.CounterNotAvailableException;
import com.bank.system.exception.InvalidTokenException;

@ControllerAdvice
public class BankOperationsExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<Object> handleInvalidTokenId(InvalidTokenException ex, WebRequest request) {
		String bodyOfResponse = "Please pass a valid token id";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(CounterNotAvailableException.class)
	protected ResponseEntity<Object> handleAvailCounters(CounterNotAvailableException ex, WebRequest request) {
		String bodyOfResponse = "Counters are busy. Please wait in the queue";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
