package me.ix.icks.discordBot.Commands;

import java.awt.Color;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TempFreq extends ListenerAdapter {

	private String command;
	
	public TempFreq(String command) {
		this.command = command;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getMessage().getContentRaw().equals(DiscordMain.commandStart + command)) {
			
			String temperature = null, frequency = null;
			
			String[] memory = null;
			
			try {
				temperature = Utils.getPiTempFreq()[0];
				frequency = Utils.getPiTempFreq()[1];
				memory = Utils.getPiMem();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.addField("Pi System Info",
					"Temperature: " + temperature
					+ "\nFrequency: " + frequency
					+ "\nMemory: " + memory[0] + "/" + memory[1] + " GB"
					, true);
			
			eb.setFooter("Powered by " + wrapper.getName());
			eb.setColor(Color.MAGENTA);
				
			e.getChannel().sendMessage(eb.build()).queue();
			
		}
	}
	
}
