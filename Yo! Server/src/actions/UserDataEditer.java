package actions;

import java.sql.SQLException;

import database.DatabaseConnector;
import transferDataContainers.EditedUserData;

public class UserDataEditer {
	
	public void edit(EditedUserData editedData) {
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			dbConnector.updateUserInfo(editedData.getUser());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
