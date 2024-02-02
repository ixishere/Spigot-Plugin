package me.ix.icks;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.ix.icks.commands.Block;
import me.ix.icks.commands.Commands;
import me.ix.icks.commands.Craft;
import me.ix.icks.commands.DelHome;
import me.ix.icks.commands.Enderchest;
import me.ix.icks.commands.Hand;
import me.ix.icks.commands.Home;
import me.ix.icks.commands.Icks;
import me.ix.icks.commands.IcksTC;
import me.ix.icks.commands.InvSee;
import me.ix.icks.commands.ListHomes;
import me.ix.icks.commands.Nick;
import me.ix.icks.commands.Performance;
import me.ix.icks.commands.RealName;
import me.ix.icks.commands.Rename;
import me.ix.icks.commands.Rules;
import me.ix.icks.commands.SetHome;
import me.ix.icks.commands.Spawn;
import me.ix.icks.commands.TPA;
import me.ix.icks.commands.TPACCEPT;
import me.ix.icks.commands.Trash;
import me.ix.icks.discordBot.DiscordMain;
import me.ix.icks.events.ChatEvent;
import me.ix.icks.events.DeathEvent;
import me.ix.icks.events.EventListener;
import me.ix.icks.events.EventsForLogging;
import me.ix.icks.events.JoinLeave;
import me.ix.icks.events.ServerPing;
import me.ix.icks.events.SkipSleepEvent;
import me.ix.icks.events.XrayCheckEvent;
import me.ix.icks.files.ConfigManager;
import me.ix.icks.files.DataManager;
import me.ix.icks.files.LogManager;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class Main extends JavaPlugin {
	
	public static DataManager data;
	public static LogManager logs;
	public static ConfigManager config;
	
	public EventListener MainEvents = new EventListener(this);
	public JoinLeave joinLeave = new JoinLeave(this);
	public ServerPing serverPing = new ServerPing(this);
	public ChatEvent chatevent = new ChatEvent(this);
	public DeathEvent deathevent = new DeathEvent(this);
	public SkipSleepEvent skipsleepevent = new SkipSleepEvent(this);
	public XrayCheckEvent xraycheckevent = new XrayCheckEvent(this);
	public EventsForLogging eventsForLogging = new EventsForLogging(this);
	
	public DiscordMain discordBotThread = new DiscordMain(this);
	
	@Override
	public void onEnable() {
		// Config Files
		this.data = new DataManager(this);
		this.data.reloadConfig();
		this.logs = new LogManager(this);
		this.config = new ConfigManager(this);
		this.config.reloadConfig();
		
		// Initialise Events and Commands
		initEvents();
		initCommands();
		
		repeatingSystemInfo();
		repeatingTip();
		
		if(config.getConfig() != null) {
			if(config.getConfig().contains("discord-bot.enabled")) {
				if(config.getConfig().getBoolean("discord-bot.enabled"))
					discordBotThread.start();
			}
		}
    }

	@Override
	public void onDisable() {
		
	}
	
	public void repeatingSystemInfo()
	{
		// System info
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
    	    @Override
    	    public void run() {
    	    	try {
    	    		String temperature = Utils.getPiTempFreq()[0];
    	    		String frequency = Utils.getPiTempFreq()[1];
    	    		String[] memory = Utils.getPiMem();
    	    		
					Bukkit.broadcastMessage(wrapper.getStartText() +
							"§C§l< Pi Status >"
							+ "\n§b§lTemperature: §r§6" + temperature 
							+ "\n§b§lFrequency: §r§6" + frequency
							+ "\n§b§lMemory: §r§6" + memory[0] + "/" + memory[1] + " GB");
				} catch (IOException e) {
					e.printStackTrace();
				}
    	    }
    	}, 0L, ((20L * 60L) * 25L ));
	}
	
	public void repeatingTip()
	{
		// System info
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
    	    @Override
    	    public void run() {
    	    	Random random = new Random();
    	    	
    	    	int randomNum = random.nextInt(5);
    	    	
    	    	Bukkit.broadcastMessage(randomNum + "");
    	    	
    	    	String tip = wrapper.getStartText() + ChatColor.GOLD + "Tip: " + ChatColor.AQUA;
    	    	
    	    	switch(randomNum) {
    	    	case 1:
    	    		tip += "use /commands to view the list of useful commands";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 2:
    	    		tip += "You can use colour codes to colour chat messages, nicknames and even signs!";
    	    		Bukkit.broadcastMessage(tip);
    	    		Bukkit.broadcastMessage(ChatColor.BLUE + 
    	    				"Find them here: https://www.planetminecraft.com/blog/server-color-codes-updated/");
    	    		break;
    	    	case 3:
    	    		tip += "If you get lost you can always teleport to spawn using" + ChatColor.BLUE + "/spawn";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 4:
    	    		tip += "Using" + ChatColor.BLUE + " /sethome {name}" + 
    	    	ChatColor.GOLD + "and " + ChatColor.BLUE + "/home" + ChatColor.GOLD + "can be used for quick travel";
    	    		Bukkit.broadcastMessage(tip);
    	    		break;
    	    	case 5:
    	    		tip += "";
    	    		break;
    	    	}
    	    }
    	}, (20L * 30L), ((20L * 60L) * 10L ));
	}
	
	public void initEvents()
	{
		// Events
		this.getServer().getPluginManager().registerEvents(MainEvents, this);
		this.getServer().getPluginManager().registerEvents(joinLeave, this);
		this.getServer().getPluginManager().registerEvents(serverPing, this);
		this.getServer().getPluginManager().registerEvents(chatevent, this);
		this.getServer().getPluginManager().registerEvents(deathevent, this);
		this.getServer().getPluginManager().registerEvents(skipsleepevent, this);
		this.getServer().getPluginManager().registerEvents(xraycheckevent, this);
		this.getServer().getPluginManager().registerEvents(eventsForLogging, this);
	}
	
	public void initCommands()
	{
		// Commands && Tab Completer
		this.getCommand("Icks").setExecutor(new Icks(this));
		this.getCommand("Icks").setTabCompleter(new IcksTC());
		
		this.getCommand("Nick").setExecutor(new Nick(this));
		this.getCommand("RealName").setExecutor(new RealName());
		
		this.getCommand("SetHome").setExecutor(new SetHome(this));
		this.getCommand("Home").setExecutor(new Home(this));
		this.getCommand("DelHome").setExecutor(new DelHome(this));
		this.getCommand("Homes").setExecutor(new ListHomes(this));
		
		this.getCommand("Spawn").setExecutor(new Spawn(this));
		
		this.getCommand("Trash").setExecutor(new Trash(this));
		this.getCommand("Bin").setExecutor(new Trash(this));
		
		this.getCommand("Rules").setExecutor(new Rules(this));
		
		this.getCommand("EChest").setExecutor(new Enderchest(this));
		this.getCommand("EnderChest").setExecutor(new Enderchest(this));
		this.getCommand("Craft").setExecutor(new Craft(this));
		
		this.getCommand("InvSee").setExecutor(new InvSee(this));
		
		this.getCommand("TPA").setExecutor(new TPA(this));
		this.getCommand("TPACCEPT").setExecutor(new TPACCEPT(this));
		
		this.getCommand("Rename").setExecutor(new Rename(this));
		
		this.getCommand("Performance").setExecutor(new Performance(this));
		
		this.getCommand("Hand").setExecutor(new Hand(this));
		
		this.getCommand("Block").setExecutor(new Block(this));
		
		this.getCommand("Commands").setExecutor(new Commands(this));
	}
}