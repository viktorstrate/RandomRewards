package dk.qpqp.randomrewards;

import org.bukkit.entity.Player;

public class Permissions {
	public static enum Permission{
		ADMIN,
		CANUSE
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
			if(main.getConfig().getBoolean("usePermission")){
				return true;
			} else {
				if(player.hasPermission("randomrewards.user")){
					return true;
				} else return false;
			}
		}
		
		return false;
	}
}
