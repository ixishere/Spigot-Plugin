package me.ix.icks.discordBot.Commands;

import java.util.ArrayList;
import java.util.List;

import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clean extends ListenerAdapter {

	private String command;
	
	public Clean(String command) {
		this.command = command;
	}
	
	public ArrayList<String> commands = new ArrayList<String>();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) 
	{
		if(e.getMessage().getContentRaw().equals(DiscordMain.commandStart + command))
		{
			List<Message> messages = e.getChannel().getHistory().retrievePast(100).complete();
			
			for(Message m : messages) 
			{	
				String msg = m.getContentRaw();
			
				if(m.getAuthor().getIdLong() == 741843278235959446L) 
					m.delete().queue();
				
				for(String s : DiscordMain.getDiscordCommands()) {
					if(msg.toLowerCase().startsWith("." + s.toLowerCase()))
						m.delete().queue();
				}
			}
		}
	}
}
