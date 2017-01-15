package transferDataContainers;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String 	userName = null;
	private String 	firstName = null;
	private String 	lastName = null;
	private String 	eMail = null;
	private int 	age = 0;
	private String 	city = null;
	private String 	country = null;
	private String	gender = null;
	
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(User user) {
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.eMail = user.geteMail();
		this.age = user.getAge();
		this.city = user.getCity();
		this.country = user.getCountry();
		this.gender = user.getGender();
	}
	
	public User(String username, String firstName, String lastName,
			String eMail, int age, String city, String country, String gender) {
		this.userName = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.age = age;
		this.city = city;
		this.country = country;
		this.gender = gender;
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
	
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
