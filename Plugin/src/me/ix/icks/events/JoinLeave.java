package me.ix.icks.events;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.ChatComponentText;

public class JoinLeave implements Listener {

	private Main plugin;

	public JoinLeave(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoinForTab(PlayerJoinEvent e) {
		net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		Object header = new ChatComponentText("§aWelcome to " + wrapper.getServerName() + "\n§4----------------");
		Object footer = new ChatComponentText("§4----------------\n§bEnjoy your stay!");
		try {
			Field a = packet.getClass().getDeclaredField("header");
			a.setAccessible(true);
			Field b = packet.getClass().getDeclaredField("footer");
			b.setAccessible(true);
			
			a.set(packet, header);
			b.set(packet, footer);
			
			((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(packet);
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exc) {
			exc.printStackTrace();
		}
	}
	
	@EventHandler
	public void onJoinForNickAndCustomMessage(PlayerJoinEvent e) {
		if(isPlayerNew(e.getPlayer())) {
			e.setJoinMessage("§9§l" + e.getPlayer().getName() + "§r§6 has joined " + wrapper.getServerName() + " for the first time!");
		}
		
		if(plugin.data.getConfig().contains("custom-name." + e.getPlayer().getName())) 
		{
			Utils.setPlayersCustomName(e.getPlayer());
		}
		else
		{
			if(e.getPlayer().getCustomName() != null) 
			{
				String customName = e.getPlayer().getCustomName().replace("[", "").replace("]", "");
				
				plugin.data.getConfig().set("custom-name." + e.getPlayer().getName(), customName);
				
				plugin.data.saveConfig();
				
				Utils.setPlayersCustomName(e.getPlayer());
			}
		}
		
		e.setJoinMessage("§9§l" + e.getPlayer().getName() + "§r§6 has joined " + wrapper.getServerName());
	}
	
	@EventHandler
	public void onJoinRulesMessage(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		if(!isPlayerNew(player)) {
			return;
		}
		
		Utils.openRulesBook(player);
	}
	
	public boolean isPlayerNew(Player player) {
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getName());
		
		if(offlinePlayer.hasPlayedBefore()) {
			return false;
		}
		
		return true;
	}
	
	public void sendDelayedLocalMessage(Player player, Long delayInTicks, boolean pluginStartTag, String output) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if(pluginStartTag) {
					player.sendMessage(wrapper.getStartText() + output);
				}else {
					player.sendMessage(output);
				}
			}
		}, delayInTicks);
	}
	
	/*
	@EventHandler
	public void onJoinForAttackSpeed(PlayerJoinEvent e) {
		//setAttackSpeed(e.getPlayer(), 17);
	}
	
	private void setAttackSpeed(Player player, double attackSpeed){
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if(attribute == null){
            return;
        }

        double baseValue = attribute.getBaseValue();

        if(baseValue != attackSpeed){
            attribute.setBaseValue(attackSpeed);
            player.saveData();
        }
    }
	 */
}
