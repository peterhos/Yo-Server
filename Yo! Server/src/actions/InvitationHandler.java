package actions;

import java.time.LocalDateTime;

import database.DatabaseConnector;
import transferDataContainers.InvitationConfirmation;

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
		} else {
			dbConnector.deleteNotification(invitationConfirmation.getSender(), 
											invitationConfirmation.getReceiver());
		}
	}
}