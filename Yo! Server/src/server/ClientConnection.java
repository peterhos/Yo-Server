package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controllers.ServerController;
import data.Contact;
import data.OnlineUser;
import transfer.Listener;

public class ClientConnection implements Runnable {
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private Listener listener = null;
	private ServerController serverController = null;
	private Contact connection;
	private volatile boolean isRunning = true;
	private Thread clientThread;
	
	public ClientConnection(Socket socket, ServerController serverController) {
		this.serverController = serverController;
		this.socket = socket;
		clientThread = new Thread(this,"");
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
			listener = new Listener();
			connection = new Contact(socket);
			
			serverController.addContact(connection);
			serverController.printLogText("Connection established. "
            		+ "\n\tPort: " + socket.getPort()
            		+ "\n\tInetAddress: " + socket.getInetAddress()
            		+ "\n\tSocketChannel: " + socket.getChannel());
			clientThread.start();
		} catch (IOException e) {
			serverController.printLogText("Closing connection to user because of some fail.");
			closeConnection();
		} 
	}
	
	public void run() {
		try {
			while(isRunning) {
				listener.listen(in, out, serverController, this);
			}
		} catch (EOFException e) {
			serverController.printErrorText(e.getMessage());
		} catch (ClassNotFoundException e) {
			serverController.printErrorText("Problem with casting class. Provided class can be incompatible.");
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
		} catch (IOException e) {
			serverController.printErrorText("Closing connection failed !");
			serverController.showStackTraceDialog(e);
		}
	}
	
	public void userToOffline() {
		OnlineUser.removeOnlineUser(out);
	}
}
