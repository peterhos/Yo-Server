package transferDataContainers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LoginCredentials implements Serializable{
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	
	public LoginCredentials(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public void sendCredentials(ObjectOutputStream out) throws IOException {
		out.writeObject(this);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
