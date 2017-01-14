package transfer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sender {
	ObjectOutputStream out;
	
	public Sender(ObjectOutputStream out) {
		this.out = out;
	}
	
	public <T> void send(T object) throws IOException {
		out.writeObject(object);
	}
	
	
	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
}
