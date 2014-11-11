package dk.qpqp.randomrewards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Message {
	
	private static String pluginTag = ChatColor.GREEN+"[RandomRewards]";
	
	public static void log(String message, Plugin plugin){
		plugin.getLogger().info(message);
	}
	public static void playerMessage(String message, Player player, Plugin plugin){
		player.sendMessage(pluginTag+" "+message);
	}
}
