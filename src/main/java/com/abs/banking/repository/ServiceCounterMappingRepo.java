package com.abs.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.ServiceCounterMapping;

@Repository
public interface ServiceCounterMappingRepo extends CrudRepository<ServiceCounterMapping, Long> {

}
