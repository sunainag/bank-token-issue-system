package com.abs.banking.manager;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface CounterManager {

	Integer createToken(TokenRequest tokenRequest);

	void setComments(@NotNull Integer tokenNumber, String comments);

	Token getToken(Integer number);

	void updateTokenStatusById(Integer tokenNumber, StatusCode tokenStatus);

	List<Counter> getAllCounters();

	Map<Object, List<Integer>> getActiveTokens();

}
