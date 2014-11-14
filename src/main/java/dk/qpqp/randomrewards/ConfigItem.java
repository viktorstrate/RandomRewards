package dk.qpqp.randomrewards;

import java.util.ArrayList;

import org.bukkit.Material;

public class ConfigItem {
	
	// This file can be used, to store information about a item HashMap from the config.yml
	
	public Material itemsType;
	public int itemsAmount;
	public Short itemsData;
	public ArrayList<ConfigEnchantment> enchantments;
	
	public ConfigItem(Material itemsType, int itemsAmount, Short itemsData){
		this.itemsType = itemsType;
		this.itemsAmount = itemsAmount;
		this.itemsData = itemsData;
		enchantments = new ArrayList<ConfigEnchantment>();
	}
	
	public ConfigItem(Material itemsType, int itemsAmount, Short itemsData, ArrayList<ConfigEnchantment> enchantments){
		this(itemsType, itemsAmount, itemsData);
		this.enchantments = enchantments;
	}
}
