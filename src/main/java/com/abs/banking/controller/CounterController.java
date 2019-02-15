package com.abs.banking.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.banking.manager.CounterManager;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

@RestController
@RequestMapping(value = "/abs/bank")
public class CounterController {

	@Autowired
	private CounterManager counterManager;

	/**
	 * @return Counter details like counter number, queueSize and priority
	 */
	@GetMapping(value = "/counters")
	public List<Counter> getCounterDetails() {
		return counterManager.getAllCounters();
	}

	@GetMapping(value = "/counters/{counterNumber}")
	public ResponseEntity<Counter> getCounterByNumber(@PathVariable("counterNumber") @NotNull Integer counterNum) {
		return counterManager.getCounter(counterNum);
	}

	@PatchMapping(value = "counters/{counterNumber}/tokens/{tokenNumber}/comment")
	public void commentOnToken(@PathVariable("tokenNumber") @NotNull Integer tokenNumber,
			@RequestBody String comments) {
		counterManager.setComments(tokenNumber, comments);
	}

	@PutMapping(value = "counters/{counterNumber}/tokens/{tokenNumber}/cancel")
	public void cancelToken(@PathVariable("tokenNumber") @NotNull Integer tokenNumber) {
		counterManager.updateTokenStatusById(tokenNumber, Token.StatusCode.CANCELLED);
	}

	@PutMapping(value = "counters/{counterNumber}/tokens/{tokenNumber}/complete")
	public void completeToken(@PathVariable("counterNumber") @NotNull Integer counterNumber) {
		counterManager.updateTokenStatusById(counterNumber, StatusCode.COMPLETED);
	}
}
