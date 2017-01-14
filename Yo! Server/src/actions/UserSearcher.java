package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.User;

public class UserSearcher {
	DatabaseConnector dbConnector;
	Sender sender;
	
	public UserSearcher(ObjectOutputStream out, DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
		sender = new Sender(out);
	}
	
	public void search(User user){
		
		try {
			ArrayList<User> foundedUsers = new ArrayList<User>(dbConnector.getUsers(user.getUserName()));
			
			sender.send(foundedUsers);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
