package dk.qpqp.randomrewards;

import java.util.HashMap;

import org.bukkit.Material;

public class ItemList {
	
	// This file can be used, to store information about a item HashMap from the config.yml
	
	public HashMap<Integer, Material> itemsType;
	public HashMap<Integer, Integer> itemsAmount;
	public HashMap<Integer, Short> itemsData;
	
	public ItemList(HashMap<Integer, Material> itemsType, HashMap<Integer, Integer> itemsAmount, HashMap<Integer, Short> itemsData){
		this.itemsType = itemsType;
		this.itemsAmount = itemsAmount;
		this.itemsData = itemsData;
	}
}
