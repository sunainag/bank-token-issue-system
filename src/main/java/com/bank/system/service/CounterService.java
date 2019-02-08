package com.bank.system.service;

import java.util.List;

import com.bank.system.model.Counter;

public interface CounterService {
	
	List<Counter> getAllCounters();

	List<Counter> getAvailableCounters();

}
