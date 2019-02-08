package com.bank.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.system.model.Counter;
import com.bank.system.model.CounterStatus;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Integer>{

	@Query("SELECT c FROM Counter c WHERE c.status = ?1")
	List<Counter> findCountersByStatus(CounterStatus status);
}
