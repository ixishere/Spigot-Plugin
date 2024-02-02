package me.ix.icks.events;

import me.ix.icks.utils.wrapper;

import java.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DeathChestTask implements Runnable
{
	private Player player;
	private Inventory drops;
	private Location deathLocation;
	private List<Material> chestable_blocks;

	public DeathChestTask(Player P, Inventory D, Location L, List<Material> CB) {
		player = P;
		drops = D;
		deathLocation = L;
		
		this.chestable_blocks = new ArrayList<Material>();
		initChestable();
	}

	private void initChestable() {
    	chestable_blocks.clear();
		chestable_blocks.add(Material.WATER);
		chestable_blocks.add(Material.LAVA);
		chestable_blocks.add(Material.WHEAT);
		chestable_blocks.add(Material.POTATOES);
		chestable_blocks.add(Material.CARROTS);
		chestable_blocks.add(Material.BEETROOT);
		chestable_blocks.add(Material.PUMPKIN_STEM);
		chestable_blocks.add(Material.ATTACHED_PUMPKIN_STEM);
		chestable_blocks.add(Material.MELON_STEM);
		chestable_blocks.add(Material.ATTACHED_MELON_STEM);
		chestable_blocks.add(Material.SUGAR_CANE);
		chestable_blocks.add(Material.NETHER_WART);
		chestable_blocks.add(Material.KELP_PLANT);
		chestable_blocks.add(Material.KELP);
		chestable_blocks.add(Material.SEAGRASS);
		chestable_blocks.add(Material.TALL_SEAGRASS);
		chestable_blocks.add(Material.GRASS);
		chestable_blocks.add(Material.TALL_GRASS);
		chestable_blocks.add(Material.FERN);
		chestable_blocks.add(Material.LARGE_FERN);
	}
	
	@Override
	public void run() {
		BlockFace secondChest = BlockFace.SELF;
        int numDrops = 0;
        ItemStack d[];
		for (int i = 0; i < drops.getContents().length; i++) {
			if (drops.getContents()[i] != null &&
			    ! drops.getContents()[i].getType().equals(Material.AIR) )
				numDrops++;
		}
		
		if (! blockIsChestable(deathLocation.getBlock()) ) {
			if (blockIsChestable(deathLocation.getBlock().getRelative(BlockFace.NORTH)))
					deathLocation.add(0,0,-1);
			else if (blockIsChestable(deathLocation.getBlock().getRelative(BlockFace.SOUTH)))
				deathLocation.add(0,0,1);				
			else if (blockIsChestable(deathLocation.getBlock().getRelative(BlockFace.EAST)))
				deathLocation.add(1,0,0);				
			else if (blockIsChestable(deathLocation.getBlock().getRelative(BlockFace.WEST)))
				deathLocation.add(-1,0,0);
			else if (blockIsChestable(deathLocation.getBlock().getRelative(BlockFace.UP)))
				deathLocation.add(0,1,0);
			else {
				d = drops.getContents();
				for (int i=0 ; i < d.length ; i++){
					if (d[i] != null) {
						deathLocation.getWorld().dropItemNaturally(deathLocation, d[i]);
					}
				}
				player.sendMessage("§cYour items are on the ground. Hurry up and retrieve them!");
				
				return;
			}
		}

		deathLocation.getBlock().setType(Material.CHEST);

		if (numDrops > 27) {
			secondChest = placeSecondChest(deathLocation.getBlock());
		}

		Chest chest1 = (Chest)deathLocation.getBlock().getState();
		Chest chest2 = (Chest)deathLocation.getBlock().getRelative(secondChest).getState();

		int chest_count = 0;
		int ground_count = 0;
		d = drops.getContents();
		for (int i=0 ; i < d.length ; i++){
			if (d[i] != null) {
				if (chest_count < 27) {
					chest1.getInventory().addItem(d[i]);
					chest_count++;
				} else if (chest_count < 54 && secondChest != BlockFace.SELF) {
					chest2.getInventory().addItem(d[i]);
					chest_count++;
				}
				else {
					deathLocation.getWorld().dropItemNaturally(deathLocation, d[i]);
					ground_count++;
				}
			}
		}
		if (ground_count > 0) {
			player.sendMessage(wrapper.getStartText() + "§6 The majority of your items are in a deathchest!");
			player.sendMessage(wrapper.getStartText() + "§6Hurry up and grab the rest!");
		} else {
			player.sendMessage(wrapper.getStartText() + "§6All of your items are safe in your death chest!");
		}
		
	}

	private boolean blockIsChestable(Block block) {
		if ( block.isEmpty() || chestable_blocks.contains(block.getType()) ) {
			return true;
		}
		return false;
	}

	private BlockFace placeSecondChest(Block block)
	{
		if (blockIsChestable(block.getRelative(BlockFace.NORTH)))
		{
			block.getRelative(BlockFace.NORTH).setType(Material.CHEST);
			return BlockFace.NORTH;
		}
		if (blockIsChestable(block.getRelative(BlockFace.EAST)))
		{
			block.getRelative(BlockFace.EAST).setType(Material.CHEST);
			return BlockFace.EAST;
		}
		if (blockIsChestable(block.getRelative(BlockFace.SOUTH)))
		{
			block.getRelative(BlockFace.SOUTH).setType(Material.CHEST);
			return BlockFace.SOUTH;
		}
		if (blockIsChestable(block.getRelative(BlockFace.WEST)))
		{
			block.getRelative(BlockFace.WEST).setType(Material.CHEST);
			return BlockFace.WEST;
		}
		return BlockFace.SELF;
	}
}