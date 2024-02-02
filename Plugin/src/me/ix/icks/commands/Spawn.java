package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Spawn implements CommandExecutor {

	private final Main plugin;
	
	public Spawn(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				if(args.length != 0) {
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /spawn");
					return true;	
				}
				else 
				{
					((Player) sender).teleport(Bukkit.getWorld("world").getSpawnLocation()); // chooses default world
					
					((Player) sender).sendTitle("", "§6§lTeleported to spawn", 10, 10, 20);
					
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "You have been teleported to the world spawn");
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