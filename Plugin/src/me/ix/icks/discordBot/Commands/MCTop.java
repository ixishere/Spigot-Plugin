package me.ix.icks.discordBot.Commands;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;

import me.ix.icks.Main;
import me.ix.icks.api.MessageInterceptingCommandRunner;
import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MCTop extends ListenerAdapter {

	private Main plugin;
	private String command;
	
	public MCTop(String command, Main plugin) {
		this.plugin = plugin;
		this.command = command;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		
		if(e.getMessage().getAuthor().isBot())
			return;
		
		String msgRAW = e.getMessage().getContentRaw();
		
		if(msgRAW.contains(DiscordMain.commandStart + "mctop ") && !msgRAW.equals(DiscordMain.commandStart + command)) {
			
			Bukkit.broadcastMessage(msgRAW);
			
			String command = msgRAW.replace(DiscordMain.commandStart + "mctop ", "");
			
			final MessageInterceptingCommandRunner cmdRunner = new MessageInterceptingCommandRunner(Bukkit.getConsoleSender());
			
			try {
				Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(cmdRunner, "mctop " + command)).get();
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    @Override
			    public void run() {
			    	EmbedBuilder eb = new EmbedBuilder();
					eb.addField("McMMO", cmdRunner.getMessageLog(), true);
					eb.setFooter("Powered by " + wrapper.getName());
					eb.setColor(Color.MAGENTA);
					
					e.getChannel().sendMessage(eb.build()).queue();
					cmdRunner.clearMessageLog();
			    }
			}, 10L);
			return;
		}
		
		if(msgRAW.equals(DiscordMain.commandStart + "mctop")) {
			
			final MessageInterceptingCommandRunner cmdRunner = new MessageInterceptingCommandRunner(Bukkit.getConsoleSender());
			
			try {
				Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(cmdRunner, "mctop")).get();
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    @Override
			    public void run() {
			    	EmbedBuilder eb = new EmbedBuilder();
					eb.addField("McMMO", cmdRunner.getMessageLog(), true);
					eb.setFooter("Powered by " + wrapper.getName());
					eb.setColor(Color.MAGENTA);
					
					e.getChannel().sendMessage(eb.build()).queue();
					cmdRunner.clearMessageLog();
			    }
			}, 10L);
			return;
		}

	}
}
