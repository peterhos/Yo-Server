package actions;

import java.sql.SQLException;

import database.DatabaseConnector;
import transferDataContainers.User;
import transferDataContainers.UserToRemove;

public class UserRemover {
	
	public void remove(UserToRemove usr) {
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			User user = usr.getUser();
			dbConnector.deleteFriendship(user);
			dbConnector.deleteUserNotifications(user);
			dbConnector.deleteUserMessages(user);
			dbConnector.deleteUser(user);
			
			dbConnector.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
