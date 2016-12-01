package com.cg.bank.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.bank.exception.InsufficientBalanceException;
import com.cg.bank.exception.InsufficientInitialBalanceException;
import com.cg.bank.exception.InvalidAccountNumberException;
import com.cg.bank.exception.InvalidAmountException;
import com.cg.bank.model.Account;
import com.cg.bank.repository.IAccountRepo;
import com.cg.bank.service.AccountService;
import com.cg.bank.service.AccountServiceImpl;

import static org.mockito.Mockito.when;

public class AccountTest {

	@Mock
	IAccountRepo accountRepository;
	
	AccountService accountService;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	//Test case Create Account: initial balance <500
	@Test(expected=com.cg.bank.exception.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101,400);
	}
	
	
	//Test case Create Account: initial balance >=500 and valid info
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account();
		account.setAccountNo(101);
		account.setAmmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101,5000));
	}
	
	
	//Test Case for deposit::: when amount is negative
	@Test(expected=com.cg.bank.exception.InvalidAmountException.class)
	public void whenAmountToDepositIsNegativeShouldThrowException() throws InvalidAmountException
	{
		Account account = new Account();
		account.setAccountNo(101);
		account.setAmmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		accountService.depositAmount(account, -21);
	}
	
	
	//Test Case for deposit::: when amount is correct
	@Test
	public void whenAmountToDepositIsCorrectThenDepositSuccessfully() throws InvalidAmountException
	{
		Account account = new Account();
		account.setAccountNo(101);
		account.setAmmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		int balance = accountService.depositAmount(account,500);
		assertEquals(5500, balance);
		System.out.println("After deposit::::::"+balance);
	}
	
	
	//Test Case for withdraw::: when amount is negative
	@Test(expected=com.cg.bank.exception.InvalidAmountException.class)
	public void whenAmountToWithdrawIsNegativeShouldThrowException() throws InvalidAmountException, InsufficientBalanceException
	{
		Account account = new Account();
		account.setAccountNo(101);
		account.setAmmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		accountService.withdrawAmount(account, -21);
	}
	
	
	//Test Case for withdraw::: when amount after withdraw is less than 500
		@Test(expected=com.cg.bank.exception.InsufficientBalanceException.class)
		public void whenAmountToWithdrawIsLessThan500ShouldThrowException() throws InsufficientBalanceException, InvalidAmountException
		{
			Account account = new Account();
			account.setAccountNo(101);
			account.setAmmount(5000);
			when(accountRepository.searchAccount(101)).thenReturn(account);
			
			accountService.withdrawAmount(account,4700);
		}
	
		
		//Test Case for withdraw::: when amount after withdraw is positive balance
			@Test
			public void whenAmountToWithdrawIsCorrectShouldWithdrawSuccessfully() throws InvalidAmountException, InsufficientBalanceException
			{
				Account account = new Account();
				account.setAccountNo(101);
				account.setAmmount(5000);
				when(accountRepository.searchAccount(101)).thenReturn(account);
					
				int balance = accountService.withdrawAmount(account,400);
				assertEquals(4600, balance);
				System.out.println("After withdrawl::::::"+balance);
			}
			
			//Test Case for Transfer::: when amount is negative
			@Test(expected=com.cg.bank.exception.InvalidAmountException.class)
			public void whenAmountToTransferIsNegativeShouldThrowException() throws InvalidAmountException, InsufficientBalanceException, InvalidAccountNumberException
			{
				Account senderAccount = new Account();
				senderAccount.setAccountNo(101);
				senderAccount.setAmmount(5000);
				when(accountRepository.searchAccount(101)).thenReturn(senderAccount);
				
				Account receiverAccount = new Account();
				receiverAccount.setAccountNo(102);
				receiverAccount.setAmmount(1000);
				when(accountRepository.searchAccount(102)).thenReturn(receiverAccount);
				
				accountService.fundTransfer(senderAccount, receiverAccount,-21);
			}
			
			//Test Case for Transfer::: when amount after transfer is less than 0
			@Test(expected=com.cg.bank.exception.InsufficientBalanceException.class)
			public void whenAmountToTransferIsLessThan500ShouldThrowException() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException
			{
				Account senderAccount = new Account();
				senderAccount.setAccountNo(101);
				senderAccount.setAmmount(5000);
				when(accountRepository.searchAccount(101)).thenReturn(senderAccount);
				
				Account receiverAccount = new Account();
				receiverAccount.setAccountNo(102);
				receiverAccount.setAmmount(1000);
				when(accountRepository.searchAccount(102)).thenReturn(receiverAccount);
				
				accountService.fundTransfer(senderAccount, receiverAccount,4700);
			}
			
			//Test Case for Transfer::: when account no is not valid
			@Test(expected=com.cg.bank.exception.InvalidAccountNumberException.class)
			public void whenAccountNumberIsInvalidShouldThrowException() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException
			{
				Account senderAccount = new Account();
				senderAccount.setAccountNo(1001);
				senderAccount.setAmmount(5000);
				when(accountRepository.searchAccount(1001)).thenReturn(senderAccount);
				
				Account receiverAccount = new Account();
				receiverAccount.setAccountNo(1002);
				receiverAccount.setAmmount(1000);
				when(accountRepository.searchAccount(1002)).thenReturn(receiverAccount);
				
				accountService.fundTransfer(senderAccount, receiverAccount,4500);
			}
			
			
			//Test Case for Transfer::: when transfer details are valid
			@Test
			public void whenAccountNumberIsValidShouldTransferSuccessfully() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException
			{
				Account senderAccount = new Account();
				senderAccount.setAccountNo(101);
				senderAccount.setAmmount(5000);
				when(accountRepository.searchAccount(101)).thenReturn(senderAccount);
				
				Account receiverAccount = new Account();
				receiverAccount.setAccountNo(102);
				receiverAccount.setAmmount(1000);
				when(accountRepository.searchAccount(102)).thenReturn(receiverAccount);
				
				boolean status = accountService.fundTransfer(senderAccount,receiverAccount,400);
				assertTrue(status);
			}
}
