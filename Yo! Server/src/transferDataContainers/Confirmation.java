package transferDataContainers;

import java.io.Serializable;

public class Confirmation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean confirmed = false;
	private String message;
	
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
