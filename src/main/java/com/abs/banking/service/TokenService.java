package com.abs.banking.service;

import java.util.List;

import com.abs.banking.model.Customer;
import com.abs.banking.model.Services;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface TokenService {

	Token issueToken(Customer customer, List<String> tokenServices);

	Services findServiceById(Long nextServiceId);

	List<Token> findByStatusCode(StatusCode statusCode);

	Token getTokenByNumber(Integer tokenNumber);

	boolean isTokenInvalid(Token token);

	void comment(Integer tokenNumber, String comments);

	Token save(Token token);

	boolean existsToken(Customer customer, List<String> services);

}
