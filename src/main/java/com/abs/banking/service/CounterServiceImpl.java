package com.abs.banking.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.TokenServiceMapping;
import com.abs.banking.repository.CounterRepository;
import com.abs.banking.util.counter.allocator.CounterAllocator;

@Service
public class CounterServiceImpl implements CounterService {

	@Autowired
	CounterRepository counterRepo;

	@Autowired
	CounterAllocator counterAllocator;

	@Override
	public List<Counter> getAllCounters() {
		List<Counter> counters = new ArrayList<>();
		counterRepo.findAll().forEach(counters::add);
		return counters;
	}

	@Override
	public Counter getCounter(Integer counterNum) {
		return counterRepo.findByNumber(counterNum);
	}

	@Override
	public Token assignNextService(Token token) {
		TokenServiceMapping nextService = null;
		Iterator<TokenServiceMapping> i = token.getTokenServices().iterator();
		while (i.hasNext()) {
			TokenServiceMapping tsm = i.next();
			if (token.getCurrentService()!=null && tsm.getService().getId() == token.getCurrentService().getId() && i.hasNext()) {
				nextService = i.next();
				token.setCurrentService(nextService.getService());
			}
			else {
				token.setCurrentService(null);
			}
		}
		return token;
	}
}
