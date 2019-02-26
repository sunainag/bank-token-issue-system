package com.abs.banking.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.exception.BusinessException;
import com.abs.banking.exception.BusinessException.ErrorCode;
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

	/**
	 * @param mobile Customer contact number provided in the payload(@see TokenRequest)
	 * @return
	 */
	private Customer findByMobile(String mobile) {
		return customerRepo.findByMobile(mobile);
	}

	@Override
	@Transactional
	public Customer create(Customer customer) {
		Customer cust = customer;
		if (customer != null) {
			cust.setAddress(customer.getAddress());
			return customerRepo.save(cust);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.abs.banking.service.CustomerService#getByToken(com.abs.banking.dto.TokenRequest)
	 * TODO: provide abstraction for findByMobile, what if want to search by any other criteria/condition
	 */
	@Override
	public Customer getByToken(TokenRequest tokenReq) {
		if (tokenReq.getCustomer() == null)
			throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
		Customer customer = findByMobile(tokenReq.getCustomer().getMobile());
		if (customer == null) {
			customer = create(tokenReq.getCustomer());
		}
		return customer;
	}

}
