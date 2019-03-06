package com.abs.banking.manager;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

import com.abs.banking.model.Counter;

public interface CounterManager {

	List<Counter> getAllCounters();

	ResponseEntity<String> getCounter(Integer counterNumber);

	void setComments(@NotNull Integer tokenNumber, String comments);

	ResponseEntity<String> updateTokenStatusById(Integer counterNumber, Integer tokenNumber, String tokenStatus);

	ResponseEntity<?> getNextTokenFromQueue(Integer counterNumber);

}
