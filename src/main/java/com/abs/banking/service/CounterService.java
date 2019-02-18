package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

public interface CounterService {

	List<Counter> getAllCounters();

	Counter getCounter(Integer counterNumber);

	Token assignNextService(Token token);

}
