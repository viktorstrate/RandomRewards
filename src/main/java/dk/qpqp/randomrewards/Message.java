package dk.qpqp.randomrewards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Message {
	
	// This file is used to send messages to players, or log to the console.
	
	private static String pluginTag = ChatColor.GREEN+"[RandomRewards]";
	
	public static void log(String message, Plugin plugin){
		plugin.getLogger().info(message);
	}
	
	public static void warning(String message, Plugin plugin){
		plugin.getLogger().warning(message);
	}
	
	public static void playerMessage(String message, Player player, Plugin plugin){
		player.sendMessage(pluginTag+" "+ChatColor.WHITE+message);
	}
	
	public static void playerMessage(String message, Player player, Plugin plugin, boolean showTag){
		if(showTag){
			playerMessage(message, player, plugin);
		} else {
			player.sendMessage(message);
		}
	}
}
