package com.bank.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.system.manager.TokenManager;
import com.bank.system.model.Token;

@RestController
@RequestMapping(value="/api/token")
public class TokenController {

	@Autowired
	private TokenManager tokenManager;

	@PostMapping(value = "/create")
	public void issueToken(@RequestBody Token token) {
		tokenManager.createToken(token);
	}
	
	@GetMapping(value = "/getToken/{tokenId}")
	public @ResponseBody Token issueToken(@PathVariable ("tokenId") Long tokenNumber) {
		return tokenManager.getToken(tokenNumber);
	}
	
}
