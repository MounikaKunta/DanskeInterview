package com.danske.acctmgnt.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danske.acctmgnt.account.Service.AccountService;
import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.domain.TransferDetails;
import com.danske.acctmgnt.account.model.Account;
import com.danske.acctmgnt.account.model.TransactionDetails;

@RestController
@RequestMapping("accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping(path = "/add/{customerNumber}")
	public ResponseEntity<Object> addNewAccount(@RequestBody AccountInformation accountInformation,
			@PathVariable Long customerNumber) {
		Account acc = accountService.createAccount(accountInformation, customerNumber);
		if(null != acc) {
			return ResponseEntity.status(HttpStatus.CREATED).body("New account added succesfully");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer does not exist");

		}
		
	}

	@PutMapping(path = "/deposit/{customerNumber}")
	public ResponseEntity<Object> depositMoney(@RequestBody TransferDetails transferDetails,
			@PathVariable Long customerNumber) {

		return accountService.depositMoney(transferDetails, customerNumber);
	}

	@PutMapping(path = "/withdraw/{customerNumber}")
	public ResponseEntity<Object> withdrawMoney(@RequestBody TransferDetails transferDetails,
			@PathVariable Long customerNumber) {

		return accountService.withdrawMoney(transferDetails, customerNumber);
	}

	@GetMapping(path = "/{accountNumber}")
	public String readBalance(@PathVariable Long accountNumber) {

		return accountService.readBalance(accountNumber);
	}

	@GetMapping(path = "/transactions/{accountNumber}")
	public List<TransactionDetails> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

		return accountService.findTransactionsByAccountNumber(accountNumber);
	}

}
