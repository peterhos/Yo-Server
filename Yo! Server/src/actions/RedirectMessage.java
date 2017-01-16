package actions;

import java.io.IOException;
import java.sql.SQLException;

import data.OnlineUser;
import database.DatabaseConnector;
import transfer.Sender;
import transferDataContainers.Message;

public class RedirectMessage {
	
	public void redirectMessage(Message message){
		try {
			DatabaseConnector dbConnector = new DatabaseConnector();
			String rec = message.getReceiver();
			if(OnlineUser.isOnline(rec)){
				OnlineUser receiver = OnlineUser.findUser(message.getReceiver());
				Sender sender = new Sender(receiver.getOut());
				sender.send(message);
				message.setReceived(true);
			} 
			dbConnector.saveMessage(message);
			dbConnector.close();
		} catch (IOException e) {
			System.err.println("Problems with sending message. Failed!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
