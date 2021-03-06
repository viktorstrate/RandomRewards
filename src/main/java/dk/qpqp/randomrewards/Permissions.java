package dk.qpqp.randomrewards;

import org.bukkit.entity.Player;

public class Permissions {
	// A enum of all the avaliable permission nodes
	public static enum Permission{
		// All the admin commands
		ADMIN,
		// Can use the random reward block
		CANUSE,
		ADD_REWARDS,
		REMOVE_REWARDS,
		LIST_REWARDS
	}
	private static Main main;
	
	public static void setupMain(Main m){
		main = m;
	}
	
	public static boolean playerHasPerm(Player player, Permission permission){
		switch(permission){
		case ADMIN:
			if(player.hasPermission("randomrewards.admin")){
				return true;
			} else return false;
		case CANUSE:
			// If the node "usePermission" in the config.yml is set to false return true
			if(!main.getConfig().getBoolean("usePermission")){
				return true;
			} else {
				// Else check if player has the permission
				if(player.hasPermission("randomrewards.user.canuse")){
					return true;
				} else return false;
			}
		case ADD_REWARDS:
			if(player.hasPermission("randomrewards.admin.add")){
				return true;
			} else return false;
		case REMOVE_REWARDS:
			if(player.hasPermission("randomrewards.admin.remove")){
				return true;
			} else return false;
		case LIST_REWARDS:
			if(player.hasPermission("randomrewards.user.list")){
				return true;
			} else return false;
		default: return false;
		}
	}
}
