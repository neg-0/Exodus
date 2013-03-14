package com.tidesofwaronline.Exodus.CustomItem;

import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemHandler {

	static HashMap<Integer, CustomItem> map = new HashMap<Integer, CustomItem>();

	public enum Tier {
		Trash(ChatColor.DARK_GRAY), Decent(ChatColor.GRAY), Common(
				ChatColor.WHITE), Uncommon(ChatColor.DARK_GREEN), Rare(
				ChatColor.DARK_BLUE), Legendary(ChatColor.DARK_PURPLE), Heroic(
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
		Sword, Axe, Pickaxe, Spade, Hoe, Wand, Talasmin, Arrow
	}

	public enum Prefix {
		Bloody, Honed, Busted, Broken, Prestine, Epic;
	}

	public static void register(CustomItem i) {
		map.put(i.getID(), i);
	}

	public static void unRegister(int i) {
		map.remove(i);
	}

	public static CustomItem lookup(int i) {
		return map.get(i);
	}

	public static boolean isCustomItem(ItemStack itemstack) {
		return (getCustomId(itemstack) != -1);
	}

	public static int getCustomId(ItemStack itemstack) {
		ItemMeta m = itemstack.getItemMeta();

		if ((m == null) || (!(m.hasLore()))) {
			return -1;
		}

		List<String> l = m.getLore();
		for (int i = 0; i < l.size(); ++i) {
			String s = l.get(i);

			if (s.contains("ID:")) {
				return Integer.valueOf(s.replace("ID:", "")).intValue();
			}
		}

		return -1;
	}
}
