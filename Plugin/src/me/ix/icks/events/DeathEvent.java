package me.ix.icks.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class DeathEvent implements Listener {

	private final Main plugin;
	private List<Material> chestable_blocks;
	
	
	public DeathEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void deathCoords(PlayerDeathEvent event) 
    {
    	Entity player = event.getEntity();
    	Location loc = player.getLocation();
    	
    	int x = (int) loc.getX();
    	int y = (int) loc.getY();
    	int z = (int) loc.getZ();
    	
    	Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.GOLD + player.getName() + ChatColor.RED +
    			" just died at: X: " + x + " Y: " + y + " Z: " + z);
    }
    
    @EventHandler
    public void deathChest(PlayerDeathEvent event) 
    {
    	Player player = event.getEntity();
		Location deathLocation = player.getLocation();
		int numDrops = event.getDrops().size();
		Inventory inventory;

		if (numDrops > 0 ) {
			inventory = plugin.getServer().createInventory(null, ( ( (numDrops + 8) / 9) * 9) );
			for (ItemStack item : event.getDrops()) {
				inventory.addItem(item);
			}

			plugin.getServer().getScheduler().runTaskLater((Plugin)plugin, (Runnable) new DeathChestTask(
							player, inventory, deathLocation, chestable_blocks), 5L);
			event.getDrops().clear();
		}
		
    }
    
    @EventHandler
    public void deathHead(PlayerDeathEvent event) 
    {
    	Player deadPlayer = event.getEntity();
    	
    	if(deadPlayer.getKiller() instanceof Player)
    	{
        	event.getDrops().add(me.ix.icks.utils.Utils.getPlayerHead(deadPlayer.getName()));
    	}
    }
    
}