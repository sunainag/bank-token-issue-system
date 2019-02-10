package com.bank.system.manager;

import java.util.List;

import com.bank.system.model.CounterQueue;
import com.bank.system.model.Token;

public interface CounterManager {

	void createToken(Token token);

	Token getToken(Long tokenId);

	List<CounterQueue> getCounterDetails();

}
