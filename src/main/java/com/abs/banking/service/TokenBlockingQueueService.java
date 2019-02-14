package com.abs.banking.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public class TokenBlockingQueueService implements TokenQueueService {
	
	@Autowired
	private CounterService counterService;

	BlockingQueue<Token> tokenQueue;
	
	TokenQueueProducer producer;
	
	TokenQueueConsumer consumer;

	@Override
	public void putInQueue(Token token){
		try {
			initialize();
			tokenQueue.put(token);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pollNextInQueueWithStatus(StatusCode status) {
		if(consumer==null) {
			consumer = new TokenQueueConsumer();
			consumer.start();
		}
	}

	private void initialize() {
		if (tokenQueue == null) {
			tokenQueue = new LinkedBlockingQueue<>();
			TokenQueueProducer producer = new TokenQueueProducer();
			producer.start();
			
		}
	}
	
	class TokenQueueConsumer extends Thread {
		
		public void run() {
			try {
				Token token = tokenQueue.take();
				resolveToken(token);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void resolveToken(Token token) {
			counterService.removeToken(token.getCurrentCounter().getId());
			if (StatusCode.COMPLETED.equals(newTokenStatus)) {
				boolean nextService = tokenService.assignNextService(token);
				if (nextService) {
					token.setCurrentCounter(counterService.allocateCounter(token));
				}

			}
		}
	}

	class TokenQueueProducer extends Thread {

		public void run() {
			Token token = tokenQueue.peek();
			allocateCounter(token);
		}

		private Counter allocateCounter(Token token) {
			return counterService.allocateCounter(token);
		}
	}

}
