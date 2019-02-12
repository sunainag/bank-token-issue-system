package com.abs.banking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/abs/bank")
public class HealthController {

	@GetMapping(value = "/")
	public String home() {
		return "Welcome to ABS BANK. Please select a service which we can provide";
	}
}
