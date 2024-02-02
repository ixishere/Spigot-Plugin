package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class InvSee implements CommandExecutor {
	
	private final Main plugin;
	
	public InvSee(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("InvSee")) {
				Player player = (Player) sender;
				
				if(args.length == 0) {
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /InvSee {player}");
					return false;	
				}
				if(args.length > 0) 
				{
					String stringPlayerGiven = args[0];
					Player playerGiven = Bukkit.getPlayer(stringPlayerGiven);
					
					if(!player.isOp())
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have permission to use this command.");
						return false;
					}
					
					if(playerGiven.isOnline())
					{
						player.openInventory(playerGiven.getInventory());
						sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Opened inventory.");
						return true;
					}
					else
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Player not online");
						return false;
					}
				}
				return true;
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command");
		}
		return false;
	}
	
}