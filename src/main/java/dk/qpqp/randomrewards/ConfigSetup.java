package dk.qpqp.randomrewards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

public class ConfigSetup {

	// This file creates and loads the config.yml file

	private File configFile;
	private Plugin plugin;

	private HashMap<Integer, ConfigItem> rewards, prices;
	private ConfigItem rewardBlock;

	public ConfigSetup(Plugin plugin) {
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "config.yml");

		// Creates the config if it doesn't exists
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> rewardList = plugin.getConfig().getStringList("rewards");
		List<String> priceList = plugin.getConfig().getStringList("price");
		rewards = loadItems(rewardList);
		prices = loadItems(priceList);
		rewardBlock = new ConfigItem(Material.WOOL, 1, (short) 3);
		
		Message.log("REWARDS"+rewards.get(0).enchantmentsLevel.get(0), plugin);

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
			Message.log("DEBUG "+EnchantmentTypes.get(0).getName(), plugin);
			

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

	private Matcher regexChecker(String theRegx, String str2Check) {
		Pattern checkRegex = Pattern.compile(theRegx);
		Matcher regexMatcher = checkRegex.matcher(str2Check);

		return regexMatcher;
	}

	public ConfigItem loadBlock(String pathToBlock) {
		// Loads items from the config.yml
		Material item = Material.STONE;
		Short itemData = -1;
		Set<String> keys = plugin.getConfig().getKeys(true);
		for (String str : keys) {

			if (str.startsWith(pathToBlock + ".")) {

				for (Material mat : Material.values()) {

					if (str.endsWith(mat.name())) {
						item = mat;

						if (plugin.getConfig().get(
								pathToBlock + "." + mat.name() + ".data") != null) {
							itemData = (short) plugin.getConfig().getInt(
									pathToBlock + "." + mat.name() + ".data");
						}
					}

				}
				break;
			}
		}
		if (itemData >= 0) {
			Message.log("Block " + pathToBlock + " loaded", plugin);
			return new ConfigItem(item, 1, itemData);
		} else {
			Message.warning("Block " + pathToBlock
					+ " didn't load correctly, check the config", plugin);
			return null;
		}

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
