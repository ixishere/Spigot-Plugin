package me.ix.icks.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class ServerPing implements Listener {

	private Main plugin;

	public ServerPing(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void serverPingEvent(ServerListPingEvent e) 
	{
		String firstLine = centerMOTD("§e§l§m-------§9§8§l[§r §b§6§l" + wrapper.getServerName() + " §8§l]§e§l§m-------§r", 0);
		String secondLine = centerMOTD("§l< §b§lEnjoy Your Stay §r§l>", 5);
		
		e.setMotd(firstLine + "\n" + secondLine);
	}
	
	public String centerMOTD(String s, int shift) 
	{
		String output = s;
		int charsLeft = 50 - Utils.removeSectionColor(s).length() + shift;
		
		for(int i = 0; i < charsLeft/2; i++) {
			output = " " + output;
			output = output + " ";
		}
		return output;
	}
}