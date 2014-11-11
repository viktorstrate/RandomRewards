package dk.qpqp.randomrewards;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class Listener implements org.bukkit.event.Listener {
	
	Main main;
	Plugin plugin;
	
	public Listener(Main main) {
		this.main = main;
		plugin = main.plugin;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			Block block = event.getClickedBlock();
			if(block.getType()==Material.WOOL&&block.getData()==7){
				Material randomMaterial = main.randomItems.get(Math.random()*main.randomItems.size());
			}
		}
	}
}
