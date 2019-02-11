package com.abs.banking.counter.allocator;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Service;

public interface CounterAllocator {

	Counter allocate(Service service, Customer customer);
}
