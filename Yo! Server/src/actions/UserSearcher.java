package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.FoundedUsers;
import transferDataContainers.User;

public class UserSearcher {
	Sender sender;
	
	public UserSearcher(ObjectOutputStream out) {
		sender = new Sender(out);
	}
	
	public void search(User user){
		
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			
			FoundedUsers foundedUsers = new FoundedUsers(dbConnector.getUsers(user.getUserName()));
			
			sender.send(foundedUsers);
			
			dbConnector.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}