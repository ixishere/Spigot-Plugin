package me.ix.icks.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class Performance implements CommandExecutor {
	
	private final Main plugin;
	
	public Performance(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Performance")) 
			{
				Player player = (Player) sender;
				
				if(!player.isOp())
					return false;
				
				Bukkit.broadcastMessage(wrapper.getStartText() + "§6" + sender.getName() + " called for system performance check.");
				
				sendMessage(sender);
				
				return true;
			}
		}
		else 
		{
			Bukkit.broadcastMessage(wrapper.getStartText() + "§Console called for system performance check.");
			
			sendMessage(sender);
		}
		return false;
	}
	
	public void sendMessage(CommandSender sender) {
		try {
    		String temperature = Utils.getPiTempFreq()[0];
    		String frequency = Utils.getPiTempFreq()[1];
    		String[] memory = Utils.getPiMem();
    		
    		sender.sendMessage(wrapper.getStartText() +
					"§C§l< Pi Status >"
					+ "\n§b§lTemperature: §r§6" + temperature 
					+ "\n§b§lFrequency: §r§6" + frequency
					+ "\n§b§lMemory: §r§6" + memory[0] + "/" + memory[1] + " GB");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}