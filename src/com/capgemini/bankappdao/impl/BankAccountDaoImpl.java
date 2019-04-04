package com.capgemini.bankappdao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.client.Dbutil;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankappdao.BankAccountDao;

public class BankAccountDaoImpl implements BankAccountDao {

	@Override
	public double getBalance(long accountId) {
		String query = "SELECT account_balance from bankaccounts where account_id=" + accountId;
		double balance = -1;

		Connection connection = Dbutil.getConnection();

		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			while (result.next())
				balance = result.getDouble(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;

	}

	@Override
	public void updateBalance(long accountId, double newBalance) {
		String query = "UPDATE bankaccounts SET account_balance=WHERE account_id=?" + accountId;
		try (Connection connection = Dbutil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, newBalance);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteBankaccount(long accountId) {
		String query = "Delete from bankaccounts WHERE accountId =" + accountId;
		int result;
		try (Connection connection = Dbutil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			result = statement.executeUpdate();
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		String query = "INSERT INTO bankaccounts (customer_name, account_type, account_balance) VALUES(?, ?, ?)";
		Connection connection = Dbutil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, account.getAccountHolderName());
			statement.setString(2, account.getAccountType());
			statement.setDouble(3, account.getAccountBalance());
			int result = statement.executeUpdate();
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BankAccount> findAllBankaccount() {
		String query = "SELECT * FROM bankaccounts";
		List<BankAccount> accounts = new ArrayList<>();

		try (Connection connection = Dbutil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {

			while (result.next()) {
				long accountId = result.getLong(1);
				String accountHolderName = result.getString(2);
				String accountType = result.getString(3);
				double accountBalance = result.getDouble(4);
				BankAccount account = new BankAccount(accountId, accountHolderName, accountType, accountBalance);
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public BankAccount searchBankAccount(long accountId) {
		String query = "SELECT * FROM bankaccounts where account_id= " + accountId;
		BankAccount account = null;
		Connection connection = Dbutil.getConnection();

		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {

			while (result.next())
				account = new BankAccount(result.getLong(1), result.getString(2), result.getString(3),
						result.getDouble(4));
			System.out.println(account);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;

	}
	 
	@Override
	public boolean updateAccount(long accountId, String accountType, String accountHolderName) {
		String query = "UPDATE bankaccounts set account_type=? , customer_name=? WHERE account_id= " + accountId;

		int result = 0;
		Connection connection = Dbutil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, accountType);
			statement.setString(2, accountHolderName);
			result = statement.executeUpdate();

			if (result == 1)
				System.out.println("Account Details Updated Successfully");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
	 

