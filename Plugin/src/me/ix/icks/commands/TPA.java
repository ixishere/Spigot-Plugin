package me.ix.icks.commands;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class TPA implements CommandExecutor {
	
	private final Main plugin;
	
	public TPA(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("TPA")) 
			{
				if(args.length != 1) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /tpa (player)");
					return false;
				}
				
				if(!Bukkit.getPlayer(args[0]).isOnline()) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "That player is not online");
					return false;
				}
				
				if(Bukkit.getPlayer(args[0]) == null)
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "That player is not online");
					return false;
				}
				
				Player playerToTeleportTo = Bukkit.getPlayer(args[0]);
				
				Player player = (Player) sender;
				
				if(playerToTeleportTo == player) 
				{
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You cannot teleport to yourself");
					return false;
				}
				
				player.sendMessage(wrapper.getStartText() + ChatColor.AQUA + "Teleport request sent");
				
				playerToTeleportTo.sendMessage(wrapper.getStartText() + ChatColor.AQUA
						+ "Teleport request recieved from user [" + player.getName() + "]");
				
				playerToTeleportTo.sendTitle("§6§lTeleport request recieved from user [\" + player.getName() + \"]", "§4use/tpaccept", 10, 10, 20);
				
				UUID targetUUID = playerToTeleportTo.getUniqueId();
				UUID senderUUID = player.getUniqueId();
				
				me.ix.icks.utils.Hashmaps.getTpas().put(targetUUID, senderUUID);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
				{
					@Override
	                public void run() 
	                {
						Map<UUID, UUID> tpas = me.ix.icks.utils.Hashmaps.getTpas();

						tpas.remove(targetUUID, senderUUID);
	                }
	            }, 20L * 30L);
				
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