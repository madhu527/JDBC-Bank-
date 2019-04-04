package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountNotFoundException;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;
import com.capgemini.bankappdao.BankAccountDao;
import com.capgemini.bankappdao.impl.BankAccountDaoImpl;

public class BankAccountClient {
	
	static final Logger logger = Logger.getLogger(BankAccountClient.class);
	
	public static void main(String[] args) throws BankAccountNotFoundException {
		

		int choice;
		long accountId;
		String accountHolderName;
		String accountType;
		double accountBalance;
		BankAccountDao accountDao=new BankAccountDaoImpl();
		BankAccountService bankservice = new BankAccountServiceImpl();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1. Add New BankAccount\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
				System.out.println("5. Delete BankAccount\n6. Display All BankAccount Details");
				System.out.println("7. Search BankAccount\n8. Check Balance\n9. Update Details\n10. Exit\n");
				System.out.print("Please enter your choice = ");

				choice = Integer.parseInt(reader.readLine());

				double amount; 
				switch (choice) {

				case 1:
					System.out.println("Enter account holder name: ");
					accountHolderName = reader.readLine();
					System.out.println("Enter account type: ");
					accountType = reader.readLine();
					System.out.println("Enter account balance: ");
					accountBalance = Double.parseDouble(reader.readLine());
					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
					if (bankservice.addNewBankAccount(account))
						System.out.println("Account created successfully...\n");
					else
						System.out.println("failed to create new account...\n");
					break;
				case 2:
		
					System.out.println("Enter the account ID");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the amount to be withdrawn");
					amount = Double.parseDouble(reader.readLine());
					
					try {
						bankservice.withdraw(accountId, amount);
					} catch (LowBalanceException e3) {
						System.out.println("Insufficient Fund");
						e3.printStackTrace();
					} catch (BankAccountNotFoundException e3) {
						System.out.println("Bank account doesn't exsist");
						//e3.printStackTrace();
					}
					break;	
				case 3:
					
					System.out.println("Enter the account ID");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the amount to be deposited");
					amount = Double.parseDouble(reader.readLine());
					try {
						bankservice.deposit(accountId, amount);
					} catch (BankAccountNotFoundException e2) {
						e2.printStackTrace();
					}
					break;
					
					case 4:
						
						System.out.println("enter accountId to transfer");
						long fromAccount = Long.parseLong(reader.readLine());
						System.out.println(" transfer to  ");
						long toAccount = Long.parseLong(reader.readLine());
						System.out.println(" Enter amount to transfer ");
						amount=  Double.parseDouble(reader.readLine());
					try {
						System.out.println("amount transferred successfully" +bankservice.fundTransfer(fromAccount, toAccount, amount));
					} catch (LowBalanceException e) {
						e.printStackTrace();
					}
						
						break;
						 			
				case 5:
					 
					System.out.println("Enter the bank account ID of the account to be deleted");
					accountId = Long.parseLong(reader.readLine());
					try {
						bankservice.deleteBankAccount(accountId);
					} catch (BankAccountNotFoundException e1) {
						e1.printStackTrace();
					}
					break;
					
					
					
				case 6:
					//System.out.println("Display all bank account details");
					bankservice.findAllBankAccounts();
					break;
					
					
				case 7:
					 
					System.out.println("Enter the ID of the bank account to be searched");
					accountId = Long.parseLong(reader.readLine());
					try {
						bankservice.searchBankAccount(accountId);
					} catch (BankAccountNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 8:
					System.out.println("Enter the bank account ID to check balance");
					accountId = Long.parseLong(reader.readLine());
					try {
						bankservice.checkBalance(accountId);
					} catch (BankAccountNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

					
					

				case 9:
					System.out.println("Enter the bank account ID to update");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("enter the account type");
					accountType = reader.readLine();
					System.out.println("enter the new name");
					accountHolderName = reader.readLine();

					bankservice.updateAccount(accountId,accountHolderName,accountType);
					break;
					
				case 10:
					System.out.println("Thanks for banking with us.");
					System.exit(0);
					break;
				}
			}
		} catch (IOException e) {
		//	e.printStackTrace();
		logger.error("Exception:",e);
		}
		
	}
}
