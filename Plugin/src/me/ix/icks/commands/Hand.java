package me.ix.icks.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class Hand implements CommandExecutor {
	
	private final Main plugin;
	
	public Hand(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("hand")) 
			{
				Player plr = (Player) sender;
		        if (plr.getItemInHand().getType().equals(Material.AIR)) {
		            plr.sendMessage(ChatColor.RED + "Cant broadcast air");
		            return true;
		        }
		       
		        ItemStack is = plr.getItemInHand();
		        Bukkit.broadcastMessage(wrapper.getStartText() + "§6" + plr.getName() + " is holding: §4" + is.getType().toString());
		       
		        if (is.getItemMeta().hasEnchants()) {
		            StringBuilder sb = new StringBuilder();
		            for (Map.Entry<Enchantment, Integer> e : is.getEnchantments().entrySet()) {
		                sb.append(e.getKey().getName()).append(":").append(e.getValue()).append(", ");
		            }
		           
		            Bukkit.broadcastMessage("§6Item Enchantments: §a" + sb.toString().substring(0, sb.length() - 2) + ".");
		        }
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