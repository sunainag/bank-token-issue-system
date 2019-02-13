package com.abs.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.repository.CounterRepository;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;

	@Autowired
	CounterAllocator counterAllocator;

	@Override
	public List<Counter> getAllCounters() {
		List<Counter> counters = new ArrayList<>();
		counterRepo.findAll().forEach(counters::add);
		return counters;
	}

	@Override
	public Counter getCounter(Integer counterNum) {
		return counterRepo.findByNumber(counterNum);
	}

	@Override
	public synchronized Counter allocateCounter(Token token) {
		Counter counter = counterAllocator.allocate(token.getCurrentService(), token.getCustomer());
		token.setCurrentCounter(counter);
		return alterQueueSize(counter.getId(), 1);
	}

	@Override
	public Counter removeToken(Integer counterId) {
		return alterQueueSize(counterId, -1);
	}

	public synchronized Counter alterQueueSize(int counterId, int count) {
		Counter counter = counterRepo.findById(counterId).get();
		counter.setQueueSize(counter.getQueueSize() + count);
		return saveCounter(counter);
	}

	private synchronized Counter saveCounter(Counter counter) {
		return counterRepo.save(counter);
	}

}
