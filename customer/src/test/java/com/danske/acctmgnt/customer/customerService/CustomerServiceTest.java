package com.danske.acctmgnt.customer.customerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.mapper.CustomerServiceMapper;
import com.danske.acctmgnt.customer.model.Customer;
import com.danske.acctmgnt.customer.repository.CustomerRepository;
import com.danske.acctmgnt.customer.serviceImpl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Mock
	private CustomerServiceMapper customerServiceMapper;

	@Mock
	CustomerRepository customerRepository;

	@Test
	public void createCustomer() {
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		Customer cust = new Customer();
		cust.setCustomerNumber(123l);
		cust.setFirstName("Mounika");
		cust.setLastName("Kunta");
		when(customerServiceMapper.convertToCustomerEntity(any(CustomerDetails.class))).thenReturn(cust);
		when(customerRepository.save(any(Customer.class))).thenReturn(cust);
		Customer customer = customerServiceImpl.createCustomer(custDetails);
		verify(customerRepository, times(1)).save(cust);
	}
	
	@Test
	public void findByCustomerNumber() {
		
		Customer cust = new Customer();
		cust.setCustomerNumber(123l);
		cust.setFirstName("Mounika");
		cust.setLastName("Kunta");
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		when(customerRepository.findByCustomerNumber(any(Long.class))).thenReturn(Optional.of(cust));
		when(customerServiceMapper.convertToCustomerDomain(any(Customer.class))).thenReturn(custDetails);
		CustomerDetails customerDtls = customerServiceImpl.findByCustomerNumber(123l);
		assertThat(customerDtls.getFirstName()).isEqualTo("Mounika");
		
	}

}
