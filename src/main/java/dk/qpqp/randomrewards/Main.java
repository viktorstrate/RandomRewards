package dk.qpqp.randomrewards;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public final class Main extends JavaPlugin {
	
	// This is the main file, this starts up the plugin and shuts it down. It also contains some core variables like "plugin"
	
	public Plugin plugin = this;
	PluginDescriptionFile pdf = plugin.getDescription();
	
	public ConfigSetup configSetup;
	
	public void onEnable() {
		
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configSetup = new ConfigSetup(plugin);
        
        Permissions.setupMain(this);

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        	Message.warning("COULDN'T LOAD METRICS", plugin);
        }
        
    }
	
	public void onDisable(){
		getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been disabled!");
	}
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return Commands.onCommand(sender, cmd, label, args, this);
	}
}
