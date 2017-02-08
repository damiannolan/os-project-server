package server;

public class ServerRunner {
	
	public static void main(String[] args) {
		
		final int PORT = 8080;
		
		new BankServer(PORT);
	}
}
