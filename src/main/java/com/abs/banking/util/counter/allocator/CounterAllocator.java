package com.abs.banking.util.counter.allocator;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

public interface CounterAllocator {

	Counter allocate(Token token);
}
