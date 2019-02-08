package com.bank.system.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.system.exception.CounterNotAvailableException;
import com.bank.system.model.Counter;
import com.bank.system.model.Token;
import com.bank.system.service.CounterService;
import com.bank.system.service.CustomerService;
import com.bank.system.service.TokenService;

@Component
public class TokenManagerImpl implements TokenManager {
	
	private List counterList = new ArrayList();

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;
	
	@Autowired
	CounterService counterService;

	@Override
	public void createToken(Token token) {
		if (token != null) {
			// checkIfCustomerExists
			if (!customerService.ifExists(token.getCustomer())) {
				customerService.createCustomer(token.getCustomer()); // TODO: what to do with the new
			}

			// issue token
			Token issuedToken = tokenService.generateToken(token);

			// assign counter
			assignCounter(issuedToken);
		}
	}

	private void assignCounter(Token issuedToken) {
		//TODO: synchronize this
		List<Counter> counters = counterService.getAvailableCounters();
		if(counters.isEmpty())
			throw new CounterNotAvailableException();
		
		Counter minQueueCounter = counters.get(0);
		//check counter with minimum length and then check service
		counters.forEach(counter->{
			
		});
		if(firstCounter.getListOfServices().contains(issuedToken.getTokenType())){
			
		}else {
			
		}
	}

	@Override
	public Token getToken(Long tokenId) {
		return tokenService.getTokenById(tokenId);
	}

}
