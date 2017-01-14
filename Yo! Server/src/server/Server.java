package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import controllers.ServerController;

public class Server extends Thread{
	private int serverPortNumber;
	private ServerSocket serverSocket = null;
	private ServerController serverController;
	
	private static List<ClientConnection> clients = new ArrayList<ClientConnection>();
	
	public Server (int serverPortNumber, ServerController serverController) {
		this.serverPortNumber = serverPortNumber;
		this.serverController = serverController;
		start();
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(this.getServerPortNumber());
			serverController.printLogText("Server is up & ready for connections");
              
        	while (true) {		
                listen(); 	
            }
		} catch (IOException e) {
			stopClients();
			serverController.printLogText("Server stopped.");
			serverController.printErrorText(e.getMessage() + ". Server stopped.");
		}
	}
	
	public void listen() throws IOException {
		Socket clientSocket = serverSocket.accept();                                                              
        
        ClientConnection client = new ClientConnection(clientSocket, serverController); 
        clients.add(client);
	}
	
	public void stopServer() throws IOException {
		if(!serverSocket.isClosed())
			serverSocket.close();
	}
	
	private void stopClients() {
		for(ClientConnection cc : clients) {
			cc.stopClient();
		}
		
		clients.removeAll(clients);
	}
	
	public static void deleteClient(ClientConnection client) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).equals(client))
				clients.remove(i);
		}
	}
	
	public int getServerPortNumber() {
		return serverPortNumber;
	}
	
	public void setServerPortNumber(int serverPortNumber) {
		this.serverPortNumber = serverPortNumber;
	}
}
