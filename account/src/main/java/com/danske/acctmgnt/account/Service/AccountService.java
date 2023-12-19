package com.danske.acctmgnt.account.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.domain.TransferDetails;
import com.danske.acctmgnt.account.model.Account;
import com.danske.acctmgnt.account.model.TransactionDetails;

public interface AccountService {

	public Account createAccount(AccountInformation custDetails, Long customerNumber);

	public ResponseEntity<Object> depositMoney(TransferDetails transferDetails, Long customerNumber);

	public ResponseEntity<Object> withdrawMoney(TransferDetails transferDetails, Long customerNumber);

	public String readBalance(Long accountNumber);

	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber);

}
