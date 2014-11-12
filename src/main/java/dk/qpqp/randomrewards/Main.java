package dk.qpqp.randomrewards;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	ConfigSetup configSetup;
	
	public HashMap<Integer, Material> randomItems;
	public HashMap<Integer, Integer> randomItemsAmount;
	public HashMap<Integer, Short> randomItemsData;
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configSetup = new ConfigSetup(plugin);
        
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
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
}
