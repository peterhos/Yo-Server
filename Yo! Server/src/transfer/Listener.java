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
import actions.UserDataSupplier;
import actions.UserSearcher;
import controllers.ServerController;
import database.DatabaseConnector;
import transferDataContainers.Invitation;
import transferDataContainers.InvitationConfirmation;
import transferDataContainers.LoginCredentials;
import transferDataContainers.Message;
import transferDataContainers.RegistrationInformation;
import transferDataContainers.User;
import transferDataContainers.UserDataRequest;

public class Listener {
	Object input;
	
	public void listen(ObjectInputStream in, ObjectOutputStream out, DatabaseConnector dbConnector, ServerController serverController) 
										throws ClassNotFoundException, IOException, EOFException {
		input = new Object();
		input = in.readObject();
		
		if (input instanceof LoginCredentials) {	
			Login login = new Login(out, dbConnector, serverController);
			login.checkCredentials((LoginCredentials)input);
		} else if (input instanceof Message) {
			RedirectMessage redMsg = new RedirectMessage(dbConnector);
			redMsg.redirectMessage((Message)input);
		} else if (input instanceof RegistrationInformation) {
			Registrant registrant = new Registrant(out, dbConnector);
			registrant.registerNewUser((RegistrationInformation)input);
		} else if (input instanceof User) {
			UserSearcher userSearcher = new UserSearcher(out, dbConnector);
			userSearcher.search((User)input);
		} else if (input instanceof Invitation) {
			InvitationGenerator invitationGenerator = new InvitationGenerator(dbConnector);
			invitationGenerator.generate((Invitation)input);
		} else if (input instanceof InvitationConfirmation) {
			InvitationHandler invitationHandler = new InvitationHandler(dbConnector);
			invitationHandler.handle((InvitationConfirmation)input);
		} else if(input instanceof UserDataRequest) {
			UserDataSupplier dataSupplier = new UserDataSupplier(out, dbConnector);
			dataSupplier.deliver((UserDataRequest)input);
		}
	}
}
