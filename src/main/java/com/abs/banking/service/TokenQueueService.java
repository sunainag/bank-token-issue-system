package com.abs.banking.service;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface TokenQueueService {

	void putInQueue(Token token);
	
	Token pollNextInQueueAndSetStatus(Counter counter, StatusCode newTokenStatus);
	
}
