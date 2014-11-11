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
	public HashMap<Integer, Byte> randomItemsData;
	
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
        randomItemsData = new HashMap<Integer, Byte>();
        Set<String> keys = plugin.getConfig().getKeys(true);
		for(String str: keys){
			Message.log(str, plugin);
			
			if(str.contains("rewards.items.")){
				Message.log("YO", plugin);
				
				for(Material mat: Material.values()){
					
					if(str.matches("rewards.items."+mat.name())){
						Message.log("MEGA YO "+mat.name(), plugin);
						randomItems.put(randomItems.size(), mat);
					}
					
					for(String strData: keys){
						if(strData.matches("rewards.items."+mat.name()+".data")){
							Message.log("MEGA YO DATA "+strData, plugin);
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
