package me.ix.icks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ix.icks.Main;
import me.ix.icks.utils.Utils;

public class Rules implements CommandExecutor {
	
	private final Main plugin;
	
	public Rules(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("rules")) {
				
				Player player = (Player) sender;
				
				Utils.openRulesBook(player);
				
				return true;
			}
		}
		else 
		{
			sender.sendMessage("Console cant open the rules");
		}
		return false;
	}
	
}