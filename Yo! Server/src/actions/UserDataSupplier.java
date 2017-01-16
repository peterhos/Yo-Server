package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;


import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Friends;
import transferDataContainers.Invitation;
import transferDataContainers.Message;
import transferDataContainers.OverdueInvitations;
import transferDataContainers.UnreadMessages;
import transferDataContainers.User;
import transferDataContainers.UserDataRequest;

public class UserDataSupplier {
	Sender sender;
	
	public UserDataSupplier(ObjectOutputStream out) {
		this.sender = new Sender(out);
	}
	
	public void deliver(UserDataRequest dataRequest) throws IOException {
		User user;
		ArrayList<User> friends;
		ArrayList<Message> unreadMessages;
		ArrayList<Invitation> invitations;
		String username = dataRequest.getUser().getUserName();
		
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			
			user = new User(dbConnector.getUserInfo(username));
			friends = new ArrayList<User>(dbConnector.getUserFriends(username));
			unreadMessages = new ArrayList<Message>(dbConnector.getUnreadedMessages(username));   
			invitations = new ArrayList<Invitation>(dbConnector.getInvitations(username));
			
			Friends userFriends = new Friends(friends);
			UnreadMessages userUnreadMessages = new UnreadMessages(unreadMessages);
			OverdueInvitations userInvitations = new OverdueInvitations(invitations);
			
			sender.send(user);
			sender.send(userFriends);
			sender.send(userUnreadMessages);
			sender.send(userInvitations);
			
			dbConnector.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
