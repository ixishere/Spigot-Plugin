package me.ix.icks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class RealName implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("realname")) {

			if(args.length == 0) {
				sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /realname [Nickname]");
				return false;	
			}
			
			String nickName = args[0];
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(Utils.removeSectionColor(p.getCustomName().toLowerCase()).contains(nickName.toLowerCase())) {
					sender.sendMessage(wrapper.getStartText() + ChatColor.AQUA + "Users real name is: " + p.getName());
					return true;
				}
			}
			sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Could not find player");
		}
		return false;
	}
}