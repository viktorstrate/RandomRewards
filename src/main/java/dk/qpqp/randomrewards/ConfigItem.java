package dk.qpqp.randomrewards;

import org.bukkit.Material;

public class ConfigItem {
	
	// This file can be used, to store information about a item HashMap from the config.yml
	
	public Material itemsType;
	public int itemsAmount;
	public Short itemsData;
	
	public ConfigItem(Material itemsType, int itemsAmount, Short itemsData){
		this.itemsType = itemsType;
		this.itemsAmount = itemsAmount;
		this.itemsData = itemsData;
	}
}
