package data;

import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class OnlineUser {
	public static ArrayList<OnlineUser> onlineUsers = new ArrayList<OnlineUser>();
	
	String username;
	ObjectOutputStream out;
	
	public OnlineUser(String username, ObjectOutputStream out) {
		this.username = username;
		this.out = out;
		
		onlineUsers.add(this);
	}
	
	
	public static boolean isOnline(String user) {
		for(OnlineUser u : onlineUsers) {
			if(u.getUsername().equals(user))
				return true;
		}
		return false;
	}
	
	public static OnlineUser findUser(String user) {
		OnlineUser ret = null;
		for(OnlineUser u : onlineUsers){
			if(u.getUsername().equals(user))
				ret = u;
		}
		return ret;
	}
	
	public static void addOnlineUser(OnlineUser user) {
		onlineUsers.add(user);
	}
	
	public static void removeOnlineUser(ObjectOutputStream out) {
		for (int i = 0; i < onlineUsers.size(); i++) {
			ObjectOutputStream tmp = onlineUsers.get(i).getOut();
			if (tmp.equals(out)) {
				onlineUsers.remove(i);
			}
		}
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
}
