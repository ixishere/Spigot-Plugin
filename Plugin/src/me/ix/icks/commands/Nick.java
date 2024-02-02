package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Nick implements CommandExecutor {

	private final Main plugin;
	
	public Nick(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("nick")) 
		{
			if(!(sender instanceof Player)) {
				sender.sendMessage("Not a console command!");
				return false;
			}
			
			if(args.length == 0) {
				sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /nick [Nickname]");
				return false;	
			}
			if(args.length > 1) {
				sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "No Spaces Allowed");
				return false;	
			}
			
			if(args[0].toString().equalsIgnoreCase("remove")) {
				Player bPlayer = Bukkit.getPlayer(sender.getName().toString());
				if(bPlayer.isOnline()) {
					bPlayer.setCustomName(null);
					bPlayer.setDisplayName(bPlayer.getName());
					bPlayer.setPlayerListName(bPlayer.getName());
					bPlayer.setCustomNameVisible(false);
					
					plugin.data.getConfig().set("custom-name." + sender.getName(), null);
					
					plugin.data.saveConfig();
					
					((Player) sender).sendTitle("", "§6§lNick removed", 10, 10, 20);
					sender.sendMessage(wrapper.getStartText() + ChatColor.AQUA + "Nickname Removed");
					return false;
				}else {
					sender.sendMessage("Error occured");
					return false;
				}
			}
			
			String listName = args[0].replace("&", "§") + "§r";
			String customName = "[" + args[0].replace("&", "§") + "§r]";
			
			Player bPlayer = Bukkit.getPlayer(sender.getName().toString());
			
			if(bPlayer.isOnline()) {
				bPlayer.setCustomName(customName);
				bPlayer.setDisplayName(customName);
				bPlayer.setPlayerListName(listName);
				bPlayer.setCustomNameVisible(true);
				
				plugin.data.getConfig().set("custom-name." + sender.getName(), args[0].replace("&", "§"));
				
				plugin.data.saveConfig();

				((Player) sender).sendTitle("", "§6§lNick set to " + customName, 10, 10, 20);
				
				Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.RED + bPlayer.getName() + " has nicked themselves as: §r" + customName);
			}else {
				sender.sendMessage("Error occured");
			}
		}
		return false;
	}
	
	
}