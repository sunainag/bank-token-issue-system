package com.abs.banking.service;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.exception.CounterNotAvailableException;
import com.abs.banking.exception.InvalidTokenException;
import com.abs.banking.exception.InvalidTokenQueueException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Services.ServicesType;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.repository.TokenRepository;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Service
public class TokenBlockingQueueService implements TokenQueueService {

	@Autowired
	CounterAllocator counterAllocator;

	@Autowired
	TokenRepository tokenRepo;

	TokenQueueConsumer producer;

	BlockingQueue<Token> tokenQueue;

	// map of counter numbers : queue of tokens
	Map<Integer, PriorityBlockingQueue<Token>> counterWiseQueueMap;

	@Override
	public void putInQueue(Token token) {
		initialize();
		tokenQueue.add(token);
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
					throw new InvalidTokenException();
					// or token.setCurrentCounter(counter);
				}

				return token;
			}
			else {
				throw new InvalidTokenQueueException();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Set status for next queue to be IN_PROGRESS; Already removed from previous
	 * queue; when counter gets the token from queue @see this.pollNextInQueue(...)
	 * ; Add to the token queue with highest priority, as this token is already in
	 * process from previous queue;
	 * 
	 */
	@Override
	public Token addToNextQueue(Token token) {
		token.setStatusCode(StatusCode.IN_PROGRESS);
		token.getCurrentService().setType(ServicesType.URGENT);
		tokenQueue.add(token);
		return token;
	}

	/**
	 * Consumer thread to allocate queue for each token generated and assign them a
	 * counter
	 *
	 */
	class TokenQueueConsumer extends Thread {

		public void run() {
			try {
				while (true) {
					Token token = tokenQueue.take();
					Thread.sleep(1000);
					addToTokenQueue(token);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Comparator to decide the sequence in which the token is to be served first in
	 * the queue
	 *
	 */
	class TokenPriorityComparator implements Comparator<Token> {

		@Override
		public int compare(Token t1, Token t2) {
			return t1.getCurrentService().getType().ordinal() - t2.getCurrentService().getType().ordinal();
		}

	}

	private void initialize() {
		if (tokenQueue == null) {
			tokenQueue = new LinkedBlockingQueue<>();
			counterWiseQueueMap = new ConcurrentHashMap<>();
			producer = new TokenQueueConsumer();
			producer.start();

		}
	}

	private Counter addToTokenQueue(Token token) throws InterruptedException {
		if (token == null || token.isInactive()) {
			throw new InvalidTokenException();
		}

		Counter counter = counterAllocator.allocate(token);
		if (counter == null)
			throw new CounterNotAvailableException();
		token.setCurrentCounter(counter);
		tokenRepo.save(token);
		Thread.sleep(1000);
		PriorityBlockingQueue<Token> counterQueue = counterWiseQueueMap.get(counter.getNumber());
		if (counterQueue == null) {
			counterQueue = new PriorityBlockingQueue<>(11, new TokenPriorityComparator());
		}
		counterQueue.put(token);
		counterWiseQueueMap.put(counter.getNumber(), counterQueue);
		return counter;
	}

}
