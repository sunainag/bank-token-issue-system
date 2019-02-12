package com.abs.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.model.Counter;
import com.abs.banking.repository.CounterRepository;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;

	public synchronized Counter incrementQueueSize(int counterId) {
		Counter counter = counterRepo.findById(counterId).get();
		counter.setQueueSize(counter.getQueueSize() + 1);
		return saveCounter(counter);
	}

	public synchronized Counter decrementQueueSize(int counterId) {
		Counter counter = counterRepo.findById(counterId).get();
		counter.setQueueSize(counter.getQueueSize() - 1);
		return saveCounter(counter);
	}

	public synchronized Counter saveCounter(Counter counter) {
		return counterRepo.save(counter);
	}

	@Override
	public List<Counter> getAll() {
		List<Counter> counters = new ArrayList<>();
		counterRepo.findAll().forEach(counters::add);
		return counters;
	}
}
