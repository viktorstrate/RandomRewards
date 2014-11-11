package dk.qpqp.randomrewards;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Listener implements org.bukkit.event.Listener {
	
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
				int randomId = (int) (Math.random()*main.randomItems.size());
				Material randomMaterial = main.randomItems.get(randomId);
				ItemStack randomItem = new ItemStack(randomMaterial, main.randomItemsAmount.get(randomId), (short) main.randomItemsData.get(randomId));
				event.getPlayer().getInventory().addItem(randomItem);
				event.getPlayer().updateInventory();
				Message.playerMessage("Got a "+randomMaterial.name()+" and randomid: "+randomId+", and amount of items: "+main.randomItems.size(), event.getPlayer(), plugin);
			}
		}
	}
}
