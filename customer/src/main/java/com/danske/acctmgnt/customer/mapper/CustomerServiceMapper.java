package com.danske.acctmgnt.customer.mapper;

import org.springframework.stereotype.Component;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.model.Customer;

@Component
public class CustomerServiceMapper {

	public Customer convertToCustomerEntity(CustomerDetails customerDetails) {

		return Customer.builder().firstName(customerDetails.getFirstName()).lastName(customerDetails.getLastName())
				.customerNumber(customerDetails.getCustomerNumber()).build();
	}

	public CustomerDetails convertToCustomerDomain(Customer customer) {

		return CustomerDetails.builder().firstName(customer.getFirstName()).lastName(customer.getLastName())
				.customerNumber(customer.getCustomerNumber()).build();
	}

}
