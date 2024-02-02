package me.ix.icks.commands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.ix.icks.Main;
import me.ix.icks.utils.wrapper;
import net.md_5.bungee.api.ChatColor;

public class Block implements CommandExecutor {
	
	private final Main plugin;
	
	public Block(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) 
		{
			if(label.equalsIgnoreCase("Block")) 
			{	
				Player player = (Player) sender;
				
				for(ItemStack is : player.getInventory()) 
				{
					if(!hasInvSpace(player)) {
						player.sendMessage(wrapper.getStartText() + org.bukkit.ChatColor.RED + "Clear more inventory space.");
						return false;
					}
					
					if(is != null) {
						blockMethod(is, player);
					}
				}
				
			}
		}
		else 
		{
			sender.sendMessage("This is a player only command.");
		}
		return false;
	}
	
	
	public void blockMethod(ItemStack itemStack, Player player) {
		Inventory inv = player.getInventory();
		
		String ingotName = "default";
		String blockName = "default";
		
		if(itemStack.getType() == Material.DIAMOND) {
			ingotName = "diamond";
			blockName = "diamond_block";
		}
		if(itemStack.getType() == Material.IRON_INGOT) {
			ingotName = "iron_ingot";
			blockName = "iron_block";
		}
		if(itemStack.getType() == Material.GOLD_INGOT) {
			ingotName = "gold_ingot";
			blockName = "gold_block";
		}
		if(itemStack.getType() == Material.COAL) {
			ingotName = "coal";
			blockName = "coal_block";
		}
		if(itemStack.getType() == Material.REDSTONE) {
			ingotName = "redstone";
			blockName = "redstone_block";
		}
		if(itemStack.getType() == Material.LAPIS_LAZULI) {
			ingotName = "lapis_lazuli";
			blockName = "lapis_block";
		}
		if(itemStack.getType() == Material.EMERALD) {
			ingotName = "emerald";
			blockName = "emerald_block";
		}
		
		if(ingotName == "default") {
			return;	
		}
		
		ingotName = ingotName.toUpperCase();
		blockName = blockName.toUpperCase();
		
		int initialMinerals = itemStack.getAmount();
		int returnMinerals = 0;
		int returnBlocks = 0;
		
		if(initialMinerals >= 9) 
		{
			returnBlocks = initialMinerals / 9;
			returnMinerals = initialMinerals % 9;
			
			inv.removeItem( // Remove all ingots
					new ItemStack[] { new ItemStack(Material.getMaterial(ingotName), initialMinerals) }
			);
			
			inv.addItem( // Add blocks
					new ItemStack[] { new ItemStack(Material.getMaterial(blockName), returnBlocks) }
			);
			
			inv.addItem( // Add remaining ingots
					new ItemStack[] { new ItemStack(Material.getMaterial(ingotName), returnMinerals) }
			);
			
			player.sendMessage(wrapper.getStartText() + org.bukkit.ChatColor.GOLD
					+ "Converted " + Material.getMaterial(ingotName).name() + " to " 
					+ Material.getMaterial(blockName).name());
		}
		
	}
	
	public boolean hasInvSpace(Player player) {
		int slotsUsed = 0;

		for(ItemStack is : player.getInventory()) 
		{
			if(is != null) {
				slotsUsed++;
			}
		}
		
		if(player.getInventory().getHelmet() == null) {
			slotsUsed++;
		}
		if(player.getInventory().getChestplate() == null) {
			slotsUsed++;
		}
		if(player.getInventory().getLeggings() == null) {
			slotsUsed++;
		}
		if(player.getInventory().getBoots() == null) {
			slotsUsed++;
		}
		
		
		if(slotsUsed < 39)
		{
			return true;
		}
		
		return false;
	}
}