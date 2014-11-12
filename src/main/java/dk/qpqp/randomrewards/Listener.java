package dk.qpqp.randomrewards;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
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
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			Block block = event.getClickedBlock();
			if(block.getType()==Material.WOOL&&block.getData()==7){
				HashMap<Integer, ConfigItem> rewards;
				rewards = main.rewards;
				
				int randomId = (int) (Math.random()*rewards.size());
				Material randomMaterial = rewards.get(randomId).itemsType;
				ItemStack randomItem = new ItemStack(randomMaterial, rewards.get(randomId).itemsAmount, (short) rewards.get(randomId).itemsData);
				event.getPlayer().getInventory().addItem(randomItem);
				event.getPlayer().updateInventory();
				Message.playerMessage("Got a "+randomMaterial.name()+" and randomid: "+randomId+", and amount of items: "+rewards.size(), event.getPlayer(), plugin);
			}
		}
	}
}
