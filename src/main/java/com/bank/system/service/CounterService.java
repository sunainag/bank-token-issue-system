package com.bank.system.service;

import java.util.List;

import com.bank.system.model.Counter;
import com.bank.system.model.CounterQueue;
import com.bank.system.model.Service;
import com.bank.system.model.Token;

public interface CounterService {

	List<Counter> getAllCounters();

	List<Counter> getAvailableCounters();

	List<Service> getServicesByCounterId(Integer counterId);
	
	List<CounterQueue> getAllCounterQueues();
	
	void addTokenToCounterQueue(CounterQueue queue, Token token);
	
}
