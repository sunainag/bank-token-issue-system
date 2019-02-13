package com.abs.banking.service;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Customer;

/**
 * @author sunainag
 * 
 *         Provides for the creation, retrieval and other operations of the
 *         customer by interacting with the database
 *
 */
public interface CustomerService {

	boolean ifExists(Customer customer);

	/**
	 * @param customer
	 * @return created customer object
	 */
	Customer create(Customer customer);

	/**
	 * @param tokenReq Payload sent in the request for token creation
	 * @return Customer for who the token is generated
	 */
	Customer getByToken(TokenRequest tokenReq);
}
