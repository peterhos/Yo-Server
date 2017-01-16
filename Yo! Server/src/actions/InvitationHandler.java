package actions;

import java.io.IOException;
import java.time.LocalDateTime;

import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.InvitationConfirmation;
import transferDataContainers.NewFriend;
import transferDataContainers.User;

public class InvitationHandler {
	private DatabaseConnector dbConnector = null;
	
	public InvitationHandler(DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void handle(InvitationConfirmation invitationConfirmation) {
		User sender = invitationConfirmation.getSender();
		User receiver = invitationConfirmation.getReceiver();
		
		if(invitationConfirmation.isConfirmed()) {
			dbConnector.addFriend(sender, receiver, LocalDateTime.now());
			
			if(OnlineUser.isOnline(sender.getUserName())) {
				sendNewFriend(sender.getUserName());
			}
			
			if(OnlineUser.isOnline(receiver.getUserName())) {
				sendNewFriend(receiver.getUserName());
			}
		} 
		dbConnector.deleteNotification(sender, receiver);
	}
	
	public void sendNewFriend(String username) {
		OnlineUser user = OnlineUser.findUser(username);
		User person = dbConnector.getUserInfo(username);
		NewFriend newFriend = new NewFriend(person);
		Sender sender = new Sender(user.getOut());
		
		try {
			sender.send(newFriend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}