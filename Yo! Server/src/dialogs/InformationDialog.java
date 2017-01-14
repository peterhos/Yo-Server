package dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InformationDialog {
	private Alert alert;
	
	public InformationDialog(String title, String header, String content) {
		alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		show();
	}
	
	private void show() {
		alert.showAndWait();
	}
}
