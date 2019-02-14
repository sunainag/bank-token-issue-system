package com.abs.banking.model;

import java.util.concurrent.PriorityBlockingQueue;

public class CounterQueue {
	
	private PriorityBlockingQueue<Token> counterQueue = new PriorityBlockingQueue<>();
	private Counter counter;
	

	public Counter getCounter() {
		return counter;
	}

	public void putInQueue(Token token) {
		counterQueue.put(token);
	}

	public int size() {
		return counterQueue.size();
	} 

}
