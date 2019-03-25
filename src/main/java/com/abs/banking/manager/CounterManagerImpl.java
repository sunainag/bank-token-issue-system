package com.abs.banking.manager;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abs.banking.exception.InvalidCounterException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.service.CounterService;
import com.abs.banking.service.CustomerService;
import com.abs.banking.service.TokenQueueService;
import com.abs.banking.service.TokenService;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Component
@Transactional
public class CounterManagerImpl implements CounterManager {
	
	private final static Logger LOG = LoggerFactory.getLogger(CounterManagerImpl.class);

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;

	@Autowired
	TokenQueueService tokenQueueService;

	@Autowired
	CounterService counterService;

	@Autowired
	CounterAllocator counterAllocator;

	@Override
	public ResponseEntity<?> getNextTokenFromQueue(Integer counterNumber) {
		Token token = tokenQueueService.pollNextInQueue(counterService.getCounter(counterNumber));
		LOG.info("Next token in this queue is: "+token.getNumber());
		return ResponseEntity
				.ok("Next token from queue:" + token.getNumber() + " is assigned to this counter:" + counterNumber);
	}

	@Override
	public Set<String> getAllCounters() {
		Set<Counter> counters = counterService.getAllCounters();
		Set<String> counterDetails = new HashSet<>();
		counters.forEach(counter -> {
			counterDetails.add(counter.toString());
		});
		return counterDetails;
	}

	@Override
	public ResponseEntity<String> getCounter(Integer counterNumber) {
		Counter counter = counterService.getCounter(counterNumber);
		if (counter != null)
			return ResponseEntity.ok(counter.toString());
		throw new InvalidCounterException();
	}

	@Override
	public void setComments(Integer tokenNumber, String comments) {
		tokenService.comment(tokenNumber, comments);
	}

	@Override
	public ResponseEntity<String> updateTokenStatusById(Integer counterNumber, Integer tokenNumber,
			String newTokenStatus) {
		counterService.resolveToken(tokenNumber, newTokenStatus);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(tokenNumber + " is udpated with status " + newTokenStatus);
	}
}
