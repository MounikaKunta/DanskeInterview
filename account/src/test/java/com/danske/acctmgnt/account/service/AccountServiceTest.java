package com.danske.acctmgnt.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.domain.TransferDetails;
import com.danske.acctmgnt.account.mapper.AccountServiceMapper;
import com.danske.acctmgnt.account.model.Account;
import com.danske.acctmgnt.account.model.CustomerAccountXRef;
import com.danske.acctmgnt.account.model.Transaction;
import com.danske.acctmgnt.account.model.TransactionDetails;
import com.danske.acctmgnt.account.repository.AccountRepository;
import com.danske.acctmgnt.account.repository.CustomerAccountXRefRepository;
import com.danske.acctmgnt.account.repository.TransactionRepository;
import com.danske.acctmgnt.account.serviceImpl.AccountServiceImpl;
import com.danske.acctmgnt.customer.domain.CustomerDetails;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountServiceImpl accountServiceImpl;

	@Mock
	private AccountServiceMapper accountServiceMapper;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CustomerAccountXRefRepository customerAccountXRefRepository;

	@Mock
	TransactionRepository transactionRepository;
	
	@Mock
	RestTemplate restTemplate;

	@Test
	public void createAccountTest() {
		Long customernumber = 220593L;
		AccountInformation accInfo = new AccountInformation();
		accInfo.setAccountNumber(1234567890L);
		accInfo.setAccountType("Savings");
		accInfo.setAccountBalance(0.0);
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountType("Savings");
		acc.setAccountBalance(0.0);
		CustomerAccountXRef customerAccountXRef = new CustomerAccountXRef();
		customerAccountXRef.setAccountNumber(1234567890L);
		customerAccountXRef.setCustomerNumber(220593L);
		CustomerDetails custDetails = new CustomerDetails();
		custDetails.setCustomerNumber(123l);
		custDetails.setFirstName("Mounika");
		custDetails.setLastName("Kunta");
		when(accountServiceMapper.convertToAccountEntity(any(AccountInformation.class))).thenReturn(acc);
		when(restTemplate.getForObject(any(String.class), any())).thenReturn(custDetails);
		Account account = accountServiceImpl.createAccount(accInfo, customernumber);
		verify(accountRepository, times(1)).save(acc);
	}

	@Test
	public void depositMoneyTest() {
		Long customernumber = 220593L;
		TransferDetails details = new TransferDetails();
		details.setToAccountNumber(1234567890L);
		details.setTransferAmount(10.0);
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountBalance(11.0);
		acc.setAccountType("Savings");
		Transaction t = new Transaction();
		t.setAccountNumber(1234567890L);
		t.setTxType("DEPOSIT");
		t.setTxAmount(10.0);
		when(accountRepository.findByAccountNumber(any(Long.class))).thenReturn(Optional.of(acc));
		when(accountServiceMapper.createTransaction(any(TransferDetails.class), any(Long.class), any(String.class)))
				.thenReturn(t);
		ResponseEntity<Object> response = accountServiceImpl.depositMoney(details, customernumber);
		assertThat(response.getBody()).isEqualTo("Success: Amount Deposited for Customer Number ");
		assertThat(acc.getAccountBalance()).isEqualTo(21.0);
	}

	@Test
	public void withdrawMoneyTest() {
		Long customernumber = 220593L;
		TransferDetails details = new TransferDetails();
		details.setFromAccountNumber(1234567890L);
		details.setTransferAmount(10.0);
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountBalance(11.0);
		acc.setAccountType("Savings");
		Transaction t = new Transaction();
		t.setAccountNumber(1234567890L);
		t.setTxType("DEPOSIT");
		t.setTxAmount(10.0);
		when(accountRepository.findByAccountNumber(any(Long.class))).thenReturn(Optional.of(acc));
		when(accountServiceMapper.createTransaction(any(TransferDetails.class), any(Long.class), any(String.class)))
				.thenReturn(t);
		ResponseEntity<Object> response = accountServiceImpl.withdrawMoney(details, customernumber);
		assertThat(response.getBody()).isEqualTo("Success: Amount withdrawn for Customer ");
		assertThat(acc.getAccountBalance()).isEqualTo(1.0);
	}

	@Test
	public void readBalanceTest() {
		Long accountnumber = 1234567890L;
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountBalance(11.0);
		acc.setAccountType("Savings");
		when(accountRepository.findByAccountNumber(any(Long.class))).thenReturn(Optional.of(acc));
		String bal = accountServiceImpl.readBalance(accountnumber);
		assertThat(bal).isEqualTo("11.0");
	}

	@Test
	public void findTransactionsByAccountNumber() {
		Account acc = new Account();
		acc.setAccountNumber(1234567890L);
		acc.setAccountBalance(11.0);
		acc.setAccountType("Savings");
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		t.setAccountNumber(1234567890L);
		t.setTxType("DEPOSIT");
		t.setTxAmount(10.0);
		transactions.add(t);
		TransactionDetails tdtls = new TransactionDetails();
		tdtls.setAccountNumber(1234567890L);
		tdtls.setTxAmount(10.0);
		tdtls.setTxType("DEPOSIT");
		when(accountRepository.findByAccountNumber(any(Long.class))).thenReturn(Optional.of(acc));
		when(transactionRepository.findByAccountNumber(any(Long.class))).thenReturn(Optional.of(transactions));
		when(accountServiceMapper.convertToTransactionDomain(any(Transaction.class))).thenReturn(tdtls);
		List<TransactionDetails> response = accountServiceImpl.findTransactionsByAccountNumber(1234567890L);
		assertThat(response.get(0).getTxAmount()).isEqualTo(10.0);
	}

}
