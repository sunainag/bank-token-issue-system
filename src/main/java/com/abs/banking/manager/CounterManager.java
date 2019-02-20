package com.abs.banking.manager;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface CounterManager {

	List<Counter> getAllCounters();

	ResponseEntity<Counter> getCounter(Integer counterNumber);

	void setComments(@NotNull Integer tokenNumber, String comments);

	ResponseEntity<Integer> updateTokenStatusById(Integer counterNumber, Integer tokenNumber, StatusCode tokenStatus);

	Token getNextTokenFromQueue(Integer counterNumber);

}
