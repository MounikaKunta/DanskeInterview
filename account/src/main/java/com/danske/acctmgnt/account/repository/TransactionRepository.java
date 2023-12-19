package com.danske.acctmgnt.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danske.acctmgnt.account.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
	
	public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);
}
