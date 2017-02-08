package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class BankServer {
	private ArrayList<Account> accountList = new ArrayList<>();
	private ServerSocket serverSocket;
	
	private boolean running = true;
	
	public BankServer(int port) {
		
		//Instantiate the server socket listening on port number
		try {
			this.serverSocket = new ServerSocket(port);
			
			//Create a new thread with a Listener runnable
			Thread server = new Thread(new Listener(), "Request Listener");
			server.setPriority(Thread.MAX_PRIORITY);
			server.start();
			
			System.out.println("Server started and listening on port " + port);
			
		} catch (IOException e) {
			System.out.println("Error starting server...");
			e.printStackTrace();
		}
	}
	
	private class Listener implements Runnable{ //A Listener IS-A Runnable
		
		@Override
		public void run() {
			while (running) {
				try {
					//Blocking method, causing the thread to wait for an incoming client request
					Socket clientSocket = serverSocket.accept();
					
					//This is the entry point for a client request
					System.out.println("Accepted " + clientSocket.getInetAddress().getHostName());
			
					//Pass the clientSocket to a request handler object and start thread
					RequestHandler reqHandler = new RequestHandler(clientSocket, accountList);
					reqHandler.start(); //call start() to call run()
					
				} catch (Exception e) {
					System.out.println("Error handling incoming request..." + e.getMessage());
				}
			} //end while
		}
	}//End of inner class Listener	
}
