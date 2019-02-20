package com.abs.banking.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.manager.TokenManager;
import com.abs.banking.model.Token;

@RestController
@RequestMapping(value = "/abs/bank")
public class TokenController {

	@Autowired
	private TokenManager tokenManager;

	/**
	 * @return Map of counter number(Integer) and list of token numbers (List<Integer>) corresponding to the
	 *         counter.
	 */
	@GetMapping(value = "/tokens")
	public Map<Integer, List<Integer>> activeTokens() {
		return tokenManager.getActiveTokens();
	}

	@GetMapping(value = "/tokens/{tokenNumber}")
	public @ResponseBody Token getToken(@PathVariable("tokenNumber") @NotNull Integer tokenNumber) {
		return tokenManager.getToken(tokenNumber);
	}

	@PostMapping(value = "/tokens")
	public ResponseEntity<String> issueToken(@RequestBody @NotNull TokenRequest tokenRequest) {
		return tokenManager.issueToken(tokenRequest);
	}

}
