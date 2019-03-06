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
	public Map<Integer, List<Integer>> getActiveTokens() {
		List<Token> activeTokens = tokenService.findByStatusCode(Token.StatusCode.ACTIVE);
		Map<Integer, List<Integer>> counterToTokens = activeTokens.stream().collect(Collectors
				.groupingBy(t -> t.getCounterNumber(), Collectors.mapping(Token::getNumber, Collectors.toList())));
		return counterToTokens;
	}

	@Override
	public ResponseEntity<String> getToken(Integer tokenNumber) {
		Token token = tokenService.getTokenByNumber(tokenNumber);
		return ResponseEntity.ok("Token " + token.getNumber() + " is waiting for counter:"
				+ token.getCounterNumber() + " issued currently for service:" + token.getCurrentService().getName());
	}

	@Override
	public ResponseEntity<String> issueToken(TokenRequest tokenReq) {

		Customer customer = customerService.getByToken(tokenReq);
		Token token = tokenService.issueToken(customer, tokenReq.getServices());
		//if (token != null && token.getCurrentCounter() != null) {
		return ResponseEntity.status(HttpStatus.CREATED).body("Token number:" + token.getNumber()
				+ " issued");
		/*}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body("Token could not be issued due to server error. Please debug to find the cause. Counter assigned to token is: "
					+ token.getCurrentCounter());*/
	}
}
