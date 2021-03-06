package com.bank.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.system.model.ServiceType;
import com.bank.system.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	@Query("SELECT distinct t.id FROM Token t WHERE t.counterId = ?1 and t.tokenType = ?2")
	List<Long> findByCounterIdAndServiceType(Integer counterId, ServiceType serviceType);

	@Query("SELECT distinct t.id FROM Token t WHERE t.counterId = ?1")
	List<Long> findByCounterId(Integer counterId);
}
