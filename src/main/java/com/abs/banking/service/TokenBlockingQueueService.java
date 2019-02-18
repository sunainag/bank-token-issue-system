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
import com.abs.banking.repository.TokenRepository;
import com.abs.banking.util.counter.allocator.CounterAllocator;

public class TokenBlockingQueueService implements TokenQueueService {

	@Autowired
	CounterAllocator counterAllocator;

	@Autowired
	TokenRepository tokenRepo;

	TokenQueueProducer producer;

	//TODO: instead use a circularfifoqueue or Dequeue
	BlockingQueue<Token> tokenQueue;

	// map of counter numbers : queue of tokens
	Map<Integer, PriorityBlockingQueue<Token>> counterWiseQueueMap;

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
	public Token pollNextInQueue(Counter counter) {
		try {
			// check if this token is alloted this counter and set status acc to
			// the token
			// TODO: also add the priority to premium customers logic
			if (counterWiseQueueMap.containsKey(counter.getNumber())) {
				Token token = counterWiseQueueMap.get(counter.getNumber()).take();// reduced
																					// queue
																					// size
																					// too
				if (token.getCounterNumber() != counter.getNumber()) {
					throw new BusinessException(BusinessException.ErrorCode.INVALID_TOKEN);
					// or token.setCurrentCounter(counter);
				}
				return token;
			} else {
				throw new BusinessException(BusinessException.ErrorCode.TOKEN_NOT_ASSIGNED_TO_THIS_COUNTER);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Counter addToNextQueue(Token token) {
		try {
			///already removed from previous queue
			token.setStatusCode(StatusCode.IN_PROGRESS);
			
			//TODO:set highest priority
			
			addToTokenQueue(token);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return token.getCurrentCounter();
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
		if (counterWiseQueueMap == null) {
			tokenQueue = new LinkedBlockingQueue<>();
			counterWiseQueueMap = new ConcurrentHashMap<>();
			TokenQueueProducer producer = new TokenQueueProducer();
			producer.start();

		}
	}
	
	private Counter addToTokenQueue(Token token) throws InterruptedException {
		if (token.isInactive()) {
			// log info - inappropriate token state
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
		saveTokenState(token);
		return counter;
	}

	private void saveTokenState(Token token) {
		tokenRepo.saveAndFlush(token);
	}

}
