package com.abs.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	List<Token> findByNumber(Integer number);

	List<Token> findByStatusCode(Token.StatusCode statusCode);
	
	@Query("SELECT t FROM Token t, Custoomer c, Services s WHERE t.customer_id = ?1 and t.current_service_id = ?2")
	Token findByCustomerAndServices(Long customer_id, Long serviceId);
}
