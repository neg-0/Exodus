package com.tidesofwaronline.Exodus.Util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Items.CustomItemHandler;

public class ItemUtil {
	public static List<Integer> weapons = Arrays
			.asList(267, 268, 272, 276, 283);
	public static List<Integer> tools = Arrays.asList(256, 257, 258, 269, 270,
			271, 273, 274, 275, 277, 278, 279, 284, 285, 286, 290, 291, 292,
			293, 294);
	
	public static ItemStack getItemFromString(String s) {
		if (CustomItemHandler.getDefinedItem(s) != null) {
			return CustomItemHandler.getDefinedItem(s);
		}

		//Items by ID
		try {
			int i = Integer.parseInt(s);
			return new ItemStack(i);
		} catch(NumberFormatException ex) { 

		}

		//Items by name
		if (Material.getMaterial(s) != null) {
			return new ItemStack(Material.getMaterial(s));
		}
		
		return null;
	}
}
