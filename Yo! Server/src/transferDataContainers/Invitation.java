package transferDataContainers;

import java.io.Serializable;

public class Invitation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private User sender;
	private User receiver;
	
	public Invitation(User sender, User receiver) {
		this.sender = sender;
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
}
