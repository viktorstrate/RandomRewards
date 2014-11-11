package dk.qpqp.randomrewards;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Listener implements org.bukkit.event.Listener {
	
	Plugin plugin;
	
	public Listener(Plugin pl) {
		plugin = pl;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block block = event.getClickedBlock();
			if(block.getType()==Material.WOOL&&block.getData()==7){
				List<Integer> list = (List<Integer>) plugin.getConfig().getList("Rewards.Items");
				int randomItem = (int)(Math.random()*list.size());
				ItemStack item = new ItemStack(list.get(randomItem), 1);
				event.getPlayer().getInventory().addItem(item);
				event.getPlayer().updateInventory();
				event.getPlayer().sendMessage("You got a "+item.getType().name());
			}
		}
	}
}
