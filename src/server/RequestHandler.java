package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler extends Thread {
	
	private Socket clientSocket;
	private AccountsManager accountsManager;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public RequestHandler(Socket clientSocket, ArrayList<Account> accountList) throws IOException {
		this.clientSocket = clientSocket;
		this.accountsManager = new AccountsManager(accountList);		
		this.in = new ObjectInputStream(this.clientSocket.getInputStream());
		this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
	}
	
	@Override
	public void run() {
		//Variables
		String name, address, username, password;
		float amount, balance;
		boolean running = true;
		
		//Keep the connection going
		while(running) {
			try {
				String request = (String) in.readObject();
				
				switch(request) {
					case "REGISTER":				
						name = (String) in.readObject();
						address = (String) in.readObject();
						username = (String) in.readObject();
						password = (String) in.readObject();
						
						//Register the account - i.e Create new account and add to account list
						accountsManager.registerAccount(name, address, username, password);
						
						out.writeObject(new String("Success! \nThank you for registering."));
						out.flush();
						break;
					case "LOGIN":
						boolean authenticate = false;
						
						username = (String) in.readObject();
						password = (String) in.readObject();
						
						//Attempt to login
						authenticate = accountsManager.login(username, password);
						
						out.writeBoolean(authenticate);
						out.flush();
						break;
					case "BALANCE":
						//Return the balance of the logged in account
						balance = accountsManager.requestBalance();
						out.writeFloat(balance);
						out.flush();
						break;
					case "EDIT":
						name = (String) in.readObject();
						address = (String) in.readObject();
						username = (String) in.readObject();
						password = (String) in.readObject();
						
						//Edit user account details
						accountsManager.editCustomerDetails(name, address, username, password);

						//Send response
						String response = "Successfully updated details";
						out.writeObject(response);
						out.flush();
						break;
					case "LODGE":
						//Lodge +balance
						amount = in.readFloat();
						
						accountsManager.makeLodgement(amount);
						
						balance = accountsManager.requestBalance();
						out.writeFloat(balance);
						out.flush();
						
						accountsManager.recordTransaction("Lodgement", amount);
						break;
					case "WITHDRAW":
						//Withdraw -balance
						amount = in.readFloat();
						
						accountsManager.makeWithdrawal(amount);
						
						balance = accountsManager.requestBalance();
						out.writeFloat(balance);
						out.flush();
						
						accountsManager.recordTransaction("Withdrawal", amount);
						break;
					case "TRANSACTIONS":
						//Data structure containing 10 transactions
						LinkedList<Transaction> transactions = accountsManager.getTransactions();
						
						out.writeInt(transactions.size());
						
						for(Transaction t : transactions) {
							out.writeObject(t.toString());
						}
						out.flush();
						break;
					case "QUIT":
						running = false;
						break;
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} //end while
		
		//Close the socket connection
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} //end run()
}
