package com.cg.bank.service;

import com.cg.bank.exception.InsufficientBalanceException;
import com.cg.bank.exception.InsufficientInitialBalanceException;
import com.cg.bank.exception.InvalidAccountNumberException;
import com.cg.bank.exception.InvalidAmountException;
import com.cg.bank.model.Account;

public interface AccountService {

	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	public Account createAccount(int accountNumber, int amount)
			throws InsufficientInitialBalanceException;
	
	public int depositAmount(Account account, int amount) throws InvalidAmountException;
	
	public int withdrawAmount(Account account, int amount) throws InvalidAmountException, InsufficientBalanceException;
	
	public boolean fundTransfer(Account senderAccount,Account receiverAccount, int amount) throws InvalidAmountException, InsufficientBalanceException, InvalidAccountNumberException;


}