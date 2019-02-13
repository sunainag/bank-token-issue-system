package com.abs.banking.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Token;
import com.abs.banking.service.CounterService;
import com.abs.banking.service.CustomerService;
import com.abs.banking.service.TokenService;

public class TokenManagerImpl implements TokenManager {

	@Autowired
	TokenService tokenService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CounterService counterService;

	//TODO: token service should notify counter service to increament queue size
	@Override
	public ResponseEntity<String> createToken(TokenRequest tokenReq) {
		Customer customer = customerService.getByToken(tokenReq);
		Token token = tokenService.generateToken(customer, tokenReq.getServices());
		counterService.allocateCounter(token);

		tokenService.saveOrUpdate(token);
		return ResponseEntity.status(HttpStatus.OK).body("Token number assigned:" + token.getNumber());
	}

	@Override
	public Token getToken(Integer tokenNumber) {
		return tokenService.getTokenByNumber(tokenNumber);
	}

	@Override
	public Map<Integer, List<Integer>> getActiveTokens() {
		List<Token> activeTokens = tokenService.findByStatusCode(Token.StatusCode.ACTIVE);
		Map<Integer, List<Integer>> counterToTokens = activeTokens.stream()
				.collect(Collectors.groupingBy(t -> Integer.valueOf(t.getCurrentCounter().getNumber()),
						Collectors.mapping(Token::getNumber, Collectors.toList())));
		return counterToTokens;
	}

}
