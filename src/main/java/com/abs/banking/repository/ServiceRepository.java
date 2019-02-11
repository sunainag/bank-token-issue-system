package com.abs.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

	Service findByName(String name);
}
