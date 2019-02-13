package com.abs.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer> {

	Counter findByNumber(Integer counterNum);
}
