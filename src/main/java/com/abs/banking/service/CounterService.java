package com.abs.banking.service;

import java.util.Set;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

public interface CounterService {

	Set<Counter> getAllCounters();

	Counter getCounter(Integer counterNumber);

	Token assignNextService(Token token);

}
