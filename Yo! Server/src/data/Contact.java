package data;

import java.net.Socket;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contact {
	private SimpleStringProperty remoteAddress;
	private SimpleStringProperty localAddress;
	private SimpleIntegerProperty remotePortNumber;
	private SimpleIntegerProperty localPortNumber;
	
	public Contact(Socket socket) {
		this.remoteAddress = new SimpleStringProperty(socket.getInetAddress().toString());
		this.localAddress = new SimpleStringProperty(socket.getLocalAddress().toString());
		this.remotePortNumber = new SimpleIntegerProperty(socket.getPort());
		this.localPortNumber = new SimpleIntegerProperty(socket.getLocalPort());
		
	}
	
	
	public String getRemoteAddress() {
		return remoteAddress.get();
	}
	
	public void setRemoteAddress(SimpleStringProperty remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getLocalAddress() {
		return localAddress.get();
	}
	public void setLocalAddress(SimpleStringProperty localAddress) {
		this.localAddress = localAddress;
	}
	public Integer getRemotePortNumber() {
		return remotePortNumber.get();
	}
	public void setRemotePortNumber(SimpleIntegerProperty remotePortNumber) {
		this.remotePortNumber = remotePortNumber;
	}
	public Integer getLocalPortNumber() {
		return localPortNumber.get();
	}
	public void setLocalPortNumber(SimpleIntegerProperty localPortNumber) {
		this.localPortNumber = localPortNumber;
	}
}
