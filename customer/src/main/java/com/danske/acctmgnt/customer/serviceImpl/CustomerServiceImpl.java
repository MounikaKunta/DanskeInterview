package com.danske.acctmgnt.customer.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.mapper.CustomerServiceMapper;
import com.danske.acctmgnt.customer.model.Customer;
import com.danske.acctmgnt.customer.repository.CustomerRepository;
import com.danske.acctmgnt.customer.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerServiceMapper customerServiceMapper;

	public Customer createCustomer(CustomerDetails custDetails) {

		Customer customer = customerServiceMapper.convertToCustomerEntity(custDetails);
		customerRepository.save(customer);
		return customer;

	}
	
	public CustomerDetails findByCustomerNumber(Long customerNumber) {

		Optional<Customer> customer = customerRepository.findByCustomerNumber(customerNumber);

		if (customer.isPresent())
			return customerServiceMapper.convertToCustomerDomain(customer.get());

		return null;
	}

}
