package me.ix.icks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;

public class BaseCommand implements CommandExecutor {
	
	private final Main plugin;
	
	public BaseCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("BaseCommand")) 
			{
				
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command.");
		}
		return false;
	}
	
}