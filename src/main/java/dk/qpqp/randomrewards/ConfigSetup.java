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
	private File configFile;
	private Plugin plugin;
	
	private HashMap<Integer, Material> randomItems;
	private HashMap<Integer, Integer> randomItemsAmount;
	private HashMap<Integer, Short> randomItemsData;
	
	public ConfigSetup(Plugin plugin){
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");
        
        loadRewards();
        
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
        randomItems = new HashMap<Integer, Material>();
        randomItemsAmount = new HashMap<Integer, Integer>();
        randomItemsData = new HashMap<Integer, Short>();
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
	}
	
	// Getters for the hashmaps
	public HashMap<Integer, Material> getRandomItems() {
		return randomItems;
	}

	public HashMap<Integer, Integer> getRandomItemsAmount() {
		return randomItemsAmount;
	}

	public HashMap<Integer, Short> getRandomItemsData() {
		return randomItemsData;
	}
}
