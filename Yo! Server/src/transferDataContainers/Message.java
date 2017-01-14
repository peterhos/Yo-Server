package transferDataContainers;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sender;
	private String receiver;
	private String textContent;
	private LocalDateTime sendDate;
	private boolean received = false;
	
	public Message (String sender, String receiver, String textContent, LocalDateTime sendDate) {
		this.sender = sender;
		this.receiver = receiver;
		this.textContent = textContent;
		this.sendDate = sendDate;
	}

	public String toString() {
		return "Sender: " + sender + 
				"\nReceiver: " + receiver +
				"\nContent: " + textContent + "\n"
				+ "Time: " + sendDate.toString() +"\n";
	}
	
	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public LocalDateTime getSendDate() {
		return sendDate;
	}

	public void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
