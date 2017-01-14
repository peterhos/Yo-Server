package transferDataContainers;

import java.io.Serializable;

public class UserDataRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserDataRequest(User user) {
		this.user = new User(user);
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
