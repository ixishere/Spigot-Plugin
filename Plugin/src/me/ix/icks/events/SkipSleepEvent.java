package me.ix.icks.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class SkipSleepEvent implements Listener {

	private final Main plugin;
	
	public SkipSleepEvent(Main plugin) {
		this.plugin = plugin;
	}
    
    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) { // NIGHT SKIP
    	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	    @Override
    	    public void run() {
    	    	World world = event.getPlayer().getWorld();
    	    	
    			if(world.isThundering()) {
    				if(!event.getPlayer().isSleeping()) return;
    				world.setThundering(false);
    				 Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.RED + " has slept to skip the thunderstorm!");
    			}
    	    	
    	    	if(!isDay()){
    	    		if(!event.getPlayer().isSleeping()) return;
    	    		long Relative_Time = 24000 - world.getTime();
    				world.setFullTime(world.getFullTime() + Relative_Time);
    				Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.RED + " has slept to skip the night!");
    	    	}
    	    }
    	}, 40L);
    }
    
    public boolean isDay() { // NIGHT CHECK FOR NIGHT SKIP
        long time = Bukkit.getWorld("world").getTime();

        return time < 12300 || time > 23850;
    }
}