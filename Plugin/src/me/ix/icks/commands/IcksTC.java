package me.ix.icks.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class IcksTC implements TabCompleter {

	List<String> arguments = new ArrayList<String>();
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
		
		if(arguments.isEmpty()) {
			arguments.add("Reload");
			arguments.add("Backup");
			arguments.add("CreateTag");
			arguments.add("DeleteTag");
			arguments.add("Info");
			arguments.add("Help");
		}
		
		List<String> result = new ArrayList<String>();
		if(args.length == 1) {
			for(String a : arguments) {
				if(a.toLowerCase().startsWith(args[0].toLowerCase())) {
					result.add(a);
				}
			}
			return result;
		}
		
		return null;
	}
	
}
