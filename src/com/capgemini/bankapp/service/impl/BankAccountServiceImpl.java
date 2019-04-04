package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.client.Dbutil;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountNotFoundException;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankappdao.BankAccountDao;
import com.capgemini.bankappdao.impl.BankAccountDaoImpl;

public class BankAccountServiceImpl implements BankAccountService {
	static final Logger logger = Logger.getLogger(BankAccountServiceImpl.class);

	private BankAccountDao bankAccountDao;

	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) {
		return bankAccountDao.getBalance(accountId);
	}

	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank account doesnot exist..");

		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			Dbutil.commit();
			return balance;
		} else

			throw new LowBalanceException("You don't have sufficient fund...");
	}

	public double withdrawForFundTransfer(long accountId, double amount)
			throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank account doesnot exist..");

		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			Dbutil.commit();
			return balance;
		} else

			throw new LowBalanceException("You don't have sufficient fund...");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank account doesnot exist..");
		balance = balance + amount;
		bankAccountDao.updateBalance(accountId, balance);
		Dbutil.commit();

		return balance;
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount)
			throws LowBalanceException, BankAccountNotFoundException {
		try {
			double newBalance = withdrawForFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			Dbutil.commit();
			return newBalance;
		} catch (LowBalanceException | BankAccountNotFoundException e) {
			logger.error("Exception", e);
			Dbutil.rollback();
			throw e;

		}
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {

		boolean result = bankAccountDao.deleteBankaccount(accountId);
		if (result) {
			Dbutil.commit();
			return result;
		}
		throw new BankAccountNotFoundException("Bank account doesnt exist");

	}

	@Override
	public List<BankAccount> findAllBankAccounts() {

		return bankAccountDao.findAllBankaccount();

	}
	@Override
	public BankAccount searchBankAccount(long accountId) throws BankAccountNotFoundException {
		BankAccount result = bankAccountDao.searchBankAccount(accountId);
		if (result != null)
			return result;
		else
			throw new BankAccountNotFoundException("BankAccount Doesn't exist..");
	}



	
	@Override
	public boolean updateAccount(long accountId,String accountHolderName, String accountType) {
		boolean result= bankAccountDao.updateAccount(accountId, accountType, accountHolderName);
		if(result) 
				Dbutil.commit();
			return result;
		}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result = bankAccountDao.addNewBankAccount(account);
		if (result)
			Dbutil.commit();
		return result;
	}
}
