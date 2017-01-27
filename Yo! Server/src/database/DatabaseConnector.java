package database;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controllers.ServerController;
import transferDataContainers.Invitation;
import transferDataContainers.Message;
import transferDataContainers.RegistrationInformation;
import transferDataContainers.User;


public class DatabaseConnector implements Closeable{
	private Connection databaseConnection = null;
	private String url;
	private String login;
	private String password;
	
	public DatabaseConnector() 
			throws SQLException{
		
		this.url = "jdbc:" + ServerController.dbEngine 
				+ "://" + ServerController.ip 
				+ ":" + ServerController.port 
				+ "/" + ServerController.schema 
				+ "?useSSL=true";
		this.login = ServerController.user;
		this.password = ServerController.password;
		
		databaseConnection = DriverManager.getConnection(url, login, password);
	}
	
	public void close() {
		try {
			databaseConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String user) throws SQLException  {
		if(databaseConnection.isClosed())
			databaseConnection = DriverManager.getConnection(url, login, password);
		
		PreparedStatement prepStatement = null;
		ResultSet myRs = null;
		int result = 1;
		try {
			final String checkQuery = "SELECT count(*) FROM YoDB.User WHERE Nick = ?";
			prepStatement = databaseConnection.prepareStatement(checkQuery);
			prepStatement.setString(1, user);
			myRs = prepStatement.executeQuery();
			
			if(myRs.next()){
				result = myRs.getInt(1);
			}
			prepStatement.close();
			myRs.close();
		} catch (SQLException e) {
			System.err.println("Something went wrong with execution SQL statement . Can't check if user exist.");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(result != 0)
			return true;
		return false;
		
	}
	
	public void updateUserInfo(User user) throws SQLException {
		PreparedStatement prepStatement = null;
		int userId = 0;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(user.getUserName());
			
			String sql = "UPDATE `YoDB`.`User` "
					+ "SET `FirstName`=?, "
						+ "`LastName`=?, "
						+ "`Email`=?, "
						+ "`Age`=?, "
						+ "`Country`=?, "
						+ "`City`=? "
					+ "WHERE `UserId`=?;";
			
			prepStatement = databaseConnection.prepareStatement(sql);
			
			prepStatement.setString(1, user.getFirstName());
			prepStatement.setString(2, user.getLastName());
			prepStatement.setString(3, user.geteMail());
			prepStatement.setInt(4, user.getAge());
			prepStatement.setString(5, user.getCountry());
			prepStatement.setString(6, user.getCity());
			prepStatement.setInt(7, userId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public User getUserInfo(int userId) {
		User user = new User(null,null,null,null,0,null,null,null);
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT * FROM YoDB.User WHERE UserId=?;";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, userId);
			
			result = prepStatement.executeQuery();
			
			if (result.next()) {
				user.setUserName(result.getString("Nick"));
				user.setFirstName(result.getString("FirstName"));
				user.setLastName(result.getString("LastName"));
				user.seteMail(result.getString("Email"));
				user.setAge(result.getInt("Age"));
				user.setCity(result.getString("City"));
				user.setCountry(result.getString("Country"));
				user.setGender(getGenderName(result.getInt("Gender_GenderId")));
			}
			
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem danych o userze");
		}
		
		return user;
	}
	
	public User getUserInfo(String username) {
		User user = new User(username);
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT * FROM YoDB.User WHERE Nick=?;";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setString(1, username);
			
			result = prepStatement.executeQuery();
			
			if (result.next()) {
				user.setUserName(result.getString("Nick"));
				user.setFirstName(result.getString("FirstName"));
				user.setLastName(result.getString("LastName"));
				user.seteMail(result.getString("Email"));
				user.setAge(result.getInt("Age"));
				user.setCity(result.getString("City"));
				user.setCountry(result.getString("Country"));
				user.setGender(getGenderName(result.getInt("Gender_GenderId")));
			}
			
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem danych o userze");
		}
		
		return user;
	}
	
	public int getUserId(String user) throws SQLException {
		if(databaseConnection.isClosed())
			databaseConnection = DriverManager.getConnection(url, login, password);
		
		int id = 0;
		PreparedStatement prepStatement = null;
		ResultSet result;
		
		try {
			prepStatement = databaseConnection.prepareStatement("SELECT UserId "
														+ "FROM YoDB.User "
														+ "where Nick = ?");
			prepStatement.setString(1, user);
			result = prepStatement.executeQuery();
			
			if(result.next())
				id = result.getInt("UserId");
			
			prepStatement.close();
			result.close();
			
		} catch (NullPointerException e) {
			System.err.println("Something went wrong in getUserId method");
		} catch (SQLException e) {
			System.err.println("getUserId method failed");
		}
		return id;
	}
	
	public void deleteUser(User user) {
		PreparedStatement prepStatement = null;
		int userId = 0;
		String sql = "DELETE FROM `YoDB`.`User` "
				+ "WHERE `UserId`=?;";
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(user.getUserName());
			prepStatement = databaseConnection.prepareStatement(sql);
			
			prepStatement.setInt(1, userId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFriendship(User user) {
		PreparedStatement prepStatement = null;
		int userId = 0;
		String sql = "DELETE FROM `YoDB`.`Friend` "
				+ "WHERE `User_UserId`=? OR `User_FriendId`=?;";
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(user.getUserName());
			prepStatement = databaseConnection.prepareStatement(sql);
			
			prepStatement.setInt(1, userId);
			prepStatement.setInt(2, userId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getUsers(String nick) {
		ResultSet myRs = null;
		PreparedStatement prepStatement = null;
		ArrayList<User> foundedUsers = new ArrayList<User>();
		
		String sql = "SELECT * FROM YoDB.User "
				+ "WHERE Nick LIKE ? "
					+ "OR FirstName LIKE ? "
					+ "OR LastName LIKE ? "
					+ "OR Email LIKE ? "
					+ "OR Country LIKE ? "
					+ "OR City LIKE ?;";
		String pattern = "%" + nick + "%";
		
		try {
			prepStatement = databaseConnection.prepareStatement(sql);
			prepStatement.setString(1, pattern);
			prepStatement.setString(2, pattern);
			prepStatement.setString(3, pattern);
			prepStatement.setString(4, pattern);
			prepStatement.setString(5, pattern);
			prepStatement.setString(6, pattern);
			
			myRs = prepStatement.executeQuery();
			
			while(myRs.next()) {
				String userName = myRs.getString("Nick");
				String firstName = myRs.getString("FirstName");
				String lastName = myRs.getString("LastName");
				String eMail = myRs.getString("Email");
				int age = myRs.getInt("Age");
				String city = myRs.getString("City");
				String country = myRs.getString("Country");
				String gender = getGenderName(myRs.getInt("Gender_GenderId"));
				
				
				User user = new User(userName, firstName, lastName, eMail, age, city, country, gender);
				foundedUsers.add(user);
			}
			
			prepStatement.close();
			myRs.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return foundedUsers;
	}
	
	public void addNewUser(RegistrationInformation newUser) throws SQLException {
		if(databaseConnection.isClosed())
			databaseConnection = DriverManager.getConnection(url, login, password);
		
		PreparedStatement prepStatement = null;
		try {
			prepStatement = databaseConnection.prepareStatement("INSERT INTO `YoDB`.`User` (`Nick`, `Password`, `FirstName`, `LastName`, `Email`, `Age`, `Country`, `City`, `Gender_GenderId`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			
			prepStatement.setString(1, newUser.getNick());
			prepStatement.setString(2, newUser.getPassword());
			prepStatement.setString(3, newUser.getFirstName());
			prepStatement.setString(4, newUser.getLastName());
			prepStatement.setString(5, newUser.geteMail());
			prepStatement.setInt(6, newUser.getAge());
			prepStatement.setString(7, newUser.getCountry());
			prepStatement.setString(8, newUser.getCity());
			if (newUser.getGender().equalsIgnoreCase("Male"))
				prepStatement.setInt(9, 1);
			else if (newUser.getGender().equalsIgnoreCase("Female"))
				prepStatement.setInt(9, 2);
			else
				prepStatement.setInt(9, 3);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch(NullPointerException e) {
			System.err.println("Poszlo sie cos tutaj w addNewUser niedobrze xD");
		} catch (SQLException e) {
			System.err.println("Something went wrong with executing SQL");
		}
	}
	
	public String getUserPassword(String nick) throws SQLException {
		if(databaseConnection.isClosed())
			databaseConnection = DriverManager.getConnection(url, login, password);
		
		ResultSet myRs = null;
		PreparedStatement prepStatement = null;
		
		String pass = null;
		try {
			prepStatement = databaseConnection.prepareStatement("select Password "
											+ "from YoDB.User "
											+ "where Nick = ?");
			prepStatement.setString(1, nick);
			myRs = prepStatement.executeQuery();
			
			if(myRs.next())
				pass = myRs.getString("Password");
			
			prepStatement.close();
			myRs.close();
		} catch(SQLException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	
	public String getGenderName(int genderId) {
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT * FROM YoDB.Gender WHERE GenderId=?;";
		String genderName = null;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, genderId);
			
			result = prepStatement.executeQuery();
			
			if (result.next()) {
				genderName = result.getString("Name");
			}
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem danych o userze");
		}
		
		return genderName;
	}
	
	
	public void saveMessage(Message message) {
		int senderId = 0;
		int receiverId = 0;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			senderId = getUserId(message.getSender());
			receiverId = getUserId(message.getReceiver());
			
			
			PreparedStatement prepStatement = null;
			String sql = "INSERT INTO `YoDB`.`Message` (`SenderId`, `ReceiverId`, `TextContent`, `SendTime`, `Received`) "
					+ "VALUES (?, ?, ?, ?, ?);";
			
			prepStatement = databaseConnection.prepareStatement(sql);
			Timestamp tsp = Timestamp.valueOf(message.getSendDate());
			prepStatement.setInt(1, senderId);
			prepStatement.setInt(2, receiverId);
			prepStatement.setString(3, message.getTextContent());
			prepStatement.setTimestamp(4, tsp);
			prepStatement.setBoolean(5, message.isReceived());
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			System.err.println("Problem z insertem nowej wiadomosci");
		}
	}
	
	
	
	public void addFriend(User user, User friend, LocalDateTime confirmationDate) {
		int userId = 0;
		int friendId = 0;
		PreparedStatement prepStatement = null;
		String query = "INSERT "
					+ "INTO `YoDB`.`Friend` (`Date`, `User_UserId`, `User_FriendId`) "
					+ "VALUES (?, ?, ?)";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			Timestamp tspDate = Timestamp.valueOf(confirmationDate);
			userId = getUserId(user.getUserName());
			friendId = getUserId(friend.getUserName());
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setTimestamp(1, tspDate);
			prepStatement.setInt(2, userId);
			prepStatement.setInt(3, friendId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z insertem przyjaciela");
		}
	}
	
	public void saveInvitation(Invitation invitation) {
		int senderId = 0;
		int receiverId = 0;
		PreparedStatement prepStatement = null;
		String sql = "INSERT INTO `YoDB`.`Notification` (`NotifTypeId`, `Status`, `ReceiverId`, `SenderId`) "
				+ "VALUES ('1', '0', ?, ?);";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			senderId = getUserId(invitation.getSender().getUserName());
			receiverId = getUserId(invitation.getReceiver().getUserName());
			
			prepStatement = databaseConnection.prepareStatement(sql);
			prepStatement.setInt(1, receiverId);
			prepStatement.setInt(2, senderId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			System.err.println("Problem z insertem zaproszenia");
		}
	}
	
	public ArrayList<Invitation> getInvitations(String username) {
		ArrayList<Invitation> invitations = new ArrayList<Invitation>();
		
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT * "
				+ "FROM `YoDB`.`Notification` "
				+ "WHERE NotifTypeId=1 AND Status=0 AND ReceiverId=?";
		int userId;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(username);
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, userId);
			
			result = prepStatement.executeQuery();
			
			while (result.next()) {
				User sender = new User(getUserInfo(result.getInt("SenderId")));
				User receiver = new User(getUserInfo(username));
				Invitation invitation = new Invitation(sender,receiver);
				invitations.add(invitation);
				
			}
			
			for (Invitation i : invitations) {
				System.out.println(i.getSender().getUserName());
			}
			prepStatement.close();
			
			return invitations;
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem zaproszen");
		}
		
		return invitations;
	}
	
	public void deleteUserNotifications(User user) {
		PreparedStatement prepStatement = null;
		int userId = 0;
		String sql = "DELETE FROM `YoDB`.`Notification` "
				+ "WHERE ReceiverId = ? OR SenderId = ?;";
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(user.getUserName());
			prepStatement = databaseConnection.prepareStatement(sql);
			
			prepStatement.setInt(1, userId);
			prepStatement.setInt(2, userId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserMessages(User user) {
		PreparedStatement prepStatement = null;
		int userId = 0;
		String sql = "DELETE FROM `YoDB`.`Message` "
				+ "WHERE SenderId = ? OR ReceiverId = ?;";
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(user.getUserName());
			prepStatement = databaseConnection.prepareStatement(sql);
			
			prepStatement.setInt(1, userId);
			prepStatement.setInt(2, userId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteNotification(User sender, User receiver) {
		int senderId = 0;
		int receiverId = 0;
		PreparedStatement prepStatement = null;
		String query = "DELETE FROM `YoDB`.`Notification` WHERE `ReceiverId`=? AND `SenderId`=?;";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			senderId = getUserId(sender.getUserName());
			receiverId = getUserId(receiver.getUserName());
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, receiverId);
			prepStatement.setInt(2, senderId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z deletem zaproszenia");
		}
	}
	
	public ArrayList<User> getUserFriends(String username) {
		ArrayList<User> friends = new ArrayList<User>();
		
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT User_UserId, User_FriendId "
				+ "FROM YoDB.Friend "
				+ "WHERE User_UserId=? OR User_FriendId=?";
		int userId;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(username);
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, userId);
			prepStatement.setInt(2, userId);
			
			result = prepStatement.executeQuery();
			int tmpUserId;
			int tmpFriendId;
			while (result.next()) {
				tmpUserId = result.getInt("User_UserId");
				tmpFriendId = result.getInt("User_FriendId");
				
				if (tmpUserId != userId) {
					friends.add(getUserInfo(tmpUserId));
				} else {
					friends.add(getUserInfo(tmpFriendId));
				}
			}
			
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem przyjaciol");
		}
		
		return friends;
	}
	
	public void changeMessageStatusToRead(int messageId) {
		PreparedStatement prepStatement = null;
		String query = "UPDATE `YoDB`.`Message` SET `Received`='1' WHERE `MessageId`=?;";
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, messageId);
			
			prepStatement.executeUpdate();
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem ze zmiana stanu wiadomosci");
		}
	}
	
	public ArrayList<Message> getUnreadedMessages(String username) {
		ArrayList<Message> unreadMessages = new ArrayList<Message>();
		
		PreparedStatement prepStatement = null;
		ResultSet result;
		String query = "SELECT * "
				+ "FROM YoDB.Message "
				+ "WHERE ReceiverId=? AND Received=0";
		int userId;
		
		try {
			if(databaseConnection.isClosed())
				databaseConnection = DriverManager.getConnection(url, login, password);
			
			userId = getUserId(username);
			
			prepStatement = databaseConnection.prepareStatement(query);
			prepStatement.setInt(1, userId);
			
			result = prepStatement.executeQuery();
			
			while (result.next()) {
				int messageId = result.getInt("MessageId");
				User sender = new User(getUserInfo(result.getInt("SenderId"))); 
				String textContent = result.getString("TextContent");
				LocalDateTime sendTime = result.getTimestamp("SendTime").toLocalDateTime();
				Message message = new Message(sender.getUserName(), username, textContent, sendTime);
				
				unreadMessages.add(message);
				changeMessageStatusToRead(messageId);
			}
			
			
			prepStatement.close();
			
		} catch (SQLException e) {
			System.err.println("Problem z pobraniem przyjaciol");
		}
		
		return unreadMessages;
	}

	

	
	
	
}
