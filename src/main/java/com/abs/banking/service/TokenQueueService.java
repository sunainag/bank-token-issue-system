package com.abs.banking.service;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

public interface TokenQueueService {

	void putInQueue(Token token);

	Token pollNextInQueue(Counter counter);

	Counter addToNextQueue(Token token);

}
