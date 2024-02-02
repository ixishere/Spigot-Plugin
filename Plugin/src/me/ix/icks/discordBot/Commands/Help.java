package me.ix.icks.discordBot.Commands;

import java.awt.Color;

import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter  {

	private String command;
	
	public Help(String command) {
		this.command = command;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getMessage().getAuthor().isBot())
			return;
		
		if(e.getMessage().getContentRaw().equals(DiscordMain.commandStart + command)) {
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("IcksGenerals Bot");
			
			eb.addField(DiscordMain.commandStart + "mctop", "The McMMO leaderboard", true);
			eb.addField(DiscordMain.commandStart + "mctop [Skill]", "The McMMO leaderboard for a skill", true);
			eb.addField(DiscordMain.commandStart + "online", "Current online players", false);
			eb.addField(DiscordMain.commandStart + "tps", "Ticks Per Second check for performance", false);
			eb.addField(DiscordMain.commandStart + "system", "Checks the current system performance", false);
			eb.addField(DiscordMain.commandStart + "clean", "Cleans all bot commands", false);
			
			eb.setFooter("Powered by " + wrapper.getName());
			
			eb.setColor(Color.MAGENTA);
			
			e.getChannel().sendMessage(eb.build()).queue();
			
			return;
		}
		
		for(String s : DiscordMain.getDiscordCommands()) {
			if(e.getMessage().getContentRaw().startsWith(DiscordMain.commandStart + s))
				return;
		}
		
		if(!e.getMessage().getContentRaw().startsWith(DiscordMain.commandStart))
			return;
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("IcksGenerals Bot");
		eb.addField("Unknown Command", "Use .help", true);
		eb.setFooter("Powered by " + wrapper.getName());
		eb.setColor(Color.RED);
		e.getChannel().sendMessage(eb.build()).queue();
		
	}
	
	/*
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getMessage().getContentRaw().equals(DiscordMain.commandStart + "help")) {
			
			e.getChannel().sendMessage("`Use Commands: .online .tps .mctop .mctop [skill]`").queue();
		}
		else if(e.getMessage().getContentRaw().startsWith(DiscordMain.commandStart)
				 && !e.getMessage().getContentRaw().contains(DiscordMain.commandStart + "online")
				 && !e.getMessage().getContentRaw().contains(DiscordMain.commandStart + "send")
				 && !e.getMessage().getContentRaw().contains(DiscordMain.commandStart + "tps")
				 && !e.getMessage().getContentRaw().contains(DiscordMain.commandStart + "mctop")) {
			e.getChannel().sendMessage("Unrecognized Command, `Use Commands: .online .send .tps .mctop .mctop (skill)`").queue();
		}
	}
	*/
}
