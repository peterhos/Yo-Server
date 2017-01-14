package transferDataContainers;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName = null;
	private String firstName = null;
	private String lastName = null;
	private String city = null;
	private String country = null;
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(User user) {
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.city = user.getCity();
		this.country = user.getCountry();
	}
	
	public User(String username, String firstName, String lastName, String city, String country) {
		this.userName = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.country = country;
	}

	public String toString() {
		return "\nUserName: " + userName
				+ "\nFirst Name: " + firstName
				+ "\nLast Name: " + lastName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
