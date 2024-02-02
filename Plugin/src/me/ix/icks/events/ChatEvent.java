package me.ix.icks.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.ix.icks.Main;

public class ChatEvent implements Listener {

	private final Main plugin;
	
	public ChatEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) 
	{
		String colouredMSG = e.getMessage().toString().replace("&", "§");
		
		if(e.getPlayer().getCustomName() != null) 
		{
			e.setCancelled(true);
			Bukkit.broadcastMessage(e.getPlayer().getCustomName().toString() + ": " + colouredMSG);
		}
		else 
		{
			e.setMessage(colouredMSG);
		}
	}
	
}
