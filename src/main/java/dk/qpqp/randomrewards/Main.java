package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	File configFile;
	FileConfiguration config;
	
	public HashMap<Integer, Material> randomItems;
	public HashMap<Integer, Integer> randomItemsAmount;
	public HashMap<Integer, Integer> randomItemsData;
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configFile = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        
        // Creates the config if it doesn't exists
        try{
        	firstRun();
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        // Find the random items from the config
        randomItems = new HashMap<Integer, Material>();
        randomItemsAmount = new HashMap<Integer, Integer>();
        randomItemsData = new HashMap<Integer, Integer>();
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			
			if(str.startsWith("rewards.items.")){
				
				for(Material mat: Material.values()){
					
					if( str.endsWith(mat.name()) ){
						Message.log("Found item: "+mat.name()+" in config!", plugin);
						randomItems.put(randomItems.size(), mat);
						if(plugin.getConfig().get("rewards.items."+mat.name()+".data")!=null){
							Message.log("-  Found data to item", plugin);
							randomItemsData.put(randomItems.size()-1, plugin.getConfig().getInt("rewards.items."+mat.name()+".data") );
						} else {
							Message.log("-  didn't find data for item", plugin);
						}
						if(plugin.getConfig().get("rewards.items."+mat.name()+".amount")!=null){
							Message.log("-  Found amount of items", plugin);
							randomItemsAmount.put(randomItems.size()-1, plugin.getConfig().getInt("rewards.items."+mat.name()+".amount"));
						} else {
							Message.log("-  didn't find the amount of items", plugin);
						}
					}
					
				}
			}
		}
    }
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
	
	private void firstRun() throws Exception {
		if(!configFile.exists()){
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
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
}
