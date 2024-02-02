package me.ix.icks.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Craft implements CommandExecutor {
	
	private final Main plugin;
	
	public Craft(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Craft")) 
			{
				Player player = (Player) sender;
				
				/*
				Inventory inventory = player.getInventory();

				Material m = Material.CRAFTING_TABLE;

				if(!inventory.contains(m)) {
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You must have a crafting table in your inventory");
					return false;
				}
				*/
				
				player.openWorkbench(null, true);
				sender.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Opened crafting bench");
				return false;
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command.");
		}
		return false;
	}
	
}