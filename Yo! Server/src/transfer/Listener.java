package transfer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import actions.InvitationGenerator;
import actions.InvitationHandler;
import actions.Login;
import actions.RedirectMessage;
import actions.Registrant;
import actions.UserDataEditer;
import actions.UserDataSupplier;
import actions.UserRemover;
import actions.UserSearcher;
import controllers.ServerController;
import server.ClientConnection;
import transferDataContainers.EditedUserData;
import transferDataContainers.Invitation;
import transferDataContainers.InvitationConfirmation;
import transferDataContainers.LoginCredentials;
import transferDataContainers.Message;
import transferDataContainers.RegistrationInformation;
import transferDataContainers.User;
import transferDataContainers.UserDataRequest;
import transferDataContainers.UserToRemove;

public class Listener {
	Object input;
	
	public void listen(ObjectInputStream in, ObjectOutputStream out, 
					ServerController serverController, ClientConnection clientConnection) 
										throws ClassNotFoundException, IOException, EOFException {
		input = new Object();
		input = in.readObject();
		
		if (input instanceof LoginCredentials) {	
			Login login = new Login(out, serverController);
			login.checkCredentials((LoginCredentials)input);
		} else if (input instanceof Message) {
			RedirectMessage redMsg = new RedirectMessage();
			redMsg.redirectMessage((Message)input);
		} else if (input instanceof RegistrationInformation) {
			Registrant registrant = new Registrant(out);
			registrant.registerNewUser((RegistrationInformation)input);
		} else if (input instanceof User) {
			UserSearcher userSearcher = new UserSearcher(out);
			userSearcher.search((User)input);
		} else if (input instanceof Invitation) {
			InvitationGenerator invitationGenerator = new InvitationGenerator();
			invitationGenerator.generate((Invitation)input);
		} else if (input instanceof InvitationConfirmation) {
			InvitationHandler invitationHandler = new InvitationHandler();
			invitationHandler.handle((InvitationConfirmation)input);
		} else if (input instanceof UserDataRequest) {
			UserDataSupplier dataSupplier = new UserDataSupplier(out);
			dataSupplier.deliver((UserDataRequest)input);
		} else if (input instanceof EditedUserData) {
			UserDataEditer userDataEditer = new UserDataEditer();
			userDataEditer.edit((EditedUserData)input);
		} else if (input instanceof UserToRemove) {
			UserRemover userRemover = new UserRemover();
			userRemover.remove((UserToRemove)input);
		} else if (input instanceof String) {
			if(((String)input).equals("quit")) {
				clientConnection.stopClient();
			}
		}
	}
}
