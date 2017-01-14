package transferDataContainers;

import java.io.Serializable;

public class RegistrationInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String 	nick = null;
	private String 	password = null;
	private String 	firstName = null;
	private String 	lastName = null;
	private String 	eMail = null;
	private int 	age = -1;
	private String	gender = null;
	private String 	country = null;
	private String 	city = null;
	
	public RegistrationInformation() {
		
	}
	
	public RegistrationInformation(String nick, String password, String firstName, String lastName, String eMail,
			int age, String gender, String country, String city) {
		super();
		this.nick = nick;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.age = age;
		this.gender = gender;
		this.country = country;
		this.city = city;
	}

	public String toString(){
		return "\n" + nick
				+ "\n" + password;		
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
