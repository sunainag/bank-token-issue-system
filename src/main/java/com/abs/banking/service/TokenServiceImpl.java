package com.abs.banking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.exception.BusinessException.ErrorCode;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.repository.ServiceRepository;
import com.abs.banking.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenRepository tokenRepo;

	@Autowired
	ServiceRepository serviceRepo;

	@Override
	public Token getTokenByNumber(Integer tokenNumber) {
		List<Token> token = tokenRepo.findByNumber(tokenNumber);
		if (!CollectionUtils.isEmpty(token))
			return token.get(0); //expected unique column `number` in table `token`
		else
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
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

	@Override
	public List<Token> findByStatusCode(StatusCode statusCode) {
		return tokenRepo.findByStatusCode(statusCode);
	}

}
