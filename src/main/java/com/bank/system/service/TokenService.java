package com.bank.system.service;

import com.bank.system.model.Token;

public interface TokenService {
	
	Token generateToken(Token token);

	void assignCounter(Token issuedToken);

}
