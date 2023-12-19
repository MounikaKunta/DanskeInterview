package com.danske.acctmgnt.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.danske.acctmgnt.account.Service.AccountService;
import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.domain.TransferDetails;
import com.danske.acctmgnt.account.model.Account;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountService accountService;

	@Test
	public void addAccountTest() {
		Long customernumber = 220593L;
		AccountInformation accInfo = new AccountInformation();
		accInfo.setAccountNumber(1234567890L);
		accInfo.setAccountType("Savings");
		accInfo.setAccountBalance(0.0);
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountType("Savings");
		acc.setAccountBalance(0.0);
		when(accountService.createAccount(any(AccountInformation.class), any(Long.class))).thenReturn(acc);
		ResponseEntity<Object> response = accountController.addNewAccount(accInfo, customernumber);
		assertThat(response.getBody()).isEqualTo("New account added succesfully");
	}

	@Test
	public void depositMoneyTest() {
		Long customernumber = 220593L;
		TransferDetails details = new TransferDetails();
		details.setToAccountNumber(1234567890L);
		details.setTransferAmount(10.0);
		ResponseEntity r = new ResponseEntity(HttpStatus.OK);
		when(accountService.depositMoney(any(TransferDetails.class), any(Long.class))).thenReturn(r);
		ResponseEntity<Object> response = accountController.depositMoney(details, customernumber);
		assertThat(response.getStatusCode().toString()).isEqualTo("200 OK");
	}

}
