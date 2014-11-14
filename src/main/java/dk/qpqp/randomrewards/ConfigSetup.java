package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ConfigSetup {
	
	// This file creates and loads the config.yml file
	
	private File configFile;
	private Plugin plugin;
	
	private HashMap<Integer, ConfigItem> rewards, prices;
	private ConfigItem rewardBlock;
	
	public ConfigSetup(Plugin plugin){
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");
        
        // Creates the config if it doesn't exists
        try{
        	firstRun();
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        rewards = loadItems("rewards");
        prices = loadItems("price.items");
        rewardBlock = loadItem("rewardBlock");
        
	}
	
	private void firstRun() throws Exception {
		if(!configFile.exists()){
			Message.log("Config not found creating one...", plugin);
	        configFile.getParentFile().mkdirs();
	        copy(plugin.getResource("config.yml"), configFile);
	    }
	}
	// Is used to, copy the config.yml from this plugin.jar to the folder plugins/RandomRewards/
	private void copy(InputStream in, File file){
		try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public HashMap<Integer, ConfigItem> loadItems(String pathToItems){

		return new HashMap<Integer, ConfigItem>();
	}
	
	public ConfigItem loadItem(String pathToItem){
		// Loads items from the config.yml
		Material item = Material.STICK;
		int itemAmount = -1;
		Short itemData = 0;
		ArrayList<ConfigEnchantment> enchantments = new ArrayList<ConfigEnchantment>();
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith(pathToItem+".")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						item = mat;
						
						// Loads the datavalue of the item(s)
						if(plugin.getConfig().get(pathToItem+"."+mat.name()+".data")!=null){
							itemData = (short) plugin.getConfig().getInt(pathToItem+"."+mat.name()+".data");
						}
						
						// Loads the amount of items
						if(plugin.getConfig().get(pathToItem+"."+mat.name()+".amount")!=null){
							itemAmount = plugin.getConfig().getInt(pathToItem+"."+mat.name()+".amount");
						}
						
						// Loads the enchantments
						enchantments = loadEnchantments(pathToItem+"."+mat.name());
						
					}
					
				}
				break;
			}
		}
		
		
		
		if(itemAmount<=0){
			Message.log("Item "+pathToItem+" loaded", plugin);
			return new ConfigItem(item, itemAmount, itemData, enchantments);
		} else {
			Message.warning("Item "+pathToItem+" didn't load correctly, check the config", plugin);
			return null;
		}
		
	}
	
	// Loads enchantments from the config, function used by function loadItems() and loadItem()
	public ArrayList<ConfigEnchantment> loadEnchantments(String pathToEnchantments){
		ArrayList<ConfigEnchantment> list = new ArrayList<ConfigEnchantment>();
		
		// A key with all the nodes in the config.yml
		Set<String> encKeys = plugin.getConfig().getKeys(true);
		
		// Loops through the nodes in the config.yml
		for(String str: encKeys){
			// If found the path to the enchantments
			if(str.startsWith(pathToEnchantments+".")){
				// Loops through all the avaliable enchantment types
				for(Enchantment enc: Enchantment.values()){
					// If found one matching the one(s) in the config.yml
					if(str.endsWith(enc.getName())){
						// Sends message and adds it to the list
						Message.log("With enchantment: "+enc.getName(), plugin);
						int level = 1;
						level = plugin.getConfig().getInt(pathToEnchantments+"."+enc.getName()+".level");
						list.add(new ConfigEnchantment(enc, level));
					}
				}
			}
		}
		return list;
	}
	
	public ConfigItem loadBlock(String pathToBlock){
		// Loads items from the config.yml
		Material item = Material.STONE;
		Short itemData = -1;
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith(pathToBlock+".")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						item = mat;
						
						if(plugin.getConfig().get(pathToBlock+"."+mat.name()+".data")!=null){
							itemData = (short) plugin.getConfig().getInt(pathToBlock+"."+mat.name()+".data");
						}
					}
					
				}
				break;
			}
		}
		if(itemData>=0){
			Message.log("Block "+pathToBlock+" loaded", plugin);
			return new ConfigItem(item, 1, itemData);
		} else {
			Message.warning("Block "+pathToBlock+" didn't load correctly, check the config", plugin);
			return null;
		}
		
		
	}
	
	public ArrayList<String> regxChecker(String theRegx, String str2Check){
		ArrayList<String> list = new ArrayList<String>();
		
		List<ItemStack> lis = new ArrayList<ItemStack>();
		lis.add(new ItemStack(Material.DIAMOND));
		lis.add(new ItemStack(Material.STICK));
		lis.add(new ItemStack(Material.GOLD_AXE));
		plugin.getConfig().set("itm", lis);
		plugin.saveConfig();
		
		return list;
	}

	public HashMap<Integer, ConfigItem> getRewards() {
		return rewards;
	}

	public HashMap<Integer, ConfigItem> getPrices() {
		return prices;
	}
	
	public ConfigItem getRewardBlock(){
		return rewardBlock;
	}
}
