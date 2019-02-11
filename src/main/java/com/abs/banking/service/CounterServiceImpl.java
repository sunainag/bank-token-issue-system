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

	public synchronized void incrementQueueSize(int counterId) {
		Counter counter = counterRepo.findById(counterId).get();
		counter.setQueueSize(counter.getQueueSize() + 1);
		saveCounter(counter);
	}

	public synchronized void decrementQueueSize(int counterId) {
		Counter counter = counterRepo.findById(counterId).get();
		counter.setQueueSize(counter.getQueueSize() - 1);
		saveCounter(counter);
	}

	public synchronized void saveCounter(Counter counter) {
		counterRepo.save(counter);
	}

	@Override
	public List<Counter> getAll() {
		List<Counter> counters = new ArrayList<>();
		counterRepo.findAll().forEach(counters::add);
		return counters;
	}
}
