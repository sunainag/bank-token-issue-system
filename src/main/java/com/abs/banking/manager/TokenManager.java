package com.abs.banking.manager;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Token;

/**
 * @author sunainag
 * 
 * Manages the creation, retrieval and other operations on the token object
 *
 */
public interface TokenManager {

	/**
	 * @param tokenRequest Payload sent in the request for token creation
	 * @return Response with details of the token created
	 */
	ResponseEntity<String> issueToken(TokenRequest tokenRequest);

	/**
	 * @param number Token number alloted to the customer(@see Customer)
	 * @return Token
	 */
	Token getToken(Integer number);

	/**
	 * @return A Map of Counter number to the List of token numbers assigned to it(@see Token)
	 */
	Map<Integer, List<Integer>> getActiveTokens();

}
