package com.bank.system.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.bank.system.exception.CounterNotAvailableException;
import com.bank.system.model.Counter;
import com.bank.system.model.CounterQueue;
import com.bank.system.model.Customer;
import com.bank.system.model.ServiceUrgencyLevel;
import com.bank.system.model.Token;
import com.bank.system.model.TokenStatus;
import com.bank.system.service.CounterService;
import com.bank.system.service.CustomerService;
import com.bank.system.service.TokenService;

@Component
public class CounterManagerImpl implements CounterManager {

	private List<CounterQueue> counterQueues = new ArrayList<>();

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;

	@Autowired
	CounterService counterService;

	/**
	 * @param event
	 * 
	 *            ContextRefreshedEvent annotation will get executed when the
	 *            springBoot application starts. This init method is used to
	 *            initialize counters - assuming all counters are 'AVAILABLE' on
	 *            application startup, and open counterQueues.
	 */
	@EventListener
	public void initCounters(ContextRefreshedEvent event) {
		List<Counter> availCounters = counterService.getAvailableCounters();
		if (CollectionUtils.isEmpty(availCounters)) {
			throw new CounterNotAvailableException("No counters available on startup. Please check the system.");
		}
		availCounters.forEach(counter -> {
			CounterQueue queue = new CounterQueue(counter);
			counter.setListOfServices(counterService.getServicesByCounterId(counter.getId()));
			counterQueues.add(queue);
		});
	}

	@Override
	public List<CounterQueue> getCounterDetails() {
		return counterService.getAllCounterQueues();
	}

	@Override
	public void createToken(Token token) {
		if (token != null) {
			// checkIfCustomerExists
			Customer cust = token.getCustomer();
			if (!customerService.ifExists(cust)) {
				customerService.createCustomer(cust);
			}
			// issue token
			Token issuedToken = tokenService.generateToken(token);
			// assign counter
			assignCounter(issuedToken);
		}
	}

	private void assignCounter(Token issuedToken) {
		// TODO: synchronize this
		List<CounterQueue> counterQueues = getServingCounters(issuedToken);
		if (counterQueues.isEmpty())
			throw new CounterNotAvailableException();

		// check counter queue with minimum length and then check service
		Comparator<CounterQueue> comparator_queues = Comparator.comparing(
		        CounterQueue::getTokens, (t1, t2) -> {
		            return t2.size()-t1.size();
		        });
		CounterQueue smallestQueue = counterQueues.stream().min(comparator_queues).get();
		addTokenToQueue(smallestQueue, issuedToken);
	}

	private void addTokenToQueue(CounterQueue counterQueue, Token issuedToken) {
		issuedToken.setStatus(TokenStatus.IN_QUEUE);
		counterService.addTokenToCounterQueue(counterQueue, issuedToken);
		tokenService.updateToken(issuedToken);
	}

	private List<CounterQueue> getServingCounters(Token issuedToken) {
		//also check service type
		if (ServiceUrgencyLevel.PREMIUM.equals(issuedToken.getServiceLevel())) {
			return counterQueues.parallelStream().filter(
					counterQueue -> ServiceUrgencyLevel.PREMIUM.equals(counterQueue.getCounter().getCounterType()))
					.collect(Collectors.toList());
		}else{
			return counterQueues.parallelStream().filter(
					counterQueue -> ServiceUrgencyLevel.REGULAR.equals(counterQueue.getCounter().getCounterType()))
					.collect(Collectors.toList());
		}
	}

	@Override
	public Token getToken(Long tokenId) {
		return tokenService.getTokenById(tokenId);
	}

}
