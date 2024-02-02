package me.ix.icks.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class ListHomes implements CommandExecutor {
	
	private final Main plugin;
	
	public ListHomes(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("homes")) 
			{
				Player player = (Player) sender;
				
				if(!plugin.data.getConfig().contains("home-location." + sender.getName())) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have any homes");
					return false;
				}
				
				int i = 1;
				
				Set<String> cs = plugin.data.getConfig().getConfigurationSection("home-location." + sender.getName()).getKeys(false);
				
				String homes = "";
				
				for(String s : cs) {
					homes += (s + ", ");
				}

				homes = homes.substring(0, homes.length() - 2);
				
				sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Your homes are: " + ChatColor.BLUE + homes);
				
				return true;
			}
		}
		else 
		{
			sender.sendMessage("Console has nothing to trash");
		}
		return false;
	}
	
}