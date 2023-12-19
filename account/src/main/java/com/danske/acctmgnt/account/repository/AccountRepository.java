package com.danske.acctmgnt.account.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danske.acctmgnt.account.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
	
	Optional<Account> findByAccountNumber(Long accountNumber);
}
