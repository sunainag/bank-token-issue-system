package com.abs.banking.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Token;
import com.abs.banking.service.CounterService;
import com.abs.banking.service.CustomerService;
import com.abs.banking.service.TokenService;

@Component
public class TokenManagerImpl implements TokenManager {

	@Autowired
	TokenService tokenService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CounterService counterService;

	@Override
	public ResponseEntity<String> issueToken(TokenRequest tokenReq) {

		Customer customer = customerService.getByToken(tokenReq);
		Token token = tokenService.issueToken(customer, tokenReq.getServices());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Token number:" + token.getNumber() + " is assigned counter number:" + token.getCounterNumber());
	}

	@Override
	public Token getToken(Integer tokenNumber) {
		return tokenService.getTokenByNumber(tokenNumber);
	}

	@Override
	public Map<Integer, List<Integer>> getActiveTokens() {
		List<Token> activeTokens = tokenService.findByStatusCode(Token.StatusCode.ACTIVE);
		Map<Integer, List<Integer>> counterToTokens = activeTokens.stream().collect(Collectors
				.groupingBy(t -> t.getCounterNumber(), Collectors.mapping(Token::getNumber, Collectors.toList())));
		return counterToTokens;
	}

}
