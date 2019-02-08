package com.bank.system.manager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.system.model.Counter;
import com.bank.system.service.CounterService;
import com.bank.system.service.TokenService;

@Component
public class CounterManagerImpl implements CounterManager{
	
	@Autowired
	CounterService counterService;
	
	@Autowired
	TokenService tokenService;

	@Override
	public List<Counter> getCounterDetails() {
		List<Counter> counters = counterService.getAllCounters();
		counters.forEach(counter->{
			
			int counterId = counter.getId();
			List<Long> tokens = tokenService.getTokenByCounterId(counterId);
			counter.setTokenIds(tokens);
			counter.setListOfServices(counter.getListOfServices());
		});
		
		return counters;
	}
	

}
