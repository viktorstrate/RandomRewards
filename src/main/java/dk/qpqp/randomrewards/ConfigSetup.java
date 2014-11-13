package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
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
        
        rewards = loadItems("rewards.items");
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
		// Loads items from the config
		HashMap<Integer, Material> items = new HashMap<Integer, Material>();
		HashMap<Integer, Integer> itemsAmount = new HashMap<Integer, Integer>();
		HashMap<Integer, Short> itemsData = new HashMap<Integer, Short>();
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith(pathToItems+".")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						Message.log("Found "+pathToItems+": "+mat.name()+" in config!", plugin);
						items.put(items.size(), mat);
						
						if(plugin.getConfig().get(pathToItems+"."+mat.name()+".data")!=null){
							itemsData.put(items.size()-1, (short) plugin.getConfig().getInt(pathToItems+"."+mat.name()+".data") );
						}
						
						if(plugin.getConfig().get(pathToItems+"."+mat.name()+".amount")!=null){
							itemsAmount.put(items.size()-1, plugin.getConfig().getInt(pathToItems+"."+mat.name()+".amount"));
						}
					}
					
				}
			}
		}
		
		HashMap<Integer, ConfigItem> configItems = new HashMap<Integer, ConfigItem>();
		for(int i = 0; i<items.size(); i++){
			configItems.put(i, new ConfigItem(items.get(i), itemsAmount.get(i), itemsData.get(i)));
		}
		
		return configItems;
	}
	
	public ConfigItem loadItem(String pathToItem){
		// Loads items from the config
		Material item = Material.STICK;
		int itemAmount = -1;
		Short itemData = 0;
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith(pathToItem+".")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						item = mat;
						
						if(plugin.getConfig().get(pathToItem+"."+mat.name()+".data")!=null){
							itemData = (short) plugin.getConfig().getInt(pathToItem+"."+mat.name()+".data");
						}
						
						if(plugin.getConfig().get(pathToItem+"."+mat.name()+".amount")!=null){
							itemAmount = plugin.getConfig().getInt(pathToItem+"."+mat.name()+".amount");
						}
					}
					
				}
				break;
			}
		}
		
		
		
		if(itemAmount<=0){
			Message.log("Item "+pathToItem+" loaded", plugin);
			return new ConfigItem(item, itemAmount, itemData);
		} else {
			Message.warning("Item "+pathToItem+" didn't load correctly, check the config", plugin);
			return null;
		}
		
	}
	
	public ArrayList<Enchantment> loadEnchantments(String pathToEnchantments, Material mat){
		ArrayList<Enchantment> list = new ArrayList<Enchantment>();
		Set<String> encKeys = plugin.getConfig().getKeys(true);
		for(String str: encKeys){
			if(str.startsWith(pathToEnchantments+".")){
				for(Enchantment enc: Enchantment.values()){
					if(str.endsWith(enc.getName())){
						list.add(enc);
						
					}
				}
			}
		}
		return null;
		
		
	}
	
	public ConfigItem loadBlock(String pathToBlock){
		// Loads items from the config
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
