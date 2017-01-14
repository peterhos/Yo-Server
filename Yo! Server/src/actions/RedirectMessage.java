package actions;

import java.io.IOException;

import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Message;

public class RedirectMessage {
	DatabaseConnector dbConnector;
	Sender sender;
	
	public RedirectMessage(DatabaseConnector dbConnector) {
		this.dbConnector = dbConnector;
	}
	
	public void redirectMessage(Message message){
		try {
			String rec = message.getReceiver();
			if(OnlineUser.isOnline(rec)){
				OnlineUser receiver = OnlineUser.findUser(message.getReceiver());
				Sender sender = new Sender(receiver.getOut());
				sender.send(message);
				message.setReceived(true);
			} 
			dbConnector.saveMessage(message);
		} catch (IOException e) {
			System.err.println("Problems with sending message. Failed!");
		}
	}
}
