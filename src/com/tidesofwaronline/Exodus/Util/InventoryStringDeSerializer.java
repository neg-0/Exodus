package com.tidesofwaronline.Exodus.Util;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryStringDeSerializer {
	public static String InventoryToString(final Inventory invInventory) {
		String serialization = invInventory.getSize() + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			final ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = new String();

				final String isType = String.valueOf(is.getType().getId());
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0) {
					final String isDurability = String.valueOf(is
							.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1) {
					final String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}

				final Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (final Entry<Enchantment, Integer> ench : isEnch
							.entrySet()) {
						serializedItemStack += ":e@" + ench.getKey().getId()
								+ "@" + ench.getValue();
					}
				}

				serialization += i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}

	public static Inventory StringToInventory(final String invString) {
		final String[] serializedBlocks = invString.split(";");
		final String invInfo = serializedBlocks[0];
		final Inventory deserializedInventory = Bukkit.getServer()
				.createInventory(null, Integer.valueOf(invInfo));

		for (int i = 1; i < serializedBlocks.length; i++) {
			final String[] serializedBlock = serializedBlocks[i].split("#");
			final int stackPosition = Integer.valueOf(serializedBlock[0]);

			if (stackPosition >= deserializedInventory.getSize()) {
				continue;
			}

			ItemStack is = null;
			Boolean createdItemStack = false;

			final String[] serializedItemStack = serializedBlock[1].split(":");
			for (final String itemInfo : serializedItemStack) {
				final String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")) {
					is = new ItemStack(Material.getMaterial(Integer
							.valueOf(itemAttribute[1])));
					createdItemStack = true;
				} else if (itemAttribute[0].equals("d") && createdItemStack) {
					is.setDurability(Short.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("a") && createdItemStack) {
					is.setAmount(Integer.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("e") && createdItemStack) {
					is.addEnchantment(Enchantment.getById(Integer
							.valueOf(itemAttribute[1])), Integer
							.valueOf(itemAttribute[2]));
				}
			}
			deserializedInventory.setItem(stackPosition, is);
		}

		return deserializedInventory;
	}
}