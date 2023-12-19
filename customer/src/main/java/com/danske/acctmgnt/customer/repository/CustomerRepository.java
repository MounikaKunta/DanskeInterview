package com.danske.acctmgnt.customer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danske.acctmgnt.customer.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

	public Optional<Customer> findByCustomerNumber(Long customerNumber);
}
