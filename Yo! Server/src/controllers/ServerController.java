package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import data.Contact;
import dialogs.CloseWindowDialog;
import dialogs.InformationDialog;
import dialogs.StackTraceDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.Server;

public class ServerController implements Initializable {

	private Server server;
	
	public static String dbEngine = "mysql";
	public static String ip = "127.0.0.1";
	public static String port = "3306";
	public static String schema = "YoDB";
	public static String user = "ServerSquad";
	public static String password = "Server1.Conn";
	
	@FXML
	private Label serverStatus = new Label("Stopped");
	
	@FXML
	private TextArea logArea = new TextArea("");
	
	@FXML
	private TextArea errorArea = new TextArea("");
	
	
	// Table and columns for connections
	@FXML
	TableView<Contact> tableID;
	@FXML
	TableColumn remoteAddress;
	@FXML
	TableColumn localAddress;
	@FXML
	TableColumn remotePortNumber;
	@FXML
	TableColumn localPortNumber;

	// The table's data
	public ObservableList<Contact> data;
	
	
	
	public void startServer(ActionEvent event) throws Exception {
		server = new Server(1056, this);
		serverStatus.setText("Running...");
	}
	
	public void stopServer(ActionEvent event) throws Exception {
		server.stopServer();
		serverStatus.setText("Stopped");
	}
	
	public void addContact(Contact connection) {
		data.add(connection);
	}
	
	public void deleteContact(Contact connection) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).equals(connection))
				data.remove(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Set up the table data
		remoteAddress.setCellValueFactory(
	        new PropertyValueFactory<Contact,String>("remoteAddress")
	    );
	    localAddress.setCellValueFactory(
	        new PropertyValueFactory<Contact,String>("localAddress")
	    );
	    remotePortNumber.setCellValueFactory(
	        new PropertyValueFactory<Contact,Integer>("remotePortNumber")
	    );
	    localPortNumber.setCellValueFactory(
	        new PropertyValueFactory<Contact,Integer>("localPortNumber")
	    );
	
	    data = FXCollections.observableArrayList();
	    tableID.setItems(data);
	}
	
	
	
	public void printLogText(String text) {
		logArea.appendText("$: " + text + "\n");
	}
	
	public void printErrorText(String text) {
		errorArea.appendText("!: " +text + "\n");
	}
	
	public void showStackTraceDialog(Exception e) {
		Platform.runLater(
			() -> {
				new StackTraceDialog(e);  
			}
		);
	}
	
	public void showServerStatus() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Yo!");
		alert.setHeaderText("Yo! Server application");
		alert.setContentText("Server app create by Cebula Development team.");

		alert.showAndWait();
	}
	
	public void showAbout(ActionEvent event) throws Exception {
		new InformationDialog("About Yo!", "Yo! Server application", "Server app create by Cebula Development team.");
	}
	
	public void openSettings(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/design/Settings.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Yo! Settings");
		primaryStage.show();
	}

	public void closeProgram(ActionEvent event) throws Exception {
		CloseWindowDialog closeWindowDialog = new CloseWindowDialog();
		boolean decision = closeWindowDialog.showAndWait();
		
		if (decision == true)
		    System.exit(0);
	}
}
