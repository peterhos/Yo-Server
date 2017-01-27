package commandLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandExecutor {
	private StringBuffer outcome;
	private Process p;
	
	public CommandExecutor() {
		outcome = new StringBuffer();
	}
	
	public String execute(String command) {
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line = "";
            while ((line = reader.readLine()) != null) {
				outcome.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outcome.toString();
	}
}
