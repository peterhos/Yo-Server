package controllers;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable{
	/*
	 * Database settings
	 */
	@FXML
	private TextField engine = new TextField();
	
	@FXML
	private TextField ip = new TextField();
	
	@FXML
	private TextField port = new TextField();
	
	@FXML
	private TextField schema = new TextField();
	
	@FXML
	private TextField user = new TextField();
	
	@FXML
	private TextField password = new TextField();
	
	/*
	 * Network settings
	 */
	@FXML
	private TextField ipAddress = new TextField();
	
	@FXML
	private TextField portNumber = new TextField();
	
	@FXML
	private TextField hostName = new TextField();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine.setText(ServerController.dbEngine);
		ip.setText(ServerController.ip);
		port.setText(ServerController.port);
		schema.setText(ServerController.schema);
		user.setText(ServerController.user);
		password.setText(ServerController.password);
		
		try {
			ipAddress.setText(InetAddress.getLocalHost().getHostAddress());
			ipAddress.setEditable(false);
			hostName.setText(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		portNumber.setText(Integer.toString(ServerController.serverPortNumber));
	}
	
	public void updateDatabaseParameters() {
		ServerController.dbEngine = engine.getText();
		ServerController.ip = ip.getText();
		ServerController.port = port.getText();
		ServerController.schema = schema.getText();
		ServerController.user = user.getText();
		ServerController.password = password.getText();
		
		changeToEditable();
	}
	
	public void changeToEditable() {
		boolean state = engine.isEditable();
		
		engine.setEditable(!state);
		ip.setEditable(!state);
		port.setEditable(!state);
		schema.setEditable(!state);
		user.setEditable(!state);
		password.setEditable(!state);
	}
}
