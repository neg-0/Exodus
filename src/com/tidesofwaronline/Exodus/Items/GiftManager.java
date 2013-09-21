package com.tidesofwaronline.Exodus.Items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.tidesofwaronline.Exodus.DataStructure;
import com.tidesofwaronline.Exodus.Exodus;

public class GiftManager {
	
	static HashMap<String, ArrayList<ItemStack>> gifts = new HashMap<String, ArrayList<ItemStack>>();
	static File file = new File(DataStructure.getGiftsFile());
	static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	static YamlConfiguration defaults = new YamlConfiguration();
	
	public GiftManager() {
		load();
	}
	
	static public ArrayList<ItemStack> getGifts(String player) {
		return gifts.get(player.toLowerCase());
	}
	
	static public void addGift(String player, ItemStack... itemStack) {
		if (gifts.get(player.toLowerCase()) == null) {
			gifts.put(player.toLowerCase(), new ArrayList<ItemStack>());
		}
		
		gifts.get(player.toLowerCase()).addAll(Lists.newArrayList(itemStack));
		if (Bukkit.getPlayer(player) != null && Bukkit.getPlayer(player).isOnline()) {
			Bukkit.getPlayer(player).sendMessage("You have some gifts available! Type '/gift claim' to receive them!");
		}
	}
	
	static public void clearGifts(Player player) {
		gifts.remove(player.getName().toLowerCase());
	}
	
	static public void claimGifts(Player player) {
		if (hasGifts(player)) {
			for (ItemStack i : GiftManager.getGifts(player.getName())) {
				if (i instanceof CustomItem) {
					player.sendMessage("Claiming " + i.getAmount() + " " + ((CustomItem) i).getName());
				} else {
					player.sendMessage("Claiming " + i.getAmount() + " " + i.getType().name());
				}
				HashMap<Integer, ItemStack> excess = player.getInventory().addItem(i);
				for (Map.Entry<Integer, ItemStack> me : excess.entrySet()) {
					player.getWorld().dropItem(player.getLocation(), me.getValue());
				}
			}
			player.sendMessage("Check your feet for any additional gifts that may not have fit in your inventory.");
			clearGifts(player);
		}
	}
	
	public static void save() {		
		file.delete();
		for (String s : gifts.keySet()) {
			config.set(s, getGifts(s));
		}
		try {
			config.save(file);
		} catch (IOException e) {
			Exodus.logger.severe("ERROR SAVING GIFTS LIST");
			e.printStackTrace();
		}
	}
	
	static void load() {
		for (String s : config.getKeys(false)) {
			for (Object o : config.getList(s)) {
				if (o instanceof CustomItem) {
					addGift(s, (CustomItem) o);
				} else if (o instanceof ItemStack){
					addGift(s, (ItemStack) o);
				}
			}
			
		}
	}

	public static boolean hasGifts(Player player) {
		if (gifts.containsKey(player.getName().toLowerCase())) {
			return !gifts.get(player.getName().toLowerCase()).isEmpty();
		} else {
			return false;
		}
	}
	
}
