package server;

import java.util.*;

public class AccountsManager {
	
	private ArrayList<Account> accountList;
	private Account userAccount;
	
	public AccountsManager(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}
	
	public void registerAccount(String name, String address, String username, String password) {
		accountList.add(new Account(name, address, username, password));
	}
	
	public boolean login(String username, String password) {
		for(Account acc : accountList) {
			if(username.equals(acc.getUsername()) && password.equals(acc.getPassword())) {	
				this.setUserAccount(acc);
				return true;
			}
		} //end for
		return false;
	}
	
	public void editCustomerDetails(String name, String address, String username, String password) {
		userAccount.setName(name);
		userAccount.setAddress(address);
		userAccount.setUsername(username);
		userAccount.setPassword(password);
	}
	
	public void makeLodgement(float amount) {
		userAccount.lodge(amount);
	}
	
	public void makeWithdrawal(float amount) {
		userAccount.withdraw(amount);
	}
	
	public float requestBalance() {
		return userAccount.getBalance();
	}

	public Account getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}
	
	public void recordTransaction(String transaction, float amount) {
		//If the list has reached 10 then poll from the head
		if(userAccount.getTransactions().size() == 10) {
			userAccount.getTransactions().poll();
		}
		//Offer to the tail
		userAccount.getTransactions().offer(new Transaction(transaction, amount));
	}
	
	public LinkedList<Transaction> getTransactions() {
		return userAccount.getTransactions();
	}
}
