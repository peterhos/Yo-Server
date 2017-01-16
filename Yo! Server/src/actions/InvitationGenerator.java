package actions;

import java.io.IOException;
import java.sql.SQLException;

import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Invitation;

public class InvitationGenerator {
	
	public void generate(Invitation invitation) {
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			String receiver = invitation.getReceiver().getUserName();
			
			if(OnlineUser.isOnline(receiver)){
				OnlineUser invitationReceiver = OnlineUser.findUser(receiver);
				Sender sender = new Sender(invitationReceiver.getOut());
				sender.send(invitation);
			} 
			dbConnector.saveInvitation(invitation);
			dbConnector.close();
		} catch (IOException e) {
			System.err.println("Problems with generating invitation. Failed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
