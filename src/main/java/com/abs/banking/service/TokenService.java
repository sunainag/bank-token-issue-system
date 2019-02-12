package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Service;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface TokenService {

	Token getTokenByNumber(Integer tokenNumber);

	Token saveOrUpdateToken(Token token);

	Service findServiceByName(String serviceName);

	Service findServiceById(Long nextServiceId);

	List<Token> findByStatusCode(StatusCode statusCode);

}
