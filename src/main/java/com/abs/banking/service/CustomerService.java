package com.abs.banking.service;

import com.abs.banking.model.Customer;

public interface CustomerService {

	boolean ifExists(Customer customer);

	Customer findByMobile(String mobileNumber);

	Customer createCustomer(Customer customer);
}
