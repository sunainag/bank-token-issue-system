package com.bank.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.system.model.Token;
import com.bank.system.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenRepository tokenRepo;

	@Override
	public Token generateToken(Token token) {
		return tokenRepo.save(token);
	}

	@Override
	public void assignCounter(Token issuedToken) {
		//TODO
	}

}
