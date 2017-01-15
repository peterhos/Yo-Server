package transferDataContainers;

public class NewFriend {
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
