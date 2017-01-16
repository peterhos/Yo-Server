package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Confirmation;
import transferDataContainers.RegistrationInformation;


public class Registrant {
	Sender sender;
	
	public Registrant(ObjectOutputStream out) {
		sender = new Sender(out);
	}
	
	public void registerNewUser(RegistrationInformation input) {
		Confirmation reply = new Confirmation();
		String nick = input.getNick();
		
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			
			if(!(dbConnector.userExists(nick))){		
				dbConnector.addNewUser(input);
				reply.setMessage("User added successfully");
				reply.setConfirmed(true);
			} else {
				reply.setMessage("User with this nick already exist in database.");
			}
			dbConnector.close();
			sender.send(reply);
		} catch (IOException e) {
			System.err.println("Couldn't send a reply to client");
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println("Something went wrong with database connection");
			System.err.println(e.getMessage());
		}
	}
}