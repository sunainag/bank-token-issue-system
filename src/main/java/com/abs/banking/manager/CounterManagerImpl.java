package com.abs.banking.manager;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abs.banking.exception.InvalidCounterException;
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
	public ResponseEntity<?> getNextTokenFromQueue(Integer counterNumber) {
		Token token = tokenQueueService.pollNextInQueue(counterService.getCounter(counterNumber));
		return ResponseEntity.ok("Next token from queue:" + token.getNumber() + " is assigned to this counter:" + counterNumber);
	}

	@Override
	public Set<String> getAllCounters() {
		Set<Counter> counters= counterService.getAllCounters();
		Set<String> counterDetails = new HashSet<>();
		counters.forEach(counter->{
			counterDetails.add(counter.toString());
		});
		return counterDetails;
	}

	@Override
	public ResponseEntity<String> getCounter(Integer counterNumber) {
		Counter counter = counterService.getCounter(counterNumber);
		if (counter != null)
			return ResponseEntity.ok(counter.toString());
		throw new InvalidCounterException();
	}

	@Override
	public void setComments(Integer tokenNumber, String comments) {
		tokenService.comment(tokenNumber, comments);
	}

	@Override
	@Transactional
	public ResponseEntity<String> updateTokenStatusById(Integer counterNumber, Integer tokenNumber,
			String newTokenStatus) {
		if (validateCounterForToken(counterNumber, tokenNumber)) {
			resolveToken(tokenNumber, newTokenStatus);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(tokenNumber + " is udpated with status " + newTokenStatus);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(tokenNumber + " could not be " + newTokenStatus);
	}

	private Token resolveToken(Integer tokenNumber, String newTokenStatus) {
		Token token = getToken(tokenNumber);
		token.setStatusCode(StatusCode.valueOf(newTokenStatus));
		tokenService.save(token);

		if (StatusCode.COMPLETED.equals(token.getStatusCode())) {
			return resolveMultiServiceTokenIfExists(token);
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
	private Token resolveMultiServiceTokenIfExists(Token token) {
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
