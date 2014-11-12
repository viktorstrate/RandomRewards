package dk.qpqp.randomrewards;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	public Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	public ConfigSetup configSetup;
	
	public HashMap<Integer, Material> randomItems;
	public HashMap<Integer, Integer> randomItemsAmount;
	public HashMap<Integer, Short> randomItemsData;
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configSetup = new ConfigSetup(plugin);
        
        randomItems = configSetup.getRandomItems();
        randomItemsAmount = configSetup.getRandomItemsAmount();
        randomItemsData = configSetup.getRandomItemsData();
        
    }
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
}
