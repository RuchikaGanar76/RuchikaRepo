package com.cg.bank.service;

import com.cg.bank.exception.InsufficientBalanceException;
import com.cg.bank.exception.InsufficientInitialBalanceException;
import com.cg.bank.exception.InvalidAccountNumberException;
import com.cg.bank.exception.InvalidAmountException;
import com.cg.bank.repository.IAccountRepo;
import com.cg.bank.model.Account;

public class AccountServiceImpl implements AccountService {
	IAccountRepo accountRepository;
	public AccountServiceImpl(IAccountRepo accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	
	
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialBalanceException
	{
		
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		Account account = new Account();
		
		account.setAccountNo(accountNumber);
		account.setAmmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}


	@Override
	public int depositAmount(Account account, int amount)
			throws InvalidAmountException {
		int balance= account.getAmmount();
		if(amount<=0){
			throw new InvalidAmountException();
		}
		else
		{
		balance = balance + amount;	
		account.setAmmount(balance);
		return account.getAmmount();
		}
	}


	@Override
	public int withdrawAmount(Account account, int amount)
			throws InvalidAmountException, InsufficientBalanceException {
		int balance= account.getAmmount();
		if(amount<=0){
			throw new InvalidAmountException();
		}
		else if((balance-amount)<500)
		{
			throw new InsufficientBalanceException();
		
		}else {
			balance = balance - amount;	
			account.setAmmount(balance);
			return account.getAmmount();
		}
	}


	@Override
	public boolean fundTransfer(Account senderAccount,Account receiverAccount, int amount)
			throws InvalidAmountException, InsufficientBalanceException,
			InvalidAccountNumberException {
		int senderBalance= senderAccount.getAmmount();
		int receiverBalance = receiverAccount.getAmmount();
		if(amount<=0){
			throw new InvalidAmountException();
		}
		else if((senderBalance-amount)<500)
		{
			throw new InsufficientBalanceException();
		
		}else if(Integer.toString(senderAccount.getAccountNo()).length()<3 || 
				Integer.toString(senderAccount.getAccountNo()).length()>3  ||
				Integer.toString(receiverAccount.getAccountNo()).length()<3 || 
				Integer.toString(receiverAccount.getAccountNo()).length()>3){
			
			throw new InvalidAccountNumberException();
			
		}else{
			senderBalance = senderBalance - amount;	
			senderAccount.setAmmount(senderBalance);
			
			receiverBalance = receiverBalance + amount;
			receiverAccount.setAmmount(receiverBalance);
			
			return true;
		}
	}

}
