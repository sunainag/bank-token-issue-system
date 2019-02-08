package com.bank.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.system.exception.InvalidTokenException;
import com.bank.system.model.ServiceType;
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
	public Token getTokenById(Long tokenId) {
		Optional<Token> token = Optional.ofNullable(tokenRepo.findById(tokenId)).orElseThrow(InvalidTokenException::new);
		return token.get();
	}

	public List<Long> getTokenByCounterIdAndServiceType(Integer counterId, ServiceType serviceType){
		return tokenRepo.findByCounterIdAndServiceType(counterId, serviceType);
	}

	@Override
	public List<Long> getTokenByCounterId(int counterId) {
		return tokenRepo.findByCounterId(counterId);
	}

}
