package com.abs.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.banking.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	List<Token> findByNumber(Integer number);

	List<Token> findByStatusCode(Token.StatusCode statusCode);
}
