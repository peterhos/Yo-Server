package actions;

import java.sql.SQLException;

import database.DatabaseConnector;
import transferDataContainers.EditedUserData;
import transferDataContainers.User;

public class UserDataEditer {
	
	public void edit(EditedUserData editedData) {
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			User usr = new User(editedData.getUser());
			
			dbConnector.updateUserInfo(usr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
