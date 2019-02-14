package com.abs.banking.service;

import com.abs.banking.model.CounterQueue;
import com.abs.banking.model.Token;

public class TokenProducer extends Thread {

	CounterQueue tokenQueue;
	Token token;

	public TokenProducer(CounterQueue tokenQueue, Token token) {
		this.tokenQueue = tokenQueue;
		this.token = token;
	}

	@Override
	public void run() {
		synchronized (tokenQueue) {
			tokenQueue.putInQueue(token);
			System.out.println("Now token queue size for the counter " + tokenQueue.getCounter().getNumber() + " is increased to: "
					+ tokenQueue.size());
		}
	}

}
