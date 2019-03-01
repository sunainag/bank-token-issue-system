package com.abs.banking.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.banking.manager.CounterManager;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;

@RestController
@RequestMapping(value = "/abs/bank")
public class CounterController {

	@Autowired
	private CounterManager counterManager;

	@GetMapping(value = "/counters/{counterNumber}/token")
	public Token getNextTokenInQueue(@PathVariable("counterNumber") Integer counterNumber) {
		return counterManager.getNextTokenFromQueue(counterNumber);
	}

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

	@PatchMapping(value = "/counters/{counterNumber}/tokens/{tokenNumber}")
	public ResponseEntity<?> performTokenAction(@PathVariable("tokenNumber") @NotNull Integer tokenNumber,
			@RequestBody Map<String, Object> updates) {
		String comments = updates.get("comments").toString();
		String status = updates.get("action").toString();
		
		if (!StringUtils.isEmpty(comments)) {
			counterManager.setComments(tokenNumber, comments);
			return ResponseEntity.ok("comments updated");
		}
		if(!StringUtils.isEmpty(status)) {
			counterManager.updateTokenStatusById(tokenNumber, tokenNumber, status);
			return ResponseEntity.ok("New token status for token " + tokenNumber + " is " +status);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You may comment/cancel/complete the token");
	}
}
