package com.abs.banking.service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.util.counter.allocator.CounterAllocator;

public class TokenBlockingQueueService implements TokenQueueService {

	@Autowired
	private CounterService counterService;

	@Autowired
	CounterAllocator counterAllocator;

	TokenQueueProducer producer;

	BlockingQueue<Token> tokenQueue;

	Map<Integer, PriorityBlockingQueue<Token>> counterWiseQueueMap;// map of counter numbers : queue of tokens

	@Override
	public void putInQueue(Token token) {
		try {
			initialize();
			tokenQueue.put(token);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Token pollNextInQueueAndSetStatus(Counter counter, StatusCode newStatus) {
		try {
			// check if this token is alloted this counter and set status acc to the token
			//TODO: also add the priority to premium customers logic
			if (counterWiseQueueMap.containsKey(counter.getNumber())) {
				Token token = counterWiseQueueMap.get(counter.getNumber()).take();//reduced queue size too
				if(token.getCurrentCounter()!=counter) {
					throw new BusinessException(BusinessException.ErrorCode.INVALID_TOKEN);
					//or token.setCurrentCounter(counter);
				}else {
					token.setStatusCode(newStatus);
					return token;
				}
			} else {
				throw new BusinessException(BusinessException.ErrorCode.INVALID_COUNTER_ID);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	class TokenQueueProducer extends Thread {

		public void run() {
			try {
				Token token = tokenQueue.take();
				addToTokenQueue(token);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initialize() {
		if (tokenQueue == null) {
			tokenQueue = new LinkedBlockingQueue<>();
			counterWiseQueueMap = new ConcurrentHashMap<>();
			TokenQueueProducer producer = new TokenQueueProducer();
			producer.start();

		}
	}

	private void resolveToken(Token token) {
		if (StatusCode.COMPLETED.equals(token.getStatusCode())) {
			token = counterService.assignNextService(token);
			if (token.getCurrentService()!=null) {
				token.setCurrentCounter(counterAllocator.allocate(token));
			}
		}
	}

	private Counter addToTokenQueue(Token token) throws InterruptedException {
		if (token.getCurrentCounter() != null || token.isInactive()) {
			// log info - token is already added to queue or inappropriate token status
			return null;
		}

		Counter counter = counterAllocator.allocate(token);
		token.setCurrentCounter(counter);
		PriorityBlockingQueue<Token> counterQueue = counterWiseQueueMap.get(counter.getNumber());
		if (counterQueue == null) {
			counterQueue = new PriorityBlockingQueue<>();
		}
		counterQueue.put(token);
		counterWiseQueueMap.put(counter.getNumber(), counterQueue);
		return counter;
	}

}
