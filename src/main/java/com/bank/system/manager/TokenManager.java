package com.bank.system.manager;

import com.bank.system.model.Token;

public interface TokenManager {

	void createToken(Token token);
	
	Token getToken(Long tokenId);
	
}
