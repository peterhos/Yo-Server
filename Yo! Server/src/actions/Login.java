package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

import controllers.ServerController;
import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Confirmation;
import transferDataContainers.LoginCredentials;

public class Login {
	Sender sender;
	ServerController serverController;
	
	public Login(ObjectOutputStream out, ServerController serverController) {
		this.sender = new Sender(out);
		this.serverController = serverController;
	}
	
	public void checkCredentials(LoginCredentials credentials) throws IOException {
		String password = null;
		Confirmation reply = new Confirmation();
		
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			
			if(dbConnector.userExists(credentials.getLogin())){
				password = dbConnector.getUserPassword(credentials.getLogin());
				if(credentials.getPassword().equals(password)){
					reply.setConfirmed(true);
					reply.setMessage("User " + credentials.getLogin() + " log in successfully!");
					serverController.printLogText("User " + credentials.getLogin() + " log in successfully!");
					
					new OnlineUser(credentials.getLogin(), sender.getOut());
				} else {
					reply.setMessage("Incorrect password");
				}
			}else {
				reply.setMessage("There is no user as: " + credentials.getLogin());
			}
			dbConnector.close();
			sender.send(reply);
		} catch (SQLException e) {
			serverController.printErrorText("Something went wrong with database connection");
			serverController.printErrorText(e.getMessage());
		} 
	}
}
