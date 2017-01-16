package transferDataContainers;

import java.io.Serializable;
import java.util.ArrayList;

public class FoundedUsers implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<User> foundedUsers;

	public FoundedUsers(ArrayList<User> foundedUsers) {
		this.foundedUsers = new ArrayList<User>(foundedUsers);
	}

	public ArrayList<User> getFoundedUsers() {
		return foundedUsers;
	}

	public void setFoundedUsers(ArrayList<User> foundedUsers) {
		this.foundedUsers = foundedUsers;
	}
	
}