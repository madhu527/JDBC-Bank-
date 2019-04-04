 package com.capgemini.bankappdao;

import java.util.List;

import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountDao {
	public double getBalance(long accountId);
	
	public void updateBalance(long accountId,double newBalance);
	
	public boolean deleteBankaccount(long accountId);
	public BankAccount searchBankAccount(long accountId);
	public boolean addNewBankAccount(BankAccount account);
	public List<BankAccount>findAllBankaccount();

	public boolean updateAccount(long accountId, String accountType, String accountHolderName);		

}

		
	