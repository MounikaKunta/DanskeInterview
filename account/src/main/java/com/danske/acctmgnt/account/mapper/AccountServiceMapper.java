package com.danske.acctmgnt.account.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.domain.TransferDetails;
import com.danske.acctmgnt.account.model.Account;
import com.danske.acctmgnt.account.model.Transaction;
import com.danske.acctmgnt.account.model.TransactionDetails;

@Component
public class AccountServiceMapper {

	public Account convertToAccountEntity(AccountInformation accInfo) {

		return Account.builder().accountType(accInfo.getAccountType()).accountBalance(accInfo.getAccountBalance())
				.accountNumber(accInfo.getAccountNumber()).accountStatus(accInfo.getAccountStatus()).build();
	}

	public Transaction createTransaction(TransferDetails transferDetails, Long accountNumber, String txType) {

		return Transaction.builder().accountNumber(accountNumber).txAmount(transferDetails.getTransferAmount())
				.txType(txType).txDateTime(new Date()).build();
	}

	public TransactionDetails convertToTransactionDomain(Transaction transaction) {

		return TransactionDetails.builder().txAmount(transaction.getTxAmount()).txDateTime(transaction.getTxDateTime())
				.txType(transaction.getTxType()).accountNumber(transaction.getAccountNumber()).build();
	}

}
