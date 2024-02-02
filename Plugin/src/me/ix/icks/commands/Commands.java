package me.ix.icks.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Commands implements CommandExecutor {
	
	private final Main plugin;
	
	public Commands(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Commands")) 
			{
				Random random = new Random();
    	    	
    	    	int randomNum = random.nextInt(5);
    	    	
    	    	String tip = ChatColor.GOLD + "" + randomNum + " Tip: " + ChatColor.AQUA;
    	    	
    	    	switch(randomNum) {
    	    	case 0:
    	    		tip += "use /commands to view the list of useful commands";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 1:
    	    		tip += "You can use colour codes to colour chat messages, nicknames and even signs!";
    	    		Bukkit.broadcastMessage(tip);
    	    		Bukkit.broadcastMessage(ChatColor.GOLD + "Find them here: https://www.planetminecraft.com/blog/server-color-codes-updated/");
    	    		break;
    	    	case 2:
    	    		tip += "If you get lost you can always teleport to spawn using " + ChatColor.BLUE + "/spawn";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 3:
    	    		tip += "Using" + ChatColor.BLUE + " /sethome {name}" + 
    	    	ChatColor.GOLD + " and " + ChatColor.BLUE + "/home" + ChatColor.GOLD + " can be used for quick travel";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 4:
    	    		tip += "";
    	    		break;
    	    	}
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command.");
		}
		return false;
	}
	
}