package com.abs.banking.manager;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

public interface CounterManager {

	Set<String> getAllCounters();

	ResponseEntity<String> getCounter(Integer counterNumber);

	void setComments(@NotNull Integer tokenNumber, String comments);

	ResponseEntity<String> updateTokenStatusById(Integer counterNumber, Integer tokenNumber, String tokenStatus);

	ResponseEntity<?> getNextTokenFromQueue(Integer counterNumber);

}
