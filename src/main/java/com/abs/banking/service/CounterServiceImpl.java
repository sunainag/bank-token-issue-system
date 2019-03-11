package com.abs.banking.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abs.banking.exception.InvalidTokenException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Services;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.model.TokenServiceMapping;
import com.abs.banking.repository.CounterRepository;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;

	@Autowired
	CounterAllocator counterAllocator;

	@Autowired
	TokenService tokenService;

	@Autowired
	TokenQueueService tokenQueueService;

	@Override
	public Set<Counter> getAllCounters() {
		Set<Counter> counters = new HashSet<>();
		counterRepo.findAll().forEach(counters::add);
		return counters;
	}

	@Override
	public Counter getCounter(Integer counterNum) {
		return counterRepo.findByNumber(counterNum);
	}

	@Transactional
	public Token resolveToken(Integer tokenNumber, String newTokenStatus) {
		Token token = tokenService.getTokenByNumber(tokenNumber);
			if (isValid(token)) {
				token.setStatusCode(StatusCode.valueOf(newTokenStatus));
				tokenService.save(token);

				if (StatusCode.COMPLETED.equals(token.getStatusCode())) {
					return resolveMultiServiceTokenIfExists(token);
				}
			}
			return token;
	}

	/**
	 * For current entry of token in token table, mark as complete, and create new
	 * entry of token with new counter id
	 * 
	 * @param token
	 * @param newTokenStatus
	 * @return Token new token entry created for next service
	 */
	@Transactional
	private Token resolveMultiServiceTokenIfExists(Token token) {
		Token newToken = new Token(token);
		Services nextService = getNextService(token);
		if (nextService != null) {
			token.setCurrentService(null);
			newToken.setStatusCode(StatusCode.IN_PROGRESS);
			newToken.setCurrentService(nextService);
			newToken.setCurrentCounter(null);
			return tokenQueueService.addToNextQueue(tokenService.save(newToken));
		}
		return token;
	}

	private Services getNextService(Token token) {
		TokenServiceMapping nextService = null;

		Iterator<TokenServiceMapping> i = token.getTokenServices().iterator();
		while (i.hasNext()) {
			TokenServiceMapping tsm = i.next();
			if (token.getCurrentService() != null && tsm.getService().getId() == token.getCurrentService().getId()
					&& i.hasNext()) {
				nextService = i.next();
				return nextService.getService();
			} else {
				return null;
			}
		}
		return null;
	}

	private boolean isValid(Token token) {
		if (token == null || token.isInactive()) {
			throw new InvalidTokenException();
		}
		return true;
	}

}
