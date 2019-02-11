package com.abs.banking.counter.allocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Service;
import com.abs.banking.model.ServiceCounterMapping;
import com.abs.banking.repository.CounterRepository;
import com.abs.banking.repository.ServiceCounterMappingRepo;

@Component
public class CustomerPriorityAndQueueBasedCounterAllocator implements CounterAllocator{

	private Map<String, List<Counter>> premiumServiceCounters = new HashMap<>();
    private Map<String, List<Counter>> normalServiceCounters = new HashMap<>();

    @Autowired
    private ServiceCounterMappingRepo serviceCounterMappingRepo;

    @Autowired
    private CounterRepository counterRepo;

    @PostConstruct
    public void init() {
        serviceCounterMappingRepo.findAll().forEach(scm -> {
            if (scm.getType().equals(Customer.Type.PREMIUM)) {
                addServiceCounterMapping(scm, premiumServiceCounters);
            } else {
                addServiceCounterMapping(scm, normalServiceCounters);
            }
        });
    }

    @Override
    public Counter allocate(Service service, Customer customer) {
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

    private List<Counter> getServiceCountersBasedOnCustomerPriority(Service service, Customer customer) {
        List<Counter> counters = Collections.emptyList();
        switch (customer.getType()) {
            case PREMIUM:
                counters = premiumServiceCounters.get(service.getName());
                break;
            case REGULAR:
                counters = normalServiceCounters.get(service.getName());
                break;
        }
        return counters;
    }

    private void addServiceCounterMapping(ServiceCounterMapping scm, Map<String, List<Counter>> serviceCounters) {
        String serviceName = scm.getService().getName();
        List<Counter> counters = serviceCounters.get(serviceName);
        if (counters == null) {
            counters = new ArrayList<>();
            serviceCounters.put(serviceName, counters);
        }
        counters.add(scm.getCounter());
    }

}
