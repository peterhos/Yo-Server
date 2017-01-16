package transferDataContainers;

import java.io.Serializable;
import java.util.ArrayList;

public class Friends implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<User> friends;
	
	public Friends(ArrayList<User> friends) {
		this.friends = new ArrayList<User>(friends);
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}
}
