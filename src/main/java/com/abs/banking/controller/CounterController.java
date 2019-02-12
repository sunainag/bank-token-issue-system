package com.abs.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.banking.manager.CounterManager;
import com.abs.banking.model.Counter;

@RestController
@RequestMapping(value = "/abs/bank")
public class CounterController {

	@Autowired
	private CounterManager counterManager;

	@GetMapping(value = "/counters")
	public List<Counter> getCounterDetails() {
		return counterManager.getAllCounters();
	}

}
