package transferDataContainers;

import java.io.Serializable;

public class InvitationConfirmation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private User sender;
	private User receiver;
	private boolean isConfirmed;
	
	public InvitationConfirmation(User sender, User receiver, boolean confirmation) {
		this.sender = new User(sender);
		this.receiver = new User(receiver);
		this.isConfirmed = confirmation;
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

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}
