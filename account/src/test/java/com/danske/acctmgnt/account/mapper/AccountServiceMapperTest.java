package com.danske.acctmgnt.account.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danske.acctmgnt.account.domain.AccountInformation;
import com.danske.acctmgnt.account.model.Account;
import com.danske.acctmgnt.account.model.Transaction;
import com.danske.acctmgnt.account.model.TransactionDetails;

@ExtendWith(MockitoExtension.class)
public class AccountServiceMapperTest {

	@InjectMocks
	private AccountServiceMapper accountServiceMapper;

	@Test
	public void convertToAccountEntity() {
		AccountInformation accInfo = new AccountInformation();
		accInfo.setAccountNumber(1234567890L);
		accInfo.setAccountType("Savings");
		accInfo.setAccountBalance(0.0);
		Account acc = accountServiceMapper.convertToAccountEntity(accInfo);
		assertThat(acc.getAccountNumber()).isEqualTo(1234567890L);
	}

	@Test
	public void convertToTransactionDomain() {
		Transaction t = new Transaction();
		t.setAccountNumber(1234567890L);
		t.setTxAmount(10.0);
		t.setTxType("DEPOSIT");
		TransactionDetails tdtls = accountServiceMapper.convertToTransactionDomain(t);
		assertThat(tdtls.getTxType()).isEqualTo("DEPOSIT");
	}

}
