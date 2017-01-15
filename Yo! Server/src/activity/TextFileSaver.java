package activity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import controllers.ServerController;
import javafx.stage.FileChooser;

public class TextFileSaver {
	FileChooser fileChooser;
	File savedFile;
	ServerController serverController;
	
	public TextFileSaver(String title, String defaultFileName, ServerController serverController) {
		fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialFileName(defaultFileName);
		this.serverController = serverController;
	}
	
	public void saveString(String content) {
		savedFile = fileChooser.showSaveDialog(null);
		if (savedFile != null) {

		    try {
		        PrintWriter out = new PrintWriter(savedFile);
		        out.println(content);
		        out.flush();
		        out.close();
		    }
		    catch(IOException e) {
			
		        e.printStackTrace();
		        serverController.printErrorText("An ERROR occurred while saving the file!");
		        return;
		    }

		    serverController.printLogText("File saved: " + savedFile.toString());
		}
		else {
		    serverController.printErrorText("File save cancelled.");
		}
	}
	
	
}
