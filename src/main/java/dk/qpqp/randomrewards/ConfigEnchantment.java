package dk.qpqp.randomrewards;

import org.bukkit.enchantments.Enchantment;

public class ConfigEnchantment {
	
	// This file can hold the config.yml's data for enchantments. The enchantment it self and level
	
	public Enchantment enchantment;
	public int level;
	
	public ConfigEnchantment(Enchantment enc, int level){
		enchantment = enc;
		this.level = level;
	}
}
