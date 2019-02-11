package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Service;
import com.abs.banking.model.Token;

public interface TokenService {

	Token getTokenById(Long tokenId);

	List<Long> getTokenByCounterId(int counterId);
	
	Token saveOrUpdateToken(Token token);
	
	Service findServiceByName(String serviceName);

	Service findServiceById(Long nextServiceId);

}
