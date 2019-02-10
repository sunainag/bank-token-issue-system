package com.bank.system.service;

import java.util.List;

import com.bank.system.model.ServiceType;
import com.bank.system.model.Token;

public interface TokenService {

	Token generateToken(Token token);

	Token getTokenById(Long tokenId);

	List<Long> getTokenByCounterIdAndServiceType(Integer counterId, ServiceType serviceType);

	List<Long> getTokenByCounterId(int counterId);
	
	void updateToken(Token token);

}
