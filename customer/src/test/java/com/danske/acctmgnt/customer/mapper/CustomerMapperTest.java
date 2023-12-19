package com.danske.acctmgnt.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danske.acctmgnt.customer.domain.CustomerDetails;
import com.danske.acctmgnt.customer.model.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {
	
	@InjectMocks
	private CustomerServiceMapper customerServiceMapper;
	
	@Test
	public void convertToCustomerEntity() {
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		Customer cust = customerServiceMapper.convertToCustomerEntity(custDetails);
		assertThat(cust.getFirstName()).isEqualTo("Mounika");
	}

}
