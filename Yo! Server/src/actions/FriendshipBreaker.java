package actions;

import java.sql.SQLException;

import database.DatabaseConnector;
import transferDataContainers.EndOfFriendship;

public class FriendshipBreaker {

	public void breakFriendship(EndOfFriendship eof) {
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
		
			dbConnector.deleteFriendship(eof.getUser(), eof.getFriend());
		
			dbConnector.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
