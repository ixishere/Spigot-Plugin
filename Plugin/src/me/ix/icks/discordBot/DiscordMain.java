package me.ix.icks.discordBot;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;

import me.ix.icks.Main;
import me.ix.icks.discordBot.Commands.Clean;
import me.ix.icks.discordBot.Commands.GetTPS;
import me.ix.icks.discordBot.Commands.Help;
import me.ix.icks.discordBot.Commands.MCTop;
import me.ix.icks.discordBot.Commands.OnlinePlayers;
import me.ix.icks.discordBot.Commands.SendMSG;
import me.ix.icks.discordBot.Commands.TempFreq;
import me.ix.icks.discordBot.event.BukkitChatEvent;
import me.ix.icks.utils.wrapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordMain extends Thread {

	static ArrayList<String> commands = new ArrayList<String>();
	
	public static String token;
	public static String commandStart;
	public static String watching;
	public static long defaultChannel;
	public static boolean crossChat;

	JDA jda = null;
	
	private final Main plugin;
	
	public DiscordMain(Main plugin) 
	{
		this.plugin = plugin;
	}
	
	public void setupBotConfig()
	{
		if(!plugin.config.getConfig().contains("discord-bot")) {
			plugin.getLogger().info("Error in " + wrapper.getName() + " discord config please fix");
			return;
		}
		if(!plugin.config.getConfig().getBoolean("discord-bot.enabled")) {
			plugin.getLogger().info("Discord bot not enabled in config");
			return;
		}
		
		token = plugin.config.getConfig().getString("discord-bot.token");
		commandStart = plugin.config.getConfig().getString("discord-bot.botCommand");
		watching = plugin.config.getConfig().getString("discord-bot.watching");
		defaultChannel = Long.parseLong(plugin.config.getConfig().getString("discord-bot.defaultChannelID"));
		crossChat = plugin.config.getConfig().getBoolean("discord-bot.crossChatEnabled");
	}
	
	public void createBot() throws LoginException, InterruptedException 
	{
		setupBotConfig();
		
		JDABuilder jdaBuilder = JDABuilder.createDefault(token);
		
		jdaBuilder.setActivity(Activity.watching(watching));
		
		// Commands
		OnlinePlayers online = new OnlinePlayers("online");
		Help help = new Help("help");
		MCTop mctop = new MCTop("mctop", plugin);
		GetTPS getTPS = new GetTPS("tps", plugin);
		TempFreq tempFreq = new TempFreq("system");
		Clean clean = new Clean("clean");
		
		addDiscordCommand("online");
		addDiscordCommand("help");
		addDiscordCommand("mctop");
		addDiscordCommand("tps");
		addDiscordCommand("system");
		addDiscordCommand("clean");
		
		// Not Commands
		SendMSG sendmsg = new SendMSG();
		
		jdaBuilder.addEventListeners(online);
		jdaBuilder.addEventListeners(help);
		jdaBuilder.addEventListeners(mctop);
		jdaBuilder.addEventListeners(getTPS);
		jdaBuilder.addEventListeners(tempFreq);
		jdaBuilder.addEventListeners(sendmsg);
		jdaBuilder.addEventListeners(clean);
		
		jda = jdaBuilder.build();
		jda.awaitReady();
		
		BukkitChatEvent chatevent = new BukkitChatEvent(jda); // Gets bukkit chat
		plugin.getServer().getPluginManager().registerEvents(chatevent, plugin); // Adds to plugin event manager
	}
	
	@Override
	public void run() 
	{
		try {
			createBot();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage("Error has occured with discord bot.");
		}
	}
	
	public static String[] getDiscordCommands() 
	{
		String output[] = new String[commands.size()];
		output = commands.toArray(output);
		
		return output;
	}
	
	public static void addDiscordCommand(String s) 
	{
		commands.add(s);
	}
}
