package com.danske.acctmgnt.customer.service;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.model.Customer;

public interface CustomerService {

	public Customer createCustomer(CustomerDetails custDetails);
	
	public CustomerDetails findByCustomerNumber(Long customerNumber);
}
