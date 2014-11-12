package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class ConfigSetup {
	
	// This file creates and loads the config.yml file
	
	private File configFile;
	private Plugin plugin;
	
	private ItemList rewards;
	private ItemList prices;
	
	public ConfigSetup(Plugin plugin){
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");
        
        loadRewards();
        loadprice();
        
        // Creates the config if it doesn't exists
        try{
        	firstRun();
        } catch (Exception e){
        	e.printStackTrace();
        }
        
	}
	
	private void firstRun() throws Exception {
		if(!configFile.exists()){
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
	
	private void loadRewards(){
		// Find the random items from the config
		HashMap<Integer, Material> randomItems = new HashMap<Integer, Material>();
		HashMap<Integer, Integer> randomItemsAmount = new HashMap<Integer, Integer>();
		HashMap<Integer, Short> randomItemsData = new HashMap<Integer, Short>();
        
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith("rewards.items.")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						Message.log("Found reward item: "+mat.name()+" in config!", plugin);
						randomItems.put(randomItems.size(), mat);
						if(plugin.getConfig().get("rewards.items."+mat.name()+".data")!=null){
							randomItemsData.put(randomItems.size()-1, (short) plugin.getConfig().getInt("rewards.items."+mat.name()+".data") );
						}
						if(plugin.getConfig().get("rewards.items."+mat.name()+".amount")!=null){
							randomItemsAmount.put(randomItems.size()-1, plugin.getConfig().getInt("rewards.items."+mat.name()+".amount"));
						}
					}
					
				}
			}
		}
		
		rewards = new ItemList(randomItems, randomItemsAmount, randomItemsData);
	}
	
	private void loadprice(){
		// Find the random items from the config
		HashMap<Integer, Material> priceItems = new HashMap<Integer, Material>();
		HashMap<Integer, Integer> priceItemsAmount = new HashMap<Integer, Integer>();
		HashMap<Integer, Short> priceItemsData = new HashMap<Integer, Short>();
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith("price.items.")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						Message.log("Found price item: "+mat.name()+" in config!", plugin);
						priceItems.put(priceItems.size(), mat);
						
						if(plugin.getConfig().get("price.items."+mat.name()+".data")!=null){
							priceItemsData.put(priceItems.size()-1, (short) plugin.getConfig().getInt("price.items."+mat.name()+".data") );
						}
						
						if(plugin.getConfig().get("rewards.items."+mat.name()+".amount")!=null){
							priceItemsAmount.put(priceItems.size()-1, plugin.getConfig().getInt("price.items."+mat.name()+".amount"));
						}
					}
					
				}
			}
		}
		
		prices = new ItemList(priceItems, priceItemsAmount, priceItemsData);
	}

	public ItemList getRewards() {
		return rewards;
	}

	public ItemList getPrices() {
		return prices;
	}
	
}
