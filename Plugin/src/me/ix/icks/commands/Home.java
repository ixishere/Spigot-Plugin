package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class Home implements CommandExecutor {

	private final Main plugin;
	
	public Home(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("home")) 
		{
			if(sender instanceof Player) 
			{
				if(args.length == 0) 
				{
					if(!plugin.data.getConfig().contains("home-location." + sender.getName() + ".home")) 
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Your main home has not been setup");
						return false;
					}
					
					double posX = plugin.data.getConfig().getDouble("home-location." + sender.getName() + ".home" + ".x");
					double posY = plugin.data.getConfig().getDouble("home-location." + sender.getName() + ".home" + ".y");
					double posZ = plugin.data.getConfig().getDouble("home-location." + sender.getName() + ".home" + ".z");
					
					String worldName = plugin.data.getConfig().getString("home-location." + sender.getName() + ".home" + ".world");

					((Player) sender).teleport(new Location(Bukkit.getWorld(worldName), posX, posY, posZ));
					
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "You have been teleported home");
					
					((Player) sender).sendTitle("", "§6§lTeleported to home", 10, 10, 20);
					
					Utils.playSoundToPlayer(((Player) sender), Sound.BLOCK_NOTE_BLOCK_PLING);
					
					return true;
				}
				if(args.length > 0) 
				{
					String homeToTP = args[0].toLowerCase();
					
					if(!plugin.data.getConfig().contains("home-location." + sender.getName() + "." + homeToTP)) 
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Home " + homeToTP + " does not exist");
						return false;
					}
					
					double posX = plugin.data.getConfig().getDouble("home-location." + sender.getName() + "." + homeToTP + ".x");
					double posY = plugin.data.getConfig().getDouble("home-location." + sender.getName() + "." + homeToTP + ".y");
					double posZ = plugin.data.getConfig().getDouble("home-location." + sender.getName() + "." + homeToTP + ".z");
					
					String world = plugin.data.getConfig().getString("home-location." + sender.getName() + "." + homeToTP + ".world");
					
					((Player) sender).teleport(new Location(Bukkit.getWorld(world), posX, posY, posZ));
					
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "You have been teleported to home: " + homeToTP);
					
					((Player) sender).sendTitle("", "§6§lTeleported to home " + homeToTP, 10, 10, 20);
					
					Utils.playSoundToPlayer(((Player) sender), Sound.BLOCK_NOTE_BLOCK_PLING);
					
					return true;	
				}
			}
			else
			{
				sender.sendMessage(wrapper.getStartText() + "This is a client only command");
			}
		}
		return false;
	}
}
