package com.abs.banking.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.manager.CounterManager;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

@RestController
@RequestMapping(value = "/abs/bank")
public class TokenController {

	@Autowired
	private CounterManager countermanager;

	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public Map<Object, List<Integer>> activeTokens() {
		return countermanager.getActiveTokens();
	}

	@PostMapping(value = "/tokens")
	public void issueToken(@RequestBody @NotNull TokenRequest tokenRequest) {
		countermanager.createToken(tokenRequest);
	}

	@GetMapping(value = "/tokens/{tokenNumber}")
	public @ResponseBody Token getToken(@PathVariable("tokenNumber") Integer tokenNumber) {
		return countermanager.getToken(tokenNumber);
	}

	@PutMapping(value = "/tokens/{tokenNumber}/comment")
	public void comment(@PathVariable("tokenNumber") @NotNull Integer tokenNumber, @RequestBody String comments) {
		countermanager.setComments(tokenNumber, comments);
	}

	@PutMapping(value = "/tokens/{tokenNumber}/cancel")
	public void cancel(@PathVariable("tokenNumber") @NotNull Integer tokenNumber) {
		countermanager.updateTokenStatusById(tokenNumber, Token.StatusCode.CANCELLED);
	}

	@PutMapping(value = "/tokens/{tokenNumber}/complete")
	public void complete(@PathVariable("tokenNumber") @NotNull Integer tokenNumber) {
		countermanager.updateTokenStatusById(tokenNumber, StatusCode.COMPLETED);
	}

}
