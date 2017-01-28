package transferDataContainers;

import java.io.Serializable;

public class EndOfFriendship implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private User friend;
	
	public EndOfFriendship(User user, User friend) {
		this.user = user;
		this.friend = friend;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
}
