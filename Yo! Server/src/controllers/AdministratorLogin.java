package controllers;

import java.io.IOException;
import java.util.Optional;

import dialogs.CloseWindowDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class AdministratorLogin {

	@FXML
	private Label loginStatus;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	
	public void login(ActionEvent event) throws Exception {
		if (credentialsCorrect()) {
			closeLoginWindow();
			openServerMainWindow();
		} else {
			loginStatus.setText("Wrong credentials");
		}
	}
	
	public void openServerMainWindow() throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/design/MainServerWindow.fxml"));
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void closeProgram() {
		CloseWindowDialog closeWindowDialog = new CloseWindowDialog();
		boolean decision = closeWindowDialog.showAndWait();
		
		if (decision == true)
		    System.exit(0);
	}

	public void closeLoginWindow() {
		Stage oldStage = (Stage)password.getScene().getWindow();
		oldStage.close();
	}
	
	public boolean credentialsCorrect() {
		return ( username.getText().equals("admin") && password.getText().equals("admin") ) ? true : false;
	}
}
