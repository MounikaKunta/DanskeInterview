package com.danske.acctmgnt.account.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.danske.acctmgnt.account.Service.AccountService;
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
import com.danske.acctmgnt.customer.domain.CustomerDetails;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountServiceMapper accountServiceMapper;

	@Autowired
	private CustomerAccountXRefRepository customerAccountXRefRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TransactionRepository transactionRepository;

	public Account createAccount(AccountInformation accountInformation, Long customerNumber) {

		// invoke customer service if customer present go with next step
		CustomerDetails custDetails = restTemplate.getForObject("http://localhost:8081/customer-api/customers/{customerNumber}", CustomerDetails.class, customerNumber);
		System.out.println("CustomerDetails " +custDetails);
		if(null != custDetails) {
		Account account = accountServiceMapper.convertToAccountEntity(accountInformation);
		accountRepository.save(account);
		customerAccountXRefRepository.save(CustomerAccountXRef.builder()
				.accountNumber(accountInformation.getAccountNumber()).customerNumber(customerNumber).build());
		return account;
		}else {
			return null;
		}

	}

	public ResponseEntity<Object> depositMoney(TransferDetails transferDetails, Long customerNumber) {

		Account depositAccount = null;
		// invoke customer service if customer present go with next step else throw
		// exception
		Optional<Account> depositAccountOpt = accountRepository
				.findByAccountNumber(transferDetails.getToAccountNumber());
		if (depositAccountOpt.isPresent()) {
			depositAccount = depositAccountOpt.get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number not found.");
		}
		depositAccount.setAccountBalance(depositAccount.getAccountBalance() + transferDetails.getTransferAmount());
		depositAccount.setUpdateDateTime(new Date());
		accountRepository.save(depositAccount);

		Transaction transaction = accountServiceMapper.createTransaction(transferDetails,
				depositAccount.getAccountNumber(), "DEPOSIT");
		transactionRepository.save(transaction);

		return ResponseEntity.status(HttpStatus.OK).body("Success: Amount Deposited for Customer Number ");

	}

	public ResponseEntity<Object> withdrawMoney(TransferDetails transferDetails, Long customerNumber) {

		Account withdrawAccount = null;
		// invoke customer service if customer present go with next step else throw
		// exception
		Optional<Account> withdrawAccountOpt = accountRepository
				.findByAccountNumber(transferDetails.getFromAccountNumber());
		if (withdrawAccountOpt.isPresent()) {
			withdrawAccount = withdrawAccountOpt.get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number not found.");
		}
		if (withdrawAccount.getAccountBalance() < transferDetails.getTransferAmount()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
		} else {
			withdrawAccount
					.setAccountBalance(withdrawAccount.getAccountBalance() - transferDetails.getTransferAmount());
			withdrawAccount.setUpdateDateTime(new Date());
			accountRepository.save(withdrawAccount);

			Transaction transaction = accountServiceMapper.createTransaction(transferDetails,
					withdrawAccount.getAccountNumber(), "WITHDRAW");
			transactionRepository.save(transaction);

			return ResponseEntity.status(HttpStatus.OK).body("Success: Amount withdrawn for Customer ");
		}

	}

	public String readBalance(Long accountNumber) {

		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);

		if (accountEntityOpt.isPresent())
			return accountEntityOpt.get().getAccountBalance().toString();

		return null;
	}

	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber) {
		List<TransactionDetails> transactionDetails = new ArrayList<>();
		Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
		if (account.isPresent()) {
			Optional<List<Transaction>> transactions = transactionRepository.findByAccountNumber(accountNumber);
			if (transactions.isPresent()) {
				transactions.get().stream().limit(10).forEach(transaction -> {
					transactionDetails.add(accountServiceMapper.convertToTransactionDomain(transaction));
				});
			}
		}

		return transactionDetails;
	}

}
