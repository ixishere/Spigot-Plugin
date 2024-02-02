package me.ix.icks.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.ix.icks.Main;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenBook;

public class Utils {

	public static String removeSectionColor(String s) {
		String output = s;
		while(output.contains("§")) {
			int pos = output.indexOf("§");
			String edit = output.substring(0, pos) + output.substring(pos + 1);
			output = edit.substring(0, pos) + edit.substring(pos + 1);
		}
		return output;
	}
	
	public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
        		if(childFile.getName().contains("session.lock")) return;
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
	
	public static void copyWorld(File source, File target)
	{
	    try 
	    {
	        ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
	        if(!ignore.contains(source.getName())) 
	        {
	            if(source.isDirectory()) 
	            {
	                if(!target.exists())
	                target.mkdirs();
	                
	                String files[] = source.list();
	                for (String file : files) 
	                {
	                    File srcFile = new File(source, file);
	                    File destFile = new File(target, file);
	                    copyWorld(srcFile, destFile);
	                }
	            } 
	            else 
	            {
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target);
	                byte[] buffer = new byte[1024];
	                int length;
	                
	                while ((length = in.read(buffer)) > 0)
	                    out.write(buffer, 0, length);
	                
	                in.close();
	                out.close();
	            }
	        }
	    } catch (IOException e) {
	 
	    }
	}
	
	public static String[] getPiTempFreq() throws IOException 
	{
		String tempString = getLinuxCommandOutput("vcgencmd measure_temp");
        String freqString = getLinuxCommandOutput("vcgencmd measure_clock arm");
        
        String[] TempFreq = {(tempString.split("=")[1]), ((Integer.parseInt(freqString.split("=")[1])/1000000) + "Mhz")}; 
        
        return TempFreq;
	}
	
	public static String[] getPiMem() throws IOException
	{
		ArrayList<String> memLines = linuxOutputArray("cat /proc/meminfo");
		
		String memTotalStr = null;
		String memAvailableStr = null;
		
		for(String s : memLines)
		{
			if(s.startsWith("MemTotal:"))
				memTotalStr = s.replace(" ", "");
			
			if(s.startsWith("MemAvailable:"))
				memAvailableStr = s.replace(" ", "");
		}
		
		memTotalStr = memTotalStr.replaceAll("[^\\d.]", "");
		memAvailableStr = memAvailableStr.replaceAll("[^\\d.]", "");
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		double memTotal = Double.parseDouble(memTotalStr) / 1048576;
		double memAvailable = Double.parseDouble(memAvailableStr) / 1048576;
		
		double memUsed = memTotal - memAvailable;
		
		String[] mems = {df.format(memUsed), df.format(memTotal)};
		
		return mems;
	}
	
    public static String getLinuxCommandOutput(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		// Read the output from the command
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		    return s;
		}
		
		return null;
	}
    
    public static ArrayList<String> linuxOutputArray(String command) throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		ArrayList<String> lines = new ArrayList<>();
		
		String s = null;
		
		while ((s = stdInput.readLine()) != null) {
		    lines.add(s);
		}
		
		return lines;
	}
    
    public static void sendLinuxCommand(String command) throws IOException {
    	Runtime rt = Runtime.getRuntime();
		rt.exec(command);
    }
    
    public static void openRulesBook(Player player) {
		String nl = "\n";
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		bookMeta.setAuthor(wrapper.getServerName());
		bookMeta.setTitle("Laws of the land");
		
		ArrayList<String> pages = new ArrayList<String>();
		
		pages.add(0,
				"§6§l§n~ Server Rules ~" 
				+ nl + nl +
				"§9These are the §4Rules. §9I will actually instaban you if you fuck about after seeing these rules. Dont fuck about..."
				+ nl + nl +	
				 "§4Scroll through the pages and READ THEM PROPERLY"
				);
		
		pages.add(1,
				"§6§l§n~ Rule #1 §r" 
				+ nl + nl +
				"§9No §4§oGriefing."
				+ nl + nl +	
				 "§9Griefing includes §4but is not limited to: " + nl + nl +
				 "§r§9- Land griefing" + nl + 
				 "- Stealing items" + nl +
				 "- Unconsensual PvP" + nl +
				 "- Killing Pets"
				);
		
		pages.add(2,
				"§6§l§n~ Rule #2 §r" 
				+ nl + nl +
				"§9No §4§oCheating."
				+ nl + nl +	
				 "§9Cheating is an unfair advantage. Cheating includes §4but is not limited to: " + nl + nl +
				 "§r§9- Hacked Clients" + nl + 
				 "- Xray packs" + nl +
				 "- Abusing glitches"
				);
		
		pages.add(3,
				"§6§l§n~ Rule #3 §r" 
				+ nl + nl +
				"§9Dont abuse §4§oPlugins."
				+ nl + nl +	
				 "§9I've left the limits on some of the commands pretty high. This is for creativity. Not for you to fuck about " + nl + nl +
				 "- Two examples are: " + nl + "/Nick" + nl + "/icks CreateTag"
				);
		
		bookMeta.setPages(pages);
		
		book.setItemMeta(bookMeta);
		
		final int slot = player.getInventory().getHeldItemSlot();
        final ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, book);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutOpenBook(EnumHand.MAIN_HAND));
        player.getInventory().setItem(slot, old);
    }
    
    public static void playSoundToPlayer(Player player, Sound sound) {
    	World world = player.getWorld();
    	
    	world.playSound(player.getLocation(), sound, 1f, 1f);
    }
    
    public static void setPlayersCustomName(Player player) {
    	
    	String listName = Main.data.getConfig().getString("custom-name." + player.getName());
    	
    	String customName = "[" + listName + "§r]";
    	
    	if(player.isOnline()) 
    	{
    		player.setCustomName(customName);
    		player.setDisplayName(customName);
    		player.setPlayerListName(listName);
    		player.setCustomNameVisible(true);
    	}
    }
    
    public static boolean isInRegion(Location playerLocation, Location lowestPos, Location highestPos){
    	 
    	double x = playerLocation.getX();
    	double y = playerLocation.getY();
    	double z = playerLocation.getZ();
    	 
    	double lowx = lowestPos.getX();
    	double lowy = lowestPos.getY();
    	double lowz = lowestPos.getZ();
    	 
    	double highx = highestPos.getX();
    	double highy = highestPos.getY();
    	double highz = highestPos.getZ();
    	 
    	if(  (x <= highx && x >= lowx) && (y <= highy && y <= lowy) && (z <= highz && z >= lowz) )
    	{
    		return true;
    	}
    	else
    	{
    		return false;	
    	}
    }
    
    public static ItemStack getPlayerHead(String player)
    {
    	boolean isNewVersion = Arrays.stream(Material.values())
    			.map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
    	
    	Material type = Material.matchMaterial(isNewVersion ?  "PLAYER_HEAD" : "SKULL_ITEM");
    	
    	ItemStack item = new ItemStack(type, 1);
    	
    	if(!isNewVersion)
    		item.setDurability((short) 3);
    	
    	SkullMeta meta = (SkullMeta) item.getItemMeta();
    	meta.setOwner(player);
    	meta.setDisplayName("§6§l" + player + "'s Skull");
    	
    	item.setItemMeta(meta);
    	
    	return item;
    }
}
