package transferDataContainers;

import java.io.Serializable;
import java.util.ArrayList;

public class UnreadMessages implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Message> unreadMessages;
	
	public UnreadMessages(ArrayList<Message> unreadMessage) {
		this.unreadMessages = new ArrayList<Message>(unreadMessage);
	}

	public ArrayList<Message> getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(ArrayList<Message> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
