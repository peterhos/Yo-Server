package transferDataContainers;

import java.io.Serializable;

public class NewFriend implements Serializable {
	User friend;
	
	public NewFriend(User friend) {
		this.friend = new User(friend);
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
}
