package dk.qpqp.randomrewards;

import org.bukkit.plugin.Plugin;

public class Message {
	public static void log(String message, Plugin plugin){
		plugin.getLogger().info(message);
	}
}
