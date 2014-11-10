package dk.qpqp.randomrewards;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
    }
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
}
