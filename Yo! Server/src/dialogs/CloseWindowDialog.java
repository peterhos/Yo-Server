package dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class CloseWindowDialog {
	private Alert alert;
	ButtonType buttonYes = new ButtonType("Yes");
	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	
	public CloseWindowDialog() {
		alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Yo! Shutdown");
		alert.setHeaderText("Program will be shut down");
		alert.setContentText("Are you sure you want to exit?");
		alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);
	}
	
	public boolean showAndWait() {
		return (alert.showAndWait().get() == buttonYes) ? true : false;
	}
}
