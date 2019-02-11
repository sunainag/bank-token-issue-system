package com.abs.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.model.Customer;
import com.abs.banking.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Override
	public boolean ifExists(Customer customer) {
		return customer != null && customerRepo.existsById(customer.getId());
	}

	public Customer findByMobile(String mobile) {
		return customerRepo.findByMobile(mobile);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		if (customer != null)
			return customerRepo.save(customer);
		return null;
	}

}
