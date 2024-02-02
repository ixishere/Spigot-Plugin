package me.ix.icks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.ix.icks.Main;

public class Trash implements CommandExecutor {
	
	private final Main plugin;
	
	public Trash(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Trash") || label.equalsIgnoreCase("Bin")) {
				Player player = (Player) sender;
				Inventory inv = plugin.getServer().createInventory(player, 27, "Trash");
				player.openInventory(inv);
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