package dk.qpqp.randomrewards;

import org.bukkit.enchantments.Enchantment;

public class ConfigEnchantment {
	public Enchantment enchantment;
	public int level;
	
	public ConfigEnchantment(Enchantment enc, int level){
		enchantment = enc;
		this.level = level;
	}
}
