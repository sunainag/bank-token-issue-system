package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Counter;

public interface CounterService {

	List<Counter> getAll();

	Counter incrementQueueSize(int counterId);

	Counter decrementQueueSize(int counterId);

}
