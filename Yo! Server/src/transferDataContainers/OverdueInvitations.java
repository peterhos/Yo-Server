package transferDataContainers;

import java.io.Serializable;
import java.util.ArrayList;

public class OverdueInvitations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	ArrayList<Invitation> invitations;
	
	public OverdueInvitations(ArrayList<Invitation> invitations) {
		this.invitations = new ArrayList<Invitation>(invitations);
	}

	public ArrayList<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}
}
