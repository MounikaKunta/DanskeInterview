package com.danske.acctmgnt.account.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountInformation {
	
private Long accountNumber;
	
	private String accountStatus;
	
	private String accountType;
	
	private Double accountBalance;
	
	private Date accountCreated;

}
