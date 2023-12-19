package com.danske.acctmgnt.customer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.model.Customer;
import com.danske.acctmgnt.customer.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@Test
	public void addCustomerTest() {
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		Customer cust = new Customer();
		cust.setCustomerNumber(123l);
		cust.setFirstName("Mounika");
		cust.setLastName("Kunta");
		when(customerService.createCustomer(any(CustomerDetails.class))).thenReturn(cust);
		ResponseEntity<Object> response = customerController.addCustomer(custDetails);
		assertThat(response.getBody()).isEqualTo("New Customer added succesfully");
	}

	@Test
	public void findByCustomerNumberTest() {
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		when(customerService.findByCustomerNumber(any(Long.class))).thenReturn(custDetails);
		CustomerDetails response = customerController.getCustomer(123l);
		assertThat(response.getFirstName()).isEqualTo("Mounika");

	}

}
