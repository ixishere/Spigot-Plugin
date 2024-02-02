package me.ix.icks.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class SetHome implements CommandExecutor {

	private final Main plugin;
	
	public SetHome(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("sethome")) {
			if(sender instanceof Player) {
				if(args.length == 0) 
				{
					double posX = ((Player) sender).getLocation().getX();
					double posY = ((Player) sender).getLocation().getY();
					double posZ = ((Player) sender).getLocation().getZ();
					
					String worldName = ((Player) sender).getWorld().getName();
					
					plugin.data.getConfig().set("home-location." + sender.getName() + ".home" + ".world", worldName);
					plugin.data.getConfig().set("home-location." + sender.getName() + ".home" + ".x", posX);
					plugin.data.getConfig().set("home-location." + sender.getName() + ".home" + ".y", posY);
					plugin.data.getConfig().set("home-location." + sender.getName() + ".home" + ".z", posZ);
					
					plugin.data.saveConfig();
					
					((Player) sender).sendTitle("", "§6§lHome set", 10, 10, 20);
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Your home has been set");
					
					Utils.playSoundToPlayer(((Player) sender), Sound.BLOCK_NOTE_BLOCK_PLING);
					
					return true;
				}
				if(args.length > 0) 
				{
					String homeToSet = args[0].toLowerCase();
					
					if(homeToSet.length() > 25)
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Max name length of a home is 25 characters.");
						return false;
					}
						
					double posX = ((Player) sender).getLocation().getX();
					double posY = ((Player) sender).getLocation().getY();
					double posZ = ((Player) sender).getLocation().getZ();
					
					String worldName = ((Player) sender).getWorld().getName();
					
					plugin.data.getConfig().set("home-location." + sender.getName() + "." + homeToSet + ".world", worldName);
					plugin.data.getConfig().set("home-location." + sender.getName() + "." + homeToSet + ".x", posX);
					plugin.data.getConfig().set("home-location." + sender.getName() + "." + homeToSet + ".y", posY);
					plugin.data.getConfig().set("home-location." + sender.getName() + "." + homeToSet + ".z", posZ);
					
					plugin.data.saveConfig();
					
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Your home " + homeToSet + " has been set");
					
					((Player) sender).sendTitle("", "§6§lHome " + homeToSet + " has been set", 10, 10, 20);
					
					Utils.playSoundToPlayer(((Player) sender), Sound.BLOCK_NOTE_BLOCK_PLING);
					
					return true;
				}
			}
			else 
			{
				sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "This is a player only command.");
			}
		}
		return false;
	}
}