package transferDataContainers;

import java.io.Serializable;

public class EditedUserData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	public EditedUserData(User user) {
		user = new User(user);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
