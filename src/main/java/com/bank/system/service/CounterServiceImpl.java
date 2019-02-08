package com.bank.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.system.model.Counter;
import com.bank.system.model.CounterStatus;
import com.bank.system.repository.CounterRepository;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;
	
	@Override
	public List<Counter> getAllCounters() {
		return counterRepo.findAll();
	}
	
	@Override
	public List<Counter> getAvailableCounters() {
		return counterRepo.findCountersByStatus(CounterStatus.AVAILABLE);
	}

}

