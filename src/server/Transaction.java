package server;

import java.util.Date;

public class Transaction {
	private Date date;
	private String transaction;
	private float amount;
	
	public Transaction(String transaction, float amount) {
		this.transaction = transaction;
		this.amount = amount;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return transaction + " : " + amount + " on " + date;
	}
}
