package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Customer;
import com.abs.banking.model.Services;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface TokenService {

	Token generateToken(Customer customer, List<String> tokenServices);

	Token saveOrUpdate(Token token);

	Services findServiceById(Long nextServiceId);

	List<Token> findByStatusCode(StatusCode statusCode);

	void updateStatus(Token token, StatusCode statusCode);

	Token getTokenByNumber(Integer tokenNumber);

	boolean assignNextService(Token token);

}
