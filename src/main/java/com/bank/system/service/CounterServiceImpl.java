package com.bank.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bank.system.model.Counter;
import com.bank.system.model.CounterQueue;
import com.bank.system.model.CounterStatus;
import com.bank.system.model.Token;
import com.bank.system.repository.CounterQueueRepository;
import com.bank.system.repository.CounterRepository;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;
	
	@Autowired
	CounterQueueRepository counterQueueRepo;

	@Override
	public List<Counter> getAllCounters() {
		return counterRepo.findAll();
	}

	@Override
	public List<Counter> getAvailableCounters() {
		return counterRepo.findCountersByStatus(CounterStatus.AVAILABLE);
	}

	public List<com.bank.system.model.Service> getServicesByCounterId(Integer counterId) {
		Optional<Counter> counter = counterRepo.findById(counterId);
		counter.orElseThrow(() -> new EmptyResultDataAccessException(
				"Invalid counter data", 1));
		return counter.get().getListOfServices();
	}

	@Override
	public void addTokenToCounterQueue(CounterQueue queue, Token token) {
		queue.getTokens().add(token);
		queue.getCounter().setStatus(CounterStatus.OCCUPIED);
		counterQueueRepo.save(queue);
	}

	@Override
	public List<CounterQueue> getAllCounterQueues() {
		return counterQueueRepo.findAll();
	}

}
