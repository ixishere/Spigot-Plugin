package me.ix.icks.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class DelHome implements CommandExecutor {
	
	private final Main plugin;
	
	public DelHome(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("delhome"))
			{
				Player player = (Player) sender;
				
				if(!plugin.data.getConfig().contains("home-location." + sender.getName())) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have any homes");
					return false;
				}
				
				if(args.length == 0 || args.length > 1)
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /delhome {home}");
					return false;
				}
				else
				{
					String homeToDel = args[0].toLowerCase();
					
					if(plugin.data.getConfig().contains("home-location." + sender.getName() + "." + homeToDel)) 
					{
						plugin.data.getConfig().set("home-location." + sender.getName() + "." + homeToDel, null);
						
						player.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Home " + homeToDel + " has been deleted");
						
						((Player) sender).sendTitle("", "§6§lHome " + homeToDel + " has been deleted", 10, 10, 20);
						
						plugin.data.saveConfig();
						
						return true;
					}
					else
					{
						player.sendMessage(wrapper.getStartText() + ChatColor.RED + "Home " + homeToDel + " does not exist");
						
						return false;
					}
				}
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command");
		}
		return false;
	}
	
}