package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Counter;

public interface CounterService {

	List<Counter> getAll();

	void incrementQueueSize(int counterId);

	void decrementQueueSize(int counterId);

}
