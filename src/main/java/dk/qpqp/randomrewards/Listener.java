package dk.qpqp.randomrewards;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Listener implements org.bukkit.event.Listener {
	
	Plugin plugin;
	
	public Listener(Plugin pl) {
		plugin = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block block = event.getClickedBlock();
			if(block.getType()==Material.WOOL&&block.getData()==7){
				event.getPlayer().sendMessage("Rightclicked a special piston!");
				List<String> list = (List<String>) plugin.getConfig().getList("Rewards.Items");
			}
		}
	}
}
