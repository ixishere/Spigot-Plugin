package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Enderchest implements CommandExecutor {
	
	private final Main plugin;
	
	public Enderchest(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Enderchest") || label.equalsIgnoreCase("EChest")) 
			{
				Player player = (Player) sender;
				
				if(args.length == 0) 
				{
					Inventory inventory = player.getInventory();

					Material m = Material.ENDER_CHEST;

					if(!inventory.contains(m)) {
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You must have an enderchest in your inventory");
						return false;
					}
					
					player.openInventory(player.getEnderChest());
					sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Opened enderchest");
					return false;
				}
				
				if(args.length == 1) 
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
						player.openInventory(playerGiven.getEnderChest());
						sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Opened enderchest");
						return true;
					}
					else
					{
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Player not online");
						return false;
					}
				}
				else
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /EChest or /EChest (player)");
					return false;
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