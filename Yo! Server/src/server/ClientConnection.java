package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import controllers.ServerController;
import data.Contact;
import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Listener;


public class ClientConnection extends Thread {
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private DatabaseConnector dbConnector = null;
	private Listener listener = null;
	private ServerController serverController = null;
	private Contact connection;
	private volatile boolean isRunning = true;
	
	private String dbEngine = "mysql";
	private String ip = "127.0.0.1";
	private String port = "3306";
	private String schema = "YoDB";
	private String user = "ServerSquad";
	private String password = "Server1.Conn";
	
	public ClientConnection(Socket socket, ServerController serverController) {
		
		try {
			this.serverController = serverController;
			this.socket = socket;
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			listener = new Listener();
			dbConnector = new DatabaseConnector(dbEngine, ip, port, schema, user, password);
			connection = new Contact(socket);
			serverController.addContact(connection);
			
			serverController.printLogText("Connection established. "
            		+ "\n\tPort: " + socket.getPort()
            		+ "\n\tInetAddress: " + socket.getInetAddress()
            		+ "\n\tSocketChannel: " + socket.getChannel());
			
			start();
		} catch (SQLException e) {
			serverController.printErrorText("Cannot connect to database.");
			serverController.printErrorText(e.getMessage());
			System.err.println("Cannot connect to database.");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			serverController.printLogText("Closing connection to user because of some fail.");
			closeConnection();
		} 
	}
	
	public void run() {
		try {
			while(isRunning) {
				listener.listen(in, out, dbConnector, serverController);
			}
		} catch (EOFException e) {
			serverController.printErrorText(e.getMessage());
		} catch (ClassNotFoundException e) {
			serverController.printErrorText("Problem with casting class. Provided class can be incompatible.");
			System.err.println("Problem with casting class. Provided class can be incompatible.");
		} catch (IOException e) {
			serverController.printErrorText(e.getMessage());
			serverController.showStackTraceDialog(e);
		} finally {
			serverController.printLogText("User logged out.");
			System.out.println("User logged out !");
			closeConnection();
			userToOffline();
		}
	}
	
	public void stopClient() {
		isRunning = false;
	}
	
	public void closeConnection() {
		serverController.deleteContact(connection);
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(socket != null)
				socket.close();
			if(dbConnector != null)
				dbConnector.close();
		} catch (IOException e) {
			serverController.printErrorText("Closing connection failed !");
			serverController.showStackTraceDialog(e);
		}
	}
	
	public void userToOffline() {
		OnlineUser.removeOnlineUser(out);
	}
}
