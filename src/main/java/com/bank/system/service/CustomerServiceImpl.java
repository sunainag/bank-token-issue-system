package com.bank.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.system.model.Customer;
import com.bank.system.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Override
	public boolean ifExists(Customer customer) {
		return customer != null && customerRepo.existsById(customer.getId());
	}

	@Override
	public Customer createCustomer(Customer customer) {
		if (customer != null)
			return customerRepo.save(customer);
		return null;
	}

}
