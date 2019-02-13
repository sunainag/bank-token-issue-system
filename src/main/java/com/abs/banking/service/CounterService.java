package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

public interface CounterService {

	List<Counter> getAllCounters();

	Counter getCounter(Integer counterNumber);
	
	Counter allocateCounter(Token token);
	
	Counter removeToken(Integer counterId);
	
	Counter alterQueueSize(int counterId, int count);

}
