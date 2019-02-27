package com.abs.banking.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<?> commentOnToken(@PathVariable("tokenNumber") @NotNull Integer tokenNumber,
			@RequestBody MultiValueMap<String,Object> updates) {
		 String comments = updates.getFirst("comments").toString();
		counterManager.setComments(tokenNumber, comments);
		return ResponseEntity.ok("comments updated");
	}

	@PostMapping(value = "/counters/{counterNumber}/tokens/{tokenNumber}/cancel")
	public ResponseEntity<?> cancelToken(@PathVariable("tokenNumber") @NotNull Integer tokenNumber) {
		counterManager.updateTokenStatusById(tokenNumber, tokenNumber, Token.StatusCode.CANCELLED);
		return ResponseEntity.ok("Token "+tokenNumber+" cancelled");
	}

	@PostMapping(value = "counters/{counterNumber}/tokens/{tokenNumber}/complete")
	public ResponseEntity<?> completeToken(@PathVariable("counterNumber") Integer counterNumber,
			@PathVariable("tokenNumber") Integer tokenNumber) {
		return counterManager.updateTokenStatusById(counterNumber, tokenNumber, StatusCode.COMPLETED);
	}
}