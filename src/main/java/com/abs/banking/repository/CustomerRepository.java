package com.abs.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByMobile(String mobile);

}
