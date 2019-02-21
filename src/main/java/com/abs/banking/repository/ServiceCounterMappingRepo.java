package com.abs.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.ServiceCounterMapping;

@Repository
public interface ServiceCounterMappingRepo extends JpaRepository<ServiceCounterMapping, Long> {

}
