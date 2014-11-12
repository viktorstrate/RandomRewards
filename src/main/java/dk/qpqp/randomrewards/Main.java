package dk.qpqp.randomrewards;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	
	// This is the main file, this starts up the plugin and shuts it down. It also contains some core variables like "plugin"
	
	public Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	public ConfigSetup configSetup;
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configSetup = new ConfigSetup(plugin);
        
    }
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
}
