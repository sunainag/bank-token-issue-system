package com.bank.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.system.model.CounterQueue;

@Repository
public interface CounterQueueRepository extends JpaRepository<CounterQueue,Integer > {

}
