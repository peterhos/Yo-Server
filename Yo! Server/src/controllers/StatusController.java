package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class StatusController implements Initializable {
	/*
	 * Status Information
	 */
	@FXML
	private Label serverStatus;
	@FXML
	private Label hostName;
	
	/*
	 * OS Information
	 */
	@FXML
	private Label osName;
	@FXML
	private Label osVersion;
	@FXML
	private Label osArchitecture;
	@FXML
	private Label userName;
	@FXML
	private Label cores;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.hostName.setText(System.getProperty("user.name"));
		this.serverStatus.setText(ServerController.srvStatus);
		this.osName.setText(System.getProperty("os.name") + " " + System.getenv("SESSION"));
		this.osVersion.setText(System.getProperty("os.version"));
		this.osArchitecture.setText(System.getProperty("os.arch"));
		this.userName.setText(System.getProperty("user.name"));
		this.cores.setText(Integer.toString(Runtime.getRuntime().availableProcessors()));
	}
}
