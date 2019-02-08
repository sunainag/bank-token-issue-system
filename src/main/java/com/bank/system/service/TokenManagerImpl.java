package com.bank.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.system.model.Customer;
import com.bank.system.model.Token;

@Component
public class TokenManagerImpl implements TokenManager {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	TokenService tokenService;

	public void createToken(Token token) {
		if (token != null) {
			// checkIfCustomerExists
			if (!customerService.ifExists(token.getCustomer())) {
				Customer cust = customerService.createCustomer(token.getCustomer());
			}
			
			// issue token
			 Token issuedToken = tokenService.generateToken(token);
			
			// assign counter
			 tokenService.assignCounter(issuedToken);
		}
	}

}
