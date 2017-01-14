package actions;

import java.io.IOException;

import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Invitation;

public class InvitationGenerator {
	private DatabaseConnector dbConnector;
	
	public InvitationGenerator(DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void generate(Invitation invitation) {
		try {
			String receiver = invitation.getReceiver().getUserName();
			if(OnlineUser.isOnline(receiver)){
				OnlineUser invitationReceiver = OnlineUser.findUser(receiver);
				Sender sender = new Sender(invitationReceiver.getOut());
				sender.send(invitation);
			} 
			dbConnector.saveInvitation(invitation);
		} catch (IOException e) {
			System.err.println("Problems with generating invitation. Failed!");
		}
	}
}
