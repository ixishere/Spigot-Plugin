package me.ix.icks.discordBot.Commands;

import org.bukkit.Bukkit;

import me.ix.icks.discordBot.DiscordMain;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SendMSG extends ListenerAdapter  {

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(!DiscordMain.crossChat) return;
		
		if(e.getMessage().getChannel().getIdLong() == DiscordMain.defaultChannel) {
			
			if(e.getMessage().getContentRaw().startsWith("."))
				return;
			
			if(e.getMessage().getAuthor().isBot())
				return;
			
			Bukkit.broadcastMessage("§4[Discord]§r §b" + e.getMessage().getAuthor().getName() + "§r: " + e.getMessage().getContentRaw());
		}
	}
}
