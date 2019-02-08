package com.bank.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.system.service.TokenManager;
import com.bank.system.model.Token;

@RestController
public class TokenController {

	@Autowired
	private TokenManager tokenManager;

	@PostMapping(value = "/api/token/create")
	public void issueToken(@RequestBody Token token) {
		tokenManager.createToken(token);
	}
	
}
