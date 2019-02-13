package com.abs.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Services;

@Repository
public interface ServiceRepository extends CrudRepository<Services, Long> {

	Services findByName(String name);
}
