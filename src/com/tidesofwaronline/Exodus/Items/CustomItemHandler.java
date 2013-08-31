package com.tidesofwaronline.Exodus.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemHandler {

	static HashMap<UUID, CustomItem> customItems = new HashMap<UUID, CustomItem>();
	static HashMap<String, CustomItem> definedItems = new HashMap<String, CustomItem>();

	public enum Tier {
		TRASH(ChatColor.DARK_GRAY), DECENT(ChatColor.GRAY), COMMON(
				ChatColor.WHITE), UNCOMMON(ChatColor.DARK_GREEN), RARE(
				ChatColor.DARK_BLUE), LEGENDARY(ChatColor.DARK_PURPLE), HEROIC(
				ChatColor.GOLD), ;

		ChatColor color;

		Tier(ChatColor color) {
			this.color = color;
		}

		public ChatColor getColor() {
			return color;
		}
	}

	public enum Type {
		SWORD, AXE, PICKAXE, SPADE, HOE, WAND, TALISMAN, BOW, ARROW;
	}

	public enum Prefix {
		BLOODY, HONED, BUSTED, BROKEN, PRESTINE, EPIC;
	}

	public static void register(CustomItem i) {
		customItems.put(i.getID(), i);
	}

	public static void unRegister(UUID i) {
		customItems.remove(i);
	}

	public static CustomItem lookup(UUID i) {
		return customItems.get(i);
	}

	public static boolean isCustomItem(ItemStack itemstack) {
		return (getCustomId(itemstack) != null);
	}

	public static UUID getCustomId(ItemStack itemstack) {
		ItemMeta m = itemstack.getItemMeta();

		if ((m == null) || (!(m.hasLore()))) {
			return null;
		}

		List<String> l = m.getLore();
		for (int i = 0; i < l.size(); ++i) {
			String s = l.get(i);

			if (s.contains("ID:")) {
				return UUID.fromString(s.replace("ID:", ""));
			}
		}

		return null;
	}
	
	public static void addDefinedItem(CustomItem item) {
		definedItems.put(item.getName().trim().replace(" ", "").toLowerCase(), item);
	}
	
	public static CustomItem getDefinedItem(String name) {
		return definedItems.get(name.trim().replace(" ", "").toLowerCase());
	}
	
	public static int getDefinedItemsSize() {
		return definedItems.size();
	}

	public static List<String> getDefinedItems() {
		List<String> list = new ArrayList<String>();
		
		for (CustomItem i : definedItems.values()) {
			list.add(i.getName());
		}
		
		return list;
	}
}
