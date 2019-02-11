package com.abs.banking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.exception.BusinessException.ErrorCode;
import com.abs.banking.model.Token;
import com.abs.banking.repository.ServiceRepository;
import com.abs.banking.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenRepository tokenRepo;

	@Autowired
	ServiceRepository serviceRepo;


	@Override
	public Token getTokenById(Long tokenId) {
		Optional<Token> token = tokenRepo.findById(tokenId);
		if (token.isPresent())
			return token.get();
		else
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
	}

	@Override
	public List<Long> getTokenByCounterId(int counterId) {
		return tokenRepo.findByCounterId(counterId);
	}

	@Override
	public Token saveOrUpdateToken(Token token) {
		return tokenRepo.save(token);
	}

	@Override
	public com.abs.banking.model.Service findServiceByName(String name) {
		return serviceRepo.findByName(name);
	}

	@Override
	public com.abs.banking.model.Service findServiceById(Long nextServiceId) {
		if (serviceRepo.findById(nextServiceId).isPresent())
			return serviceRepo.findById(nextServiceId).get();
		else
			throw new BusinessException(BusinessException.ErrorCode.SERVICE_NOT_FOUND);
	}

}
