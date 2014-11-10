package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
	
	public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener(plugin), this);
        getLogger().info(pdf.getName()+" v. "+pdf.getVersion()+" has been enabled!");
        
        configFile = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        
        try{
        	firstRun();
        } catch (Exception e){
        	e.printStackTrace();
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
