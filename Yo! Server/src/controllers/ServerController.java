package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import activity.TextFileSaver;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Server;

public class ServerController implements Initializable {

	private Server server;
	public static String srvStatus = "Stopped";
	
	public static int serverPortNumber = 1056;
	
	public static String dbEngine = "mysql";
	public static String ip = "127.0.0.1";
	public static String port = "3306";
	public static String schema = "YoDB";
	public static String user = "ServerSquad";
	public static String password = "Server1.Conn";
	
	@FXML
	private Label serverStatus = new Label(srvStatus);
	
	@FXML
	private TextArea logArea = new TextArea("");
	
	@FXML
	private TextArea errorArea = new TextArea("");
	
	
	/*
	 * Command Line controls 
	 */
	@FXML
	private TextArea commandArea = new TextArea("");
	
	@FXML
	private TextField command = new TextField("");
	
	@FXML
	public void onEnter(ActionEvent event) {
		
		String cmd = command.getText();
		command.clear();
		String output = this.executeCommand(cmd);

		commandArea.appendText(output);
		commandArea.appendText("\n");
	}
	
	private String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
	
	
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
	
	public void startServer(ActionEvent e) {
		if (srvStatus == "Stopped") {
			server = new Server(1056, this);
			serverStatus.setText("Running...");
			srvStatus = "Running...";
		}
	}
	
	public void stopServer(ActionEvent e) throws IOException {
		if (srvStatus == "Running..."){
			server.stopServer();
			serverStatus.setText("Stopped");
			srvStatus = "Stopped";
		}
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
	
	public void cleanLogArea(ActionEvent event) {
		logArea.clear();
	}
	
	public void cleanErrorArea(ActionEvent event) {
		errorArea.clear();
	}
	
	public void saveLogToFile(ActionEvent event) {
		TextFileSaver fileSaver = new TextFileSaver("Save log file", "log.txt", this);
		fileSaver.saveString(logArea.getText());
	}
	
	public void saveErrorToFile(ActionEvent event) {
		TextFileSaver fileSaver = new TextFileSaver("Save error file", "error.txt", this);
		fileSaver.saveString(errorArea.getText());
	}
	
	
	public void showServerStatus() throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/design/ServerStatus.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Yo! Server Status");
		primaryStage.show();
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
