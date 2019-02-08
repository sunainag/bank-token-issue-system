package com.bank.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.system.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long>{

}
