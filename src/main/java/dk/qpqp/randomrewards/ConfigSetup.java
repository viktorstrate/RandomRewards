package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.UnhandledException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ConfigSetup {

	// This file creates and loads the config.yml file

	private File configFile;
	private Plugin plugin;

	private HashMap<Integer, ConfigItem> rewards, prices;
	private ConfigItem rewardBlock;
	
	List<String> rewardList = null;
	List<String> priceList = null;

	public ConfigSetup(Plugin plugin) {
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");

		// Creates the config if it doesn't exists
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateConfig();
		
		String rewardBlockString = plugin.getConfig().getString("rewardBlock");

		rewardBlock = loadBlock(rewardBlockString);
		

	}

	private void firstRun() throws Exception {
		if (!configFile.exists()) {
			Message.log("Config not found creating one...", plugin);
			configFile.getParentFile().mkdirs();
			copy(plugin.getResource("config.yml"), configFile);
		}
	}

	// Is used to, copy the config.yml from this plugin.jar to the folder
	// plugins/RandomRewards/
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<Integer, ConfigItem> loadItems(List<String> values) {

		HashMap<Integer, ConfigItem> items = new HashMap<Integer, ConfigItem>();
		Message.log("Items loaded from the config.yml", plugin);
		for (String value : values) {
			
			items.put(items.size(), loadItem(value));

		}

		return items;

	}

	@SuppressWarnings("deprecation")
	public ConfigItem loadItem(String value) {
		String regex = "(\\d)+";
		Matcher regexMatcher = regexChecker(regex, value);

		int id = 0;
		int amount = 1;
		Short datavalue = 0;

		HashMap<Integer, Enchantment> EnchantmentTypes = new HashMap<Integer, Enchantment>();
		HashMap<Integer, Integer> EncnantmentLevel = new HashMap<Integer, Integer>();

		int idEnd = 0;
		int amountEnd = 0;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				if (regexMatcher.start() == 0) {

					try {
						id = Integer.parseInt(regexMatcher.group().trim());
						Message.log("Item: " + Material.getMaterial(id), plugin);
					} catch (NumberFormatException e) {
						System.out.println("The id is not a number");
					}

					idEnd = regexMatcher.end();
				}

				if (regexMatcher.start() == idEnd + 1) {

					try {
						amount = Integer.parseInt(regexMatcher.group()
								.trim());
						Message.log("- Amount: "+amount, plugin);
					} catch (NumberFormatException e) {
						System.out.println("Amount not found");
					}

					amountEnd = regexMatcher.end();
				}

				if (regexMatcher.start() == amountEnd + 1) {

					try {
						datavalue = (short) Integer.parseInt(regexMatcher.group().trim());
					} catch (NumberFormatException e) {
						System.out.println("Datavalue not found");
					}
				}
			}
		}
		
		Set<String> encKey = ConfigItem.getEnchantmentNames().keySet();
		String enchantmentsStr = "";
		for(String str: encKey){
			enchantmentsStr+=str+"|";
		}
		// removes the last character from the string
		if (enchantmentsStr.length() > 0 && enchantmentsStr.charAt(enchantmentsStr.length()-1)=='|') {
			enchantmentsStr = enchantmentsStr.substring(0, enchantmentsStr.length()-1);
		}
		
		String regexEnc = "("+enchantmentsStr+")\\s\\d+";
		Matcher regexMatcherEnc = regexChecker(regexEnc, value);
		
		while (regexMatcherEnc.find()) {
			// Find the type
			String regexEncType = "[a-zA-Z]+";
			Matcher regexMatcherEncType = regexChecker(regexEncType,
					regexMatcherEnc.group().trim());

			regexMatcherEncType.find();
			EnchantmentTypes.put(EnchantmentTypes.size(), ConfigItem.getEnchantmentNames().get(regexMatcherEncType.group().trim()));
			

			// Find the level
			String regexEncLvl = "\\d+";
			Matcher regexMatcherEncLvl = regexChecker(regexEncLvl,
					regexMatcherEnc.group().trim());

			regexMatcherEncLvl.find();
			EncnantmentLevel.put(EnchantmentTypes.size() - 1,
					Integer.parseInt(regexMatcherEncLvl.group().trim()));
			Message.log("- - "+ConfigItem.getEnchantmentNames().get(regexMatcherEncType.group().trim()).getName()+" with "+regexMatcherEncLvl.group().trim()+" levels", plugin);
		}

		return new ConfigItem(Material.getMaterial(id), amount, datavalue, EnchantmentTypes, EncnantmentLevel);
	}
	
	@SuppressWarnings("deprecation")
	public ConfigItem loadBlock(String value) {
		String regex = "(\\d)+";
		Matcher regexMatcher = regexChecker(regex, value);

		int id = 0;
		Short datavalue = 0;

		int idEnd = 0;
		while (regexMatcher.find()) {
			if (regexMatcher.group().length() != 0) {
				if (regexMatcher.start() == 0) {

					try {
						id = Integer.parseInt(regexMatcher.group().trim());
						Message.log("Item: " + Material.getMaterial(id), plugin);
					} catch (NumberFormatException e) {
					}

					idEnd = regexMatcher.end();
				}

				if (regexMatcher.start() == idEnd + 1) {

					try {
						datavalue = (short) Integer.parseInt(regexMatcher.group().trim());
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		
		
		return new ConfigItem(Material.getMaterial(id), 1, datavalue, new HashMap<Integer, Enchantment>(), new HashMap<Integer, Integer>());
	}

	private Matcher regexChecker(String theRegx, String str2Check) {
		Pattern checkRegex = Pattern.compile(theRegx);
		Matcher regexMatcher = checkRegex.matcher(str2Check);

		return regexMatcher;
	}
	
	@SuppressWarnings("deprecation")
	public void addReward(ItemStack item){
		String enchantmentString = "";
		Set<Enchantment> key = item.getEnchantments().keySet();
		for(Enchantment enc: key){
			for(Entry<String, Enchantment> encType: ConfigItem.getEnchantmentNames().entrySet()){
				if(enc.equals(encType.getValue())){
					enchantmentString += encType.getKey()+" "+item.getEnchantmentLevel(enc)+" ";
				}
			}
			
		}
		String itemString = item.getTypeId()+" "+item.getAmount()+" "+item.getData().getData()+" "+enchantmentString;
		Message.log("Added reward item: "+itemString, plugin);
		
		List<String> list = plugin.getConfig().getStringList("rewards");
		list.add(itemString);
		plugin.getConfig().set("rewards", list);
		plugin.saveConfig();
		updateConfig();
	}
	
	public boolean removeReward(int id){
		try{
		List<String> list = plugin.getConfig().getStringList("rewards");
		
		list.remove(id-1);
		plugin.getConfig().set("rewards", list);
		plugin.saveConfig();
		updateConfig();
		} catch(UnhandledException e){
			return false;
		}
		return true;
	}
	
	private void updateConfig(){
		plugin.reloadConfig();
		rewardList = plugin.getConfig().getStringList("rewards");
		priceList = plugin.getConfig().getStringList("price");
		
		rewards = loadItems(rewardList);
		prices = loadItems(priceList);
	}

	public HashMap<Integer, ConfigItem> getRewards() {
		return rewards;
	}

	public HashMap<Integer, ConfigItem> getPrices() {
		return prices;
	}

	public ConfigItem getRewardBlock() {
		return rewardBlock;
	}
}
