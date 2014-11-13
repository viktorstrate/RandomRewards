package dk.qpqp.randomrewards;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Listener implements org.bukkit.event.Listener {
	
	// This file detects events like when a player right-clicks on a block.
	
	Main main;
	Plugin plugin;
	
	public Listener(Main main) {
		this.main = main;
		plugin = main.plugin;
	}
	
	@SuppressWarnings( "deprecation" )
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			ConfigSetup configSetup = main.configSetup;
			
			// Checks if the block is identical to the block "rewardBlock" in the config.yml
			Block block = event.getClickedBlock();
			if( block.getType()==configSetup.getRewardBlock().itemsType && block.getData()==configSetup.getRewardBlock().itemsData ){
				
				Player player = event.getPlayer();
				
				// Checks if the player has permission
				if(!Permissions.playerHasPerm(player, Permissions.Permission.CANUSE)){
					Message.playerMessage(ChatColor.RED+"You don't have permission.", player, plugin);
					Message.playerMessage(ChatColor.RED+"You need node "+ChatColor.DARK_GREEN+"randomrewards.user", player, plugin);
					return;
				}
				
				event.setCancelled(true);
				
				// Checks if the player has the price it costs
				boolean hasThePrice = true;
				for(int i = 0; i < configSetup.getPrices().size(); i++){
					// if the player doesn't contains at least the the current item in the loop with the amount specified in the config.yml
					if(!player.getInventory().containsAtLeast(new ItemStack(configSetup.getPrices().get(i).itemsType, 1, configSetup.getPrices().get(i).itemsData), configSetup.getPrices().get(i).itemsAmount)){
						hasThePrice = false;
					}
				}
				// If the player has the items he / she needs to get the reward
				if(hasThePrice){
					// Removes the items from the players inventory
					for(int i = 0; i < configSetup.getPrices().size(); i++){
						player.getInventory().removeItem(new ItemStack(configSetup.getPrices().get(i).itemsType, configSetup.getPrices().get(i).itemsAmount, configSetup.getPrices().get(i).itemsData));
					}
					
					HashMap<Integer, ConfigItem> rewards;
					rewards = main.configSetup.getRewards();
					// Creates a random id
					int randomId = (int) (Math.random()*rewards.size());
					// Gets the item through the random id
					Material randomMaterial = rewards.get(randomId).itemsType;
					ItemStack randomItem = new ItemStack(randomMaterial, rewards.get(randomId).itemsAmount, (short) rewards.get(randomId).itemsData);
					// Gives the random generated item to the player
					event.getPlayer().getInventory().addItem(randomItem);
					event.getPlayer().updateInventory();
					Message.playerMessage("Got a "+randomMaterial.name()+" and randomid: "+randomId+", and amount of items: "+rewards.size(), event.getPlayer(), plugin);
				} else{
					Message.playerMessage(ChatColor.RED+"You don't have enough to do that, it costs", player, plugin);
					for(int i = 0; i < configSetup.getPrices().size(); i++){
						Message.playerMessage(ChatColor.RED+""+configSetup.getPrices().get(i).itemsAmount+" "+configSetup.getPrices().get(i).itemsType.name(), player, plugin);
					}
				}
			}
		}
	}
}
