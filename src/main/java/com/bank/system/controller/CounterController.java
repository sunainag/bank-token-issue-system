package com.bank.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.system.manager.CounterManager;
import com.bank.system.model.CounterQueue;

@RestController
@RequestMapping(value = "/api/bank/counter")
public class CounterController {

	@Autowired
	private CounterManager counterManager;

	@GetMapping(value = "/status")
	public List<CounterQueue> getCounterDetails() {
		return counterManager.getCounterDetails();
	}

}
