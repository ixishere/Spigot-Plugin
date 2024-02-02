package me.ix.icks.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class TPACCEPT implements CommandExecutor {
	
	private final Main plugin;
	
	public TPACCEPT(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("TPACCEPT")) 
			{
				Player player = (Player) sender;
				
				UUID senderUUID = player.getUniqueId();
				
				UUID requesterUUID = me.ix.icks.utils.Hashmaps.getTpas().get(senderUUID);
				
				if (requesterUUID == null)
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have any incoming requests");
					return false;
				}
					
				Player requester = Bukkit.getPlayer(requesterUUID);
				
				requester.teleport(player);
				requester.sendMessage(wrapper.getStartText() + ChatColor.GOLD + "Your teleport request was accepted");
				me.ix.icks.utils.Hashmaps.getTpas().remove(senderUUID);
				
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