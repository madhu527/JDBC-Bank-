package com.capgemini.bankapp.service;

import java.util.List;

//import java.util.List;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountService {
	public double checkBalance(long accountId) throws BankAccountNotFoundException;
	
	public double withdraw(long accountId,double amount) throws LowBalanceException, BankAccountNotFoundException;
	public double deposit(long accountId,double amount) throws BankAccountNotFoundException;
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException;
	
		
	public boolean addNewBankAccount(BankAccount account);
	
	
	public BankAccount searchBankAccount(long accountId) throws BankAccountNotFoundException;

	double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException, BankAccountNotFoundException;	
	public List<BankAccount> findAllBankAccounts();

	boolean updateAccount(long accountId,String accountHolderName, String accountType);

	 
	
	
}


