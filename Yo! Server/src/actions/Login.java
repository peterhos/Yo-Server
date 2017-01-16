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
	DatabaseConnector dbConnector;
	Sender sender;
	ServerController serverController;
	
	public Login(ObjectOutputStream out, DatabaseConnector dbConnector, ServerController serverController) {
		this.sender = new Sender(out);
		this.dbConnector = dbConnector;
		this.serverController = serverController;
	}
	
	public void checkCredentials(LoginCredentials credentials) throws IOException {
		String password = null;
		Confirmation reply = new Confirmation();
		
		try {
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
			
			sender.send(reply);
		} catch (SQLException e) {
			serverController.printErrorText("Something went wrong with database connection");
			serverController.printErrorText(e.getMessage());
		}
	}
}
