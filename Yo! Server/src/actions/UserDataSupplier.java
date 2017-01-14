package actions;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Invitation;
import transferDataContainers.Message;
import transferDataContainers.User;
import transferDataContainers.UserData;
import transferDataContainers.UserDataRequest;

public class UserDataSupplier {
	DatabaseConnector dbConnector;
	Sender sender;
	
	public UserDataSupplier(ObjectOutputStream out, DatabaseConnector dbConnector) {
		this.sender = new Sender(out);
		this.dbConnector = dbConnector;
	}
	
	public void deliver(UserDataRequest dataRequest) throws IOException {
		User user;
		ArrayList<User> friends;
		ArrayList<Message> unreadMessages;
		ArrayList<Invitation> invitations;
		String username = dataRequest.getUser().getUserName();
		
		user = new User(dbConnector.getUserInfo(username));
		friends = new ArrayList<User>(dbConnector.getUserFriends(username));
		unreadMessages = new ArrayList<Message>(dbConnector.getUnreadedMessages(username));   
		invitations = new ArrayList<Invitation>(dbConnector.getInvitations(username));
		
		sender.send(new UserData(user, friends, unreadMessages, invitations));
	}
}
