package com.bank.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.system.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
