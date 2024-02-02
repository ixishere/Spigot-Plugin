package me.ix.icks.discordBot.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.ix.icks.discordBot.DiscordMain;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class BukkitChatEvent implements Listener {
	
	JDA jda;
	TextChannel textChannel;
	
	public BukkitChatEvent(JDA jda) {
		
		this.jda = jda;
		textChannel = jda.getTextChannelById(DiscordMain.defaultChannel);
	}
	
	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent e) {
		if(!DiscordMain.crossChat) return;
		
		textChannel.sendMessage("`<" + e.getPlayer().getName() + "> " + e.getMessage() + "`").queue();
	}
}