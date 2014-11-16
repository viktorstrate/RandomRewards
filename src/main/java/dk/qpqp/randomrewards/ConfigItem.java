package dk.qpqp.randomrewards;

import static org.bukkit.enchantments.Enchantment.*;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ConfigItem {
	
	// This file can be used, to store information about a item HashMap from the config.yml
	
	public Material itemsType;
	public int itemsAmount;
	public Short itemsData;
	public HashMap<Integer, Enchantment> enchantmentsType;
	public HashMap<Integer, Integer> enchantmentsLevel;
	
	public ConfigItem(Material itemsType, int itemsAmount, Short itemsData){
		this.itemsType = itemsType;
		this.itemsAmount = itemsAmount;
		this.itemsData = itemsData;
		enchantmentsType = new HashMap<Integer, Enchantment>();
		enchantmentsLevel = new HashMap<Integer, Integer>();
	}
	
	public ConfigItem(Material itemsType, int itemsAmount, Short itemsData, HashMap<Integer, Enchantment> enchantmentsType, HashMap<Integer, Integer> enchantmentsLevel){
		this(itemsType, itemsAmount, itemsData);
		this.enchantmentsType = enchantmentsType;
		this.enchantmentsLevel = enchantmentsLevel;
	}

	public static HashMap<String, Enchantment> getEnchantmentNames(){
		HashMap<String, Enchantment> list = new HashMap<String, Enchantment>();
		list.put("protection", PROTECTION_ENVIRONMENTAL);
		list.put("fire_protection", PROTECTION_FIRE);
		list.put("feather_falling", PROTECTION_FALL);
		list.put("blast_protection", PROTECTION_EXPLOSIONS);
		list.put("projectile_protection", PROTECTION_PROJECTILE);
		list.put("respiration", OXYGEN);
		list.put("aqua_affinity", WATER_WORKER);
		list.put("thorns", THORNS);
		//list.put("depth_strider", arg1);
		list.put("sharpness", DAMAGE_ALL);
		list.put("smite", DAMAGE_UNDEAD);
		list.put("bane_of_arthropods", DAMAGE_ARTHROPODS);
		list.put("knockback", KNOCKBACK);
		list.put("fire_aspect", FIRE_ASPECT);
		list.put("looting", LOOT_BONUS_MOBS);
		list.put("efficiency", DIG_SPEED);
		list.put("silk_touch", SILK_TOUCH);
		list.put("unbreaking", DURABILITY);
		list.put("fortune", LOOT_BONUS_BLOCKS);
		list.put("Power", ARROW_DAMAGE);
		list.put("punch", ARROW_KNOCKBACK);
		list.put("flame", ARROW_FIRE);
		list.put("infinity", ARROW_INFINITE);
		list.put("luck_of_the_sea", LUCK);
		list.put("lure", LURE);
		return list;
	}
}
