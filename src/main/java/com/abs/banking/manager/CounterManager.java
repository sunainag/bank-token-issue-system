package com.abs.banking.manager;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;

public interface CounterManager {

	Long createToken(TokenRequest tokenRequest);

	void setComments(@NotNull Long tokenNumber, String comments);

	Token getToken(Long tokenId);

	void updateTokenStatusById(Long tokenNumber, StatusCode tokenStatus);

	List<Counter> getAllCounters();

	Map<Object, List<Long>> getActiveTokens();

}
