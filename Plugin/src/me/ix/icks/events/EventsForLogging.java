package me.ix.icks.events;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;

public class EventsForLogging implements Listener {

	private final Main plugin;
	
	public EventsForLogging(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoinLog(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		writeToLog(getDateAndTime() + player.getName() + " joined the server.");
	}
	
	@EventHandler
	public void onLeaveLog(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		
		writeToLog(getDateAndTime() + player.getName() + " left the server.");
	}
	
	 @EventHandler
	 public void onDeathLog(PlayerDeathEvent e) 
	 {
		 Player player = e.getEntity();
		 
		 int x = (int) player.getLocation().getX();
		 int y = (int) player.getLocation().getY();
		 int z = (int) player.getLocation().getZ();
		 
		 writeToLog(getDateAndTime() + player.getName() + " just died at {x:" + x + "}{y:" + y + "}{z:" + z + "}");
	 }
	 
	 @EventHandler
	 public void onBlockBreak(BlockBreakEvent e)
	 {
		 if(e.isCancelled())
			 return;
		 
		 if(e.getBlock().hasMetadata("PlayerPlaced"))
			 return;
		 
		 if(!isLogBlock(e.getBlock()))
			 return;
		 
		 int ores = 0;
		
		 for(int x = -5; x < 5; x++) 
         {
             for(int y = -5; y < 5; y++) 
             {
                 for(int z = -5; z < 5; z++) 
                 {
                     Block block = e.getBlock().getLocation().add(x,y,z).getBlock();
                     if(!block.hasMetadata("PlayerPlaced") && isLogBlock(block)) 
                     {
                         ores++;
                         block.setMetadata("PlayerPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
                     }
                 }
             }
         }
         
         String blockName = e.getBlock().getBlockData().getMaterial().name();
         
         Player player = e.getPlayer();
         
         String playerPos = 
         		" x:" + player.getLocation().getX() + 
         		" y:" + player.getLocation().getY() + 
         		" z:" + player.getLocation().getZ();
         
         writeToLog(getDateAndTime() + player.getName() + " mined " + ores + " " + blockName + " at location" + playerPos);
	 
         ores = 0;
	 }
	 
	 @EventHandler
	 public void onBlockPlace(BlockPlaceEvent e) 
	 {
		 if(e.isCancelled())
			 return;
		 
		 if(isLogBlock(e.getBlock())) 
		 {
			 e.getBlock().setMetadata("PlayerPlaced", new FixedMetadataValue(plugin, Boolean.valueOf(true)));
		 }
		 
		 if(e.getBlock().getType() == Material.TNT || 
			e.getBlock().getType() == Material.TNT_MINECART ||
			e.getBlock().getType() == Material.LEGACY_TNT) 
		 {
			 writeToLog(getDateAndTime() + e.getPlayer().getName() + " placed TNT"); 
		 }
	 }
	 
	 public boolean isLogBlock(Block block) 
	 {
		 if(block.getType().equals(Material.DIAMOND_ORE)
				 || block.getType().equals(Material.IRON_ORE)
				 || block.getType().equals(Material.GOLD_ORE)
				 || block.getType().equals(Material.EMERALD_ORE)
				 || block.getType().equals(Material.ANCIENT_DEBRIS)
				 || block.getType().equals(Material.COAL_ORE))
		 {
			 return true;
		 }
		 return false;
	 }
	 
	 public String getDateAndTime() 
	 {
		 LocalDateTime myDateObj = LocalDateTime.now();
		 DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		 DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
		 String formattedDate = myDateObj.format(date);
		 String formattedTime = myDateObj.format(time);

		 return "[" + formattedDate + "][" + formattedTime + "] ";
	 }
	 
	 public void writeToLog(String toWrite) {
		try {
			FileWriter chatBuffer = new FileWriter(plugin.logs.getLogFile() ,true);
			BufferedWriter mainChat = new BufferedWriter(chatBuffer);
			mainChat.write(toWrite);
			mainChat.newLine();
			mainChat.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}