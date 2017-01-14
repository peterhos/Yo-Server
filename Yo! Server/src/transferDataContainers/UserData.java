package transferDataContainers;

import java.util.ArrayList;

public class UserData {
	private User user;
	private ArrayList<User> friends;
	private ArrayList<Message> unreadMessages;
	private ArrayList<Invitation> invitations;
	
	public UserData(User user, ArrayList<User> friends, 
			ArrayList<Message> unreadMessages, ArrayList<Invitation> invitations) {
		this.user = new User(user);
		this.friends = new ArrayList<User>(friends);
		this.unreadMessages = new ArrayList<Message>(unreadMessages);
		this.invitations = new ArrayList<Invitation>();
	}

	public User getUser() {
		return user;
	}

	public ArrayList<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public ArrayList<Message> getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(ArrayList<Message> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
