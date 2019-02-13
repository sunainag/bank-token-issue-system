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
import com.abs.banking.service.TokenService;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Component
public class CounterManagerImpl implements CounterManager {

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;

	@Autowired
	CounterService counterService;

	@Autowired
	CounterAllocator counterAllocator;

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
		Token token = getToken(tokenNumber);
		token.getTokenServices().stream().filter(tsm -> tsm.getService().getId() == token.getCurrentService().getId())
				.findFirst().get().setComments(comments);
		tokenService.saveOrUpdate(token);
	}
	
	@Override
	public void updateTokenStatusById(Integer tokenNumber, StatusCode newTokenStatus) {
		Token token = getToken(tokenNumber);
		counterService.removeToken(token.getCurrentCounter().getId());

		if (StatusCode.COMPLETED.equals(newTokenStatus)) {
			boolean nextService = tokenService.assignNextService(token);
			if (nextService) {
				token.setCurrentCounter(counterService.allocateCounter(token));
			} 

		} 
		tokenService.updateStatus(token,newTokenStatus);
	}

	private Token getToken(Integer tokenNumber) {
		return tokenService.getTokenByNumber(tokenNumber);
	}

}
