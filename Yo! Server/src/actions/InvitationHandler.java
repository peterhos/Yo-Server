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
		if(invitationConfirmation.isConfirmed()) {
			dbConnector.addFriend(invitationConfirmation.getSender(), 
									invitationConfirmation.getReceiver(), 
									LocalDateTime.now());
			
			
			if(OnlineUser.isOnline(invitationConfirmation.getSender().getUserName())) {
				sendNewFriend(invitationConfirmation.getSender().getUserName());
			}
			
			if(OnlineUser.isOnline(invitationConfirmation.getReceiver().getUserName())) {
				sendNewFriend(invitationConfirmation.getReceiver().getUserName());
			}
		} 
		
		dbConnector.deleteNotification(invitationConfirmation.getSender(), 
											invitationConfirmation.getReceiver());
		
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