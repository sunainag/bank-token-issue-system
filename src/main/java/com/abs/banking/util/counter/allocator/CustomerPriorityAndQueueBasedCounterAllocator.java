package com.abs.banking.util.counter.allocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Services;
import com.abs.banking.model.ServiceCounterMapping;
import com.abs.banking.repository.CounterRepository;
import com.abs.banking.repository.ServiceCounterMappingRepo;

@Component
public class CustomerPriorityAndQueueBasedCounterAllocator implements CounterAllocator {

	private Map<String, List<Counter>> premiumServiceCounters = new HashMap<>();
	private Map<String, List<Counter>> normalServiceCounters = new HashMap<>();

	@Autowired
	private ServiceCounterMappingRepo serviceCounterMappingRepo;

	@Autowired
	private CounterRepository counterRepo;

	@EventListener
	public void init(ContextRefreshedEvent event) {
		serviceCounterMappingRepo.findAll().forEach(scm -> {
			if (scm.getType().equals(Customer.CustomerType.PREMIUM)) {
				addServiceCounterMapping(scm, premiumServiceCounters);
			}
			else {
				addServiceCounterMapping(scm, normalServiceCounters);
			}
		});
	}

	@Override
	public Counter allocate(Services service, Customer customer) {
		List<Counter> counters = getServiceCountersBasedOnCustomerPriority(service, customer);
		if (!counters.isEmpty()) {
			// Allocate counter with minimum queue size
			int minQueueSize = Integer.MAX_VALUE;
			Counter allocatedCounter = null;
			for (Counter counter : counters) {
				int queueSize = counterRepo.findById(counter.getId()).get().getQueueSize();
				if (queueSize < minQueueSize) {
					minQueueSize = queueSize;
					allocatedCounter = counter;
				}
			}
			return allocatedCounter;
		}
		return null;
	}

	private List<Counter> getServiceCountersBasedOnCustomerPriority(Services service, Customer customer) {
		List<Counter> counters = Collections.emptyList();
		switch (customer.getType()) {
		case PREMIUM:
			if (premiumServiceCounters != null) {
				counters = premiumServiceCounters.get(service.getName());
				break;
			}
		case REGULAR:
			if (normalServiceCounters != null) {
				counters = normalServiceCounters.get(service.getName());
				break;
			}
		default:
			throw new BusinessException(BusinessException.ErrorCode.COUNTERS_NOT_INITIALIZED_CORRECTLY);
		}
		return counters;
	}

	private void addServiceCounterMapping(ServiceCounterMapping scm, Map<String, List<Counter>> serviceCounters) {
		String serviceName = scm.getService().getName();
		List<Counter> counters = serviceCounters.get(serviceName);
		if (counters == null) {
			counters = new ArrayList<>();
		}
		counters.add(scm.getCounter());
		serviceCounters.put(serviceName, counters);
	}

}
