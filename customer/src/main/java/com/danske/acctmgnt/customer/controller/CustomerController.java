package com.danske.acctmgnt.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.service.CustomerService;

@RestController
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(path = "/add")
	public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customerDetails) {
		customerService.createCustomer(customerDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body("New Customer added succesfully");
	}

	@GetMapping(path = "/{customerNumber}")
	public CustomerDetails getCustomer(@PathVariable Long customerNumber) {

		return customerService.findByCustomerNumber(customerNumber);
	}

}
