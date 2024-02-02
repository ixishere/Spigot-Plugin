package me.ix.icks.discordBot.Commands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnlinePlayers extends ListenerAdapter {

	private String command;
	
	public OnlinePlayers(String command) {
		this.command = command;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getMessage().getContentRaw().equals(DiscordMain.commandStart + command)) {
			
			String output = "";
			int amountOfPlayers = 0;
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(amountOfPlayers < 1) {
					output += p.getName();
				}else {
					output += ", " + p.getName();
				}
				
				amountOfPlayers++;
			}
			
			if(amountOfPlayers == 0) {
		    	EmbedBuilder eb = new EmbedBuilder();
				eb.addField("Online Players", "There are no online players", true);
				eb.setFooter("Powered by " + wrapper.getName());
				eb.setColor(Color.MAGENTA);
				
				e.getChannel().sendMessage(eb.build()).queue();
			}else {
		    	EmbedBuilder eb = new EmbedBuilder();
				eb.addField("Online Players", output, true);
				eb.setFooter("Powered by " + wrapper.getName());
				eb.setColor(Color.MAGENTA);
				
				e.getChannel().sendMessage(eb.build()).queue();
			}
		}
	}
	
}
