package me.ix.icks.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.ix.icks.Main;

public class EventListener implements Listener {

	private final Main plugin;
	
	public EventListener(Main plugin)
	{
		this.plugin = plugin;
	}
    
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) // Item Renaming From Anvil
	{
		Inventory inv = e.getInventory();
		
		 if(inv.getType() != InventoryType.ANVIL)
			 return;
		 
		 int rawSlot = e.getRawSlot();
		 
         if(rawSlot == 2)
         {
        	 ItemStack itemStack = e.getCurrentItem();
        	 ItemMeta itemMeta = itemStack.getItemMeta();
        	 
        	 String formattedName = itemMeta.getDisplayName().replace("&", "§");
        	 
        	 if(!formattedName.contains("§")) 
        	 {
        		 return;
        	 }
        	 
        	 itemMeta.setDisplayName(formattedName + "§r");
        	 
        	 itemStack.setItemMeta(itemMeta);
         }
	}
	
	@EventHandler
	public void onSignText(SignChangeEvent event) 
	{
		event.setLine(0, event.getLine(0).replace("&", "§"));
		event.setLine(1, event.getLine(1).replace("&", "§"));
		event.setLine(2, event.getLine(2).replace("&", "§"));
		event.setLine(3, event.getLine(3).replace("&", "§"));
	}
	
	 @EventHandler
	 public void onDamage(EntityDamageByEntityEvent e) // Fix for the weird no hit bug
	 {
		 LivingEntity victim = (LivingEntity) e.getEntity();
		 victim.setNoDamageTicks(0);
	 }
	
	/*
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Location lowestPos = new Location(Bukkit.getWorlds().get(0), 532, 64, -418);
		Location highestPos = new Location(Bukkit.getWorlds().get(0), 538, 64, -412);
		
		if(me.ix.icks.utils.Utils.isInRegion(e.getPlayer().getLocation(), lowestPos, highestPos) && !wrapper.getTriggered()) 
		{
			wrapper.setTriggered(true);
			
			e.getPlayer().sendTitle("", "Entering Area", 10, 0, 10);
		}
		
		if(!me.ix.icks.utils.Utils.isInRegion(e.getPlayer().getLocation(), lowestPos, highestPos)) {
			wrapper.setTriggered(false);
		}
	}
	*/
	
	
    /*
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		{ // Saving Death Time
			plugin.data.getConfig().set("latest-death." + event.getEntity().getUniqueId(), System.currentTimeMillis());
			plugin.data.saveConfig();
		}
		
		{ // Striking Entity location on death for effect
		event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if(event.getPlayer() instanceof Player) {
			
			if(plugin.data.getConfig().getInt("jail-location.x") == 0 && 
					plugin.data.getConfig().getInt("jail-location.y") == 0 && 
					plugin.data.getConfig().getInt("jail-location1.z") == 0) {
						event.getPlayer().sendMessage(wrapper.getStartText() + "Jail has not been set up yet. You have been turned into a spectator. Contact OP");
						event.getPlayer().setGameMode(GameMode.SPECTATOR);
				}else {	
					new BukkitRunnable() {
						@Override
						public void run() {
							
							event.getPlayer().teleport(new Location(Bukkit.getWorlds().get(0), 
								plugin.data.getConfig().getInt("jail-location.x"),
								plugin.data.getConfig().getInt("jail-location.y"),
								plugin.data.getConfig().getInt("jail-location.z")));
							
							event.getPlayer().sendMessage(wrapper.getStartText() + ChatColor.RED + "" + ChatColor.BOLD + "YOU HAVE BEEN JAILED");
							event.getPlayer().setGameMode(GameMode.ADVENTURE);
						}
					}.runTaskLater(plugin, 5);
				}
			}
			
		}
	*/

    /*
    boolean activated = false;
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
    	Entity player = event.getPlayer();
    	
    	double x = player.getLocation().getX();
    	double y = player.getLocation().getY();
    	double z = player.getLocation().getZ();
    	
    	for(Entity e : Bukkit.getWorld("world").getEntities()) {
    		for(Entity p : Bukkit.getOnlinePlayers()) {
    			if(e.hasMetadata(p.getName())) {
    				double entX = e.getLocation().getX();
    				double entZ = e.getLocation().getZ();
    				
    				if(activated) {
    					if(!((x < entX + 3 && x > entX - 3) && (z < entZ + 3 && z > entZ - 3))) {
    						activated = false;
    						Bukkit.broadcastMessage("you left the message zone");
    					}else {
    						return;
    					}
    				}
    				
    				if((x < entX + 3 && x > entX - 3) && (z < entZ + 3 && z > entZ - 3)) {
    					Bukkit.broadcastMessage("You are standing in the message zone!");
    					activated = true;
    				}else {
    					activated = false;
    				}
    				
    			}
    		}
    	}
    }
    */   
}
