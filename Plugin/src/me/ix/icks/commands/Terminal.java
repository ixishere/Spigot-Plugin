package me.ix.icks.commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class Terminal implements CommandExecutor {
	
	private final Main plugin;
	
	public Terminal(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Terminal")) {
				
				Player player = (Player) sender;
				
				if(!player.isOp()) 
				{
					player.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have permission to use this command");
					return false;
				}
				
				if(args.length == 0) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /Terminal {Command to send}");
					return true;	
				}
				
				if(args.length > 0) 
				{
					StringBuilder buffer = new StringBuilder();
					
					for(int i = 1; i < args.length; i++)
					{
					    buffer.append(' ').append(args[i]);
					}
					
					String command = buffer.toString();
					
					try {
						Utils.sendLinuxCommand(command);
						player.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Command Executed");
					} catch (IOException e) {
						e.printStackTrace();
						player.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Error executing command");
					}
					
					return true;
				}
				
				return true;
			}
		}
		else 
		{
			sender.sendMessage("Player command only atm");
		}
		return false;
	}
	
}