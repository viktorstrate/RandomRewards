package dk.qpqp.randomrewards;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.DARK_GREEN;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;

import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Commands {
	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
		if (cmd.getName().equalsIgnoreCase("rr") || cmd.getName().equalsIgnoreCase("randomrewards")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				showHelp(player, main.plugin);
				return true;
			} else {
				if (args[0].equalsIgnoreCase("reward")) {
					if (args.length == 1) {
						showRewardCmds(player, main.plugin);
						return true;
					}

					if (args[1].equalsIgnoreCase("add")) {
						setReward(player, main.plugin, main);
						Message.playerMessage(GREEN+"Item added", player, main.plugin);
						return true;
					}

					if (args[1].equalsIgnoreCase("list")) {
						showRewardList(player, main);
						return true;
					}
					
					if (args[1].equalsIgnoreCase("remove")) {
						if(args.length==2){
							Message.playerMessage(GREEN+"Usage "+DARK_GREEN+"/rr reward remove [ID]", player, main.plugin);
							Message.playerMessage(GRAY+"Type "+DARK_GRAY+"/rr reward list"+GRAY+" to find the id", player, main.plugin);
						}
						
						if(removeReward(Integer.parseInt(args[2]), main.plugin, main)){
							Message.playerMessage("Syccessfully removed item", player, main.plugin);
						} else {
							Message.playerMessage("Couldn't remove item", player, main.plugin);
						}
						
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void showHelp(Player player, Plugin plugin) {
		Message.playerMessage(GREEN + "---- RandomRewards ----", player, plugin, false);
		Message.playerMessage(DARK_GREEN + "/rr reward" + GREEN + " - reward commands", player, plugin, false);
	}

	private static void showRewardCmds(Player player, Plugin plugin) {
		Message.playerMessage(GREEN + "---- RandomRewards (reward) ----", player, plugin, false);
		Message.playerMessage(DARK_GREEN + "/rr reward add" + GREEN + " - adds the item you're holding in your hand", player, plugin, false);
		Message.playerMessage(DARK_GREEN + "/rr reward list" + GREEN + " - shows a list of avaliable rewards", player, plugin, false);
	}

	private static void showRewardList(Player player, Main main) {
		Message.playerMessage(GREEN + "---- List of rewards ----", player, main.plugin, false);
		Set<Entry<Integer, ConfigItem>> rewards = main.configSetup.getRewards().entrySet();
		
		int count = 0;
		
		// Loops through the items from the config
		for (Entry<Integer, ConfigItem> str : rewards) {
			count++;
			
			ConfigItem item = str.getValue();
			
			Message.playerMessage(GREEN+"----------- "+DARK_GREEN+"Item ID "+count+""+GREEN+" -----------", player, main.plugin, false);
			String message = GREEN+"- " + DARK_GREEN + item.itemsAmount + " " + item.itemsType.name() + GREEN + " with datavalue: " + DARK_GREEN + item.itemsData;
			
			// if item has enchantments add text to message
			if(item.enchantmentsType.size()!=0){
				message +=  GREEN + " with enchantments:";
			}
			
			Message.playerMessage(message, player, main.plugin, false);

			for (int i = 0; i < item.enchantmentsType.size(); i++) {
				Message.playerMessage(GREEN+"- - " + DARK_GREEN + item.enchantmentsType.get(i).getName() + GREEN + " level: " + DARK_GREEN + item.enchantmentsLevel.get(i), player,
						main.plugin, false);
			}
		}
	}

	private static void setReward(Player player, Plugin plugin, Main main) {
		ItemStack item = player.getItemInHand();
		main.configSetup.addReward(item);
	}
	
	private static boolean removeReward(int id, Plugin plugin, Main main) {
		return main.configSetup.removeReward(id);
	}
}
