package server;

import java.util.*;

public class Account {
	private static final float CREDITLIMIT = -1000;

	private String name;
	private String address;
	private int accountNum;
	private String username;
	private String password;
	private float balance;
	
	private LinkedList<Transaction> transactions;

	public Account(String name, String address, String username, String password) {
		this.name = name;
		this.address = address;
		this.username = username;
		this.password = password;	
		this.accountNum = this.createAccountNumber();
		this.balance = 0;
		
		setTransactions(new LinkedList<>());
	}
	
	public void lodge(float amount) {
		this.balance += amount;
	}
	
	public void withdraw(float amount) {
		if(this.balance - amount >= CREDITLIMIT)
			this.balance -= amount;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int createAccountNumber() {
		int num = 0;
		
		Random rnd = new Random();
		num = 100000 + rnd.nextInt(900000);
		
		return num;
	}

	public LinkedList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(LinkedList<Transaction> transactions) {
		this.transactions = transactions;
	}
}
