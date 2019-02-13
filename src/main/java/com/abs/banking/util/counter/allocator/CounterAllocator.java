package com.abs.banking.util.counter.allocator;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Services;

public interface CounterAllocator {

	Counter allocate(Services service, Customer customer);
}
