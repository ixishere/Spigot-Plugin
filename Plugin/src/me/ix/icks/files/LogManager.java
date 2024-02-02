package me.ix.icks.files;

import java.io.File;
import java.io.IOException;

import me.ix.icks.Main;

public class LogManager {
	
	private Main plugin;
	
	public LogManager(Main plugin) {
		this.plugin = plugin;
		
		createLogFile();
	}
	
	public void createLogFile() 
	 {
		 try {
		      File file = new File(this.plugin.getDataFolder(), "logs.txt");
		      file.createNewFile();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	 }
	
	public File getLogFile() {
		return new File(this.plugin.getDataFolder(), "logs.txt");
	}
}
