package com.abs.banking.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.service.CounterService;
import com.abs.banking.service.CustomerService;
import com.abs.banking.service.TokenQueueService;
import com.abs.banking.service.TokenService;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Component
public class CounterManagerImpl implements CounterManager {

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;

	@Autowired
	TokenQueueService tokenQueueService;

	@Autowired
	CounterService counterService;

	@Autowired
	CounterAllocator counterAllocator;

	@Override
	public Token getNextTokenFromQueue(Integer counterNumber) {
		return tokenQueueService.pollNextInQueue(counterService.getCounter(counterNumber));
	}

	@Override
	public List<Counter> getAllCounters() {
		return counterService.getAllCounters();
	}

	@Override
	public ResponseEntity<Counter> getCounter(Integer counterNumber) {
		Counter counter = counterService.getCounter(counterNumber);
		if (counter != null)
			return ResponseEntity.status(HttpStatus.OK).body(counter);
		throw new BusinessException(BusinessException.ErrorCode.INVALID_COUNTER_ID);
	}

	@Override
	public void setComments(Integer tokenNumber, String comments) {
		tokenService.comment(tokenNumber, comments);
	}

	@Override
	public ResponseEntity<Integer> updateTokenStatusById(Integer counterNumber, Integer tokenNumber,
			StatusCode newTokenStatus) {
		if (validateCounterForToken(counterNumber, tokenNumber)) {
			resolveToken(tokenNumber, newTokenStatus);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenNumber);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(tokenNumber);
	}

	private Token resolveToken(Integer tokenNumber, StatusCode newTokenStatus) {
		Token token = getToken(tokenNumber);
		token.setStatusCode(newTokenStatus);
		tokenService.save(token);

		if (StatusCode.COMPLETED.equals(newTokenStatus)) {
			return resolveMultiServiceTokenIfExists(token, newTokenStatus);
		}
		return token;
	}

	/**
	 * For current entry of token in token table, mark as complete, and create new entry
	 * with new counter id
	 * 
	 * @param token
	 * @param newTokenStatus
	 * @return Token new token entry created for next service 
	 */
	private Token resolveMultiServiceTokenIfExists(Token token, StatusCode newTokenStatus) {
		token = counterService.assignNextService(token);
		if (token.getCurrentService() != null) {
			Token newToken = token;
			newToken.setStatusCode(StatusCode.IN_PROGRESS);
			newToken.setCurrentCounter(null);
			newToken = tokenQueueService.addToNextQueue(newToken);
			return tokenService.save(newToken);
		}
		return token;
	}

	private boolean validateCounterForToken(Integer counterNumber, Integer tokenNumber) {
		return counterNumber.equals(getToken(tokenNumber).getCounterNumber());
	}

	private Token getToken(Integer tokenNumber) {
		return tokenService.getTokenByNumber(tokenNumber);
	}

}