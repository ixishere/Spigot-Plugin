package me.ix.icks.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class XrayCheckEvent implements Listener {

	private final Main plugin;
	
	public XrayCheckEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onDiamondsPlace(BlockPlaceEvent e) {
        if((!e.isCancelled()) && (e.getBlock().getType().equals(Material.DIAMOND_ORE))) {
            e.getBlock().setMetadata("DiamondPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
        }
    }
	
    @EventHandler
    public void onDiamondBreak(BlockBreakEvent event) {
        if((!event.isCancelled()) && (event.getBlock().getType().equals(Material.DIAMOND_ORE)) && (!event.getBlock().hasMetadata("DiamondPlaced"))) {
            int diamonds = 0;
            for(int x = -5; x < 5; x++) {
                for(int y = -5; y < 5; y++) {
                    for(int z = -5; z < 5; z++) {
                        Block block = event.getBlock().getLocation().add(x,y,z).getBlock();
                        if((block.getType().equals(Material.DIAMOND_ORE)) && (!block.hasMetadata("DiamondPlaced"))) {
                            diamonds ++;
                            block.setMetadata("DiamondPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
                        }
                    }
                }
            }
            for(Player p : plugin.getServer().getOnlinePlayers()) {
				if(p.isOp()) {
					p.sendMessage(wrapper.getStartText() + event.getPlayer().getName() + ChatColor.AQUA + " just found a vein of " + diamonds + " diamonds! [OP MESSAGE]");
				}
        }
            Bukkit.broadcastMessage(wrapper.getStartText() + event.getPlayer().getName() + ChatColor.AQUA + " just found a vein of " + diamonds + " diamonds!");
      }
    }
    
	@EventHandler
    public void onAncientPlace(BlockPlaceEvent e) {
        if((!e.isCancelled()) && (e.getBlock().getType().equals(Material.ANCIENT_DEBRIS))) {
            e.getBlock().setMetadata("DebrisPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
        }
    }
	
    @EventHandler
    public void onAncientBreak(BlockBreakEvent event) {
        if((!event.isCancelled()) && (event.getBlock().getType().equals(Material.ANCIENT_DEBRIS)) && (!event.getBlock().hasMetadata("DebrisPlaced"))) {
            int debris = 0;
            for(int x = -5; x < 5; x++) {
                for(int y = -5; y < 5; y++) {
                    for(int z = -5; z < 5; z++) {
                        Block block = event.getBlock().getLocation().add(x,y,z).getBlock();
                        if((block.getType().equals(Material.ANCIENT_DEBRIS)) && (!block.hasMetadata("DebrisPlaced"))) {
                        	debris ++;
                            block.setMetadata("DebrisPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
                        }
                    }
                }
            }
            for(Player p : plugin.getServer().getOnlinePlayers()) {
				if(p.isOp()) {
					p.sendMessage(wrapper.getStartText() + event.getPlayer().getName() + ChatColor.AQUA + " just found a vein of " + debris + " ancient debris! [OP MESSAGE]");
				}
        }
            Bukkit.broadcastMessage(wrapper.getStartText() + event.getPlayer().getName() + ChatColor.AQUA + " just found a vein of " + debris + " ancient debris!");
      }
    }
}