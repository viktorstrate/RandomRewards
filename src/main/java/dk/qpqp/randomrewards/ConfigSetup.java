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
	
	private HashMap<Integer, ConfigItem> rewards;
	private HashMap<Integer, ConfigItem> prices;
	
	public ConfigSetup(Plugin plugin){
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");
        
        rewards = loadItem("rewards.items");
        prices = loadItem("price.items");
        
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
	
	public HashMap<Integer, ConfigItem> loadItem(String pathToItems){
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

	public HashMap<Integer, ConfigItem> getRewards() {
		return rewards;
	}

	public HashMap<Integer, ConfigItem> getPrices() {
		return prices;
	}
	
}
