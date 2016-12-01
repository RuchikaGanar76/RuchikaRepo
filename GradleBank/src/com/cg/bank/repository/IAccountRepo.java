package com.cg.bank.repository;

import com.cg.bank.model.Account;

public interface IAccountRepo {

/*	public boolean depositAmount(long accountNo, float amount);

	public boolean withdrawAmount(long accountNo, float amount);

	public boolean transferAmount(long senderAccountNo, long receiverAccountNo,
			float amount);

	public float showBalance(long accountNo);*/
	
	
	public boolean save(Account account);
	Account searchAccount(int accountNumber);

}