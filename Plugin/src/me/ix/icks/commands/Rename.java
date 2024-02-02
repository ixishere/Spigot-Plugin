package me.ix.icks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Rename implements CommandExecutor {
	
	private final Main plugin;
	
	public Rename(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Rename")) 
			{
				Player player = (Player) sender;
				
				if(args.length == 0)
				{
					player.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /Rename {Text to rename}");
					return false;
				}
				
				if(player.getItemInHand() != null)
				{
					ItemStack itemToRename = player.getItemInHand();
					
					StringBuilder buffer = new StringBuilder();
					for(int i = 0; i < args.length; i++)
					{
					    buffer.append(' ').append(args[i]);
					}
					String customItemName = buffer.toString().replaceAll("&","§");
					
					ItemMeta im = itemToRename.getItemMeta();
					im.setDisplayName(customItemName.trim());
					
					itemToRename.setItemMeta(im);
					
					player.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Item Renamed");
					player.sendTitle("", "§6§lItem Renamed", 10, 10, 20);
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