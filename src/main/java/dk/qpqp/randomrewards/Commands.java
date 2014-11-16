package dk.qpqp.randomrewards;

import static org.bukkit.ChatColor.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Commands {
	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main){
		if(cmd.getName().equalsIgnoreCase("rr") || cmd.getName().equalsIgnoreCase("randomrewards")){
			Player player = (Player) sender;
			if(args.length==0){
				showHelp(player, main.plugin);
				return true;
			} else {
				if(args[0].equalsIgnoreCase("setreward")){
					setReward(player, main.plugin, main);
				}
			}
		}
		return false;
	}
	
	private static void showHelp(Player player, Plugin plugin){
		Message.playerMessage(GREEN+"---- RandomRewards ----", player, plugin, false);
		Message.playerMessage(DARK_GREEN+"/rr setreward"+GREEN+" - adds the item you're holding in your hand", player, plugin, false);
	}
	
	private static void setReward(Player player, Plugin plugin, Main main){
		ItemStack item = player.getItemInHand();
		main.configSetup.addReward(item);
	}
}
