package controllers;

import java.io.IOException;
import java.util.Optional;

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
		Parent root = FXMLLoader.load(getClass().getResource("/design/ServerMainWindow.fxml"));
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void closeProgram() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Shutdown");
		alert.setHeaderText("Program will be shut down");
		alert.setContentText("Are you sure you want to exit?");

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes){
		    System.exit(0);
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}

	public void closeLoginWindow() {
		Stage oldStage = (Stage)password.getScene().getWindow();
		oldStage.close();
	}
	
	public boolean credentialsCorrect() {
		return ( username.getText().equals("admin") && password.getText().equals("admin") ) ? true : false;
	}
}
