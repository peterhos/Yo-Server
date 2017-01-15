package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable{
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		engine.setText(ServerController.dbEngine);
		ip.setText(ServerController.ip);
		port.setText(ServerController.port);
		schema.setText(ServerController.schema);
		user.setText(ServerController.user);
		password.setText(ServerController.password);
	}
	
	public void updateDatabaseParameters() {
		ServerController.dbEngine = engine.getText();
		ServerController.ip = ip.getText();
		ServerController.port = port.getText();
		ServerController.schema = schema.getText();
		ServerController.user = user.getText();
		ServerController.password = password.getText();
	}
}
