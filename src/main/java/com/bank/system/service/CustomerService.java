package com.bank.system.service;

import com.bank.system.model.Customer;

public interface CustomerService {

	boolean ifExists(Customer customer);

	Customer createCustomer(Customer customer);
}
