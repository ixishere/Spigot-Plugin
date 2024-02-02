package me.ix.icks.commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.ix.icks.Main;
import me.ix.icks.utils.Utils;
import me.ix.icks.utils.wrapper;

public class Icks implements CommandExecutor {
	
	private final Main plugin;
	
	public Icks(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("icks")) {
			// player
			if(sender instanceof Player) {
				
				if(args.length == 0) {
					sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: Info, Createtag, Deletetag, Reload");
					return true;	
				}
				if(args.length > 0) {
					if(args[0].equalsIgnoreCase("reload")) {
						if(sender.isOp()) {
							plugin.data.reloadConfig();
							if(plugin.data.getConfig().contains("reload-message")) {
								Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.RED + plugin.data.getConfig().getString("reload-message"));
							}
							return true;
						}else {
							sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have permission for this command.");
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("info")) {
						sender.sendMessage(wrapper.getStartText() + wrapper.getName()
						+ " is " + wrapper.getCreator() + "'s first attempt at making a plugin. He is at version " + wrapper.getVersion());
					}
					if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: Info, Createtag, Deletetag, Reload");
						return true;
					}
					if(args[0].equalsIgnoreCase("createtag")) {
						
						if(args.length < 2) {
							sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Usage: /icks CreateTag [Message here]");
							return true;
						}
						
						double playerX = ((Player) sender).getLocation().getX();
						double playerY = ((Player) sender).getLocation().getY();
						double playerZ = ((Player) sender).getLocation().getZ();
						
						if(((Player) sender).getWorld() != Bukkit.getWorld("world")){
							sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Only works in overworld for now!");
							return true;
						}
						
						if(playerX == 0 || playerY == 0 || playerZ == 0) {
							sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Cannot create tag at 0, 0, 0");
							return true;
						}
							
						if(plugin.data.getConfig().contains("tag-location." + sender.getName())) {
							double x = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".x");
							double y = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".y");
							double z = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".z");
							
							if(x != 0 && y != 0 && z != 0) {
								sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Cannot create more than 1 tag. Use /icks DeleteTag");
								return true;
							}
						}
						
						StringBuilder buffer = new StringBuilder();
						for(int i = 1; i < args.length; i++)
						{
						    buffer.append(' ').append(args[i]);
						}
						sender.sendMessage("Tag Created");
						
						ArmorStand entityToSpawn = (ArmorStand) Bukkit.getWorld("world").spawnEntity(((Player) sender).getPlayer().getLocation(), EntityType.ARMOR_STAND);
						
						String customName = buffer.toString().replaceAll("&","§");
						
						entityToSpawn.setCustomName(customName + " ");
						entityToSpawn.setCustomNameVisible(true);
						entityToSpawn.setGravity(false);
						entityToSpawn.setAI(false);
						entityToSpawn.setVisible(false);
						entityToSpawn.setCollidable(false);
						//entityToSpawn.setMetadata(sender.getName(), new FixedMetadataValue(plugin, "tagtest"));
						entityToSpawn.addScoreboardTag(sender.getName());
						
						plugin.data.getConfig().set("tag-location." + sender.getName() + ".x", ((Player) sender).getLocation().getX());
						plugin.data.getConfig().set("tag-location." + sender.getName() + ".y", ((Player) sender).getLocation().getY());
						plugin.data.getConfig().set("tag-location." + sender.getName() + ".z", ((Player) sender).getLocation().getZ());
						
						plugin.data.saveConfig();
						
					}
					if(args[0].equalsIgnoreCase("deletetag")) {
						if(plugin.data.getConfig().contains("tag-location." + sender.getName())) {
							double x = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".x");
							double y = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".y");
							double z = plugin.data.getConfig().getDouble("tag-location." + sender.getName() + ".z");
							
							if(((Player) sender).getWorld() != Bukkit.getWorld("world")){
								sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Only works in overworld for now!");
								return true;
							}
							
							if(x == 0 && y == 0 && z == 0) {
								sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "You do not have a tag, use /icks CreateTag [Text]");
								return true;
							}else {
								 Bukkit.getWorld("world").getChunkAt(new Location(Bukkit.getWorld("world"), x, y, z)).load();
								 
								 for(Entity entity : Bukkit.getWorld("world").getEntities()){
									 //if(entity.hasMetadata(sender.getName())) {
									if(entity.getScoreboardTags().contains(sender.getName())) {
									 	entity.remove();
										plugin.data.getConfig().set("tag-location." + sender.getName() + ".x", 0);
										plugin.data.getConfig().set("tag-location." + sender.getName() + ".y", 0);
										plugin.data.getConfig().set("tag-location." + sender.getName() + ".z", 0);
										plugin.data.saveConfig();
										sender.sendMessage(wrapper.getStartText() + ChatColor.RED + "Tag has been removed");
										Bukkit.getWorld("world").getChunkAt(new Location(Bukkit.getWorld("world"), x, y, z)).unload();
									 }
					             }
							}
						}
					}
					if(args[0].equalsIgnoreCase("backup")) {
						
						Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.RED + "Backing server up. Server might lag.");
						
						Bukkit.savePlayers();
						
				        for (World world : Bukkit.getWorlds()) {
				            world.save();
				        }
						
						String rootPath = plugin.getServer().getWorldContainer().getAbsolutePath();

						rootPath = rootPath.substring(0, rootPath.length() - 1);
						
						String sourceFile = rootPath + "world";
						
				        FileOutputStream fos;
						try {
							File file = new File(rootPath + "backups");
							if(!file.exists()) {
								file.mkdir();
							}

							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
							String dateString = format.format(new Date());
							
							fos = new FileOutputStream("backups/" + dateString + ".zip");
					        ZipOutputStream zipOut = new ZipOutputStream(fos);
					        File fileToZip = new File(sourceFile);
					        Utils.zipFile(fileToZip, fileToZip.getName(), zipOut);
					        zipOut.close();
					        fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						Bukkit.broadcastMessage(wrapper.getStartText() + ChatColor.RED + "Backup Complete!");
					}
				}
			}
			else {
				// console
				sender.sendMessage("wagwan console person!");
			}
		}
		
		return false;
	}

}
