package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.CustomItem.CustomItem;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler;

public class ComItem extends Command {

	public ComItem(Player player, String[] args) {
		String itemname = "";
		for (String s : args) {
			itemname += s + " ";
		}
		CustomItem item = CustomItemHandler.getDefinedItem(itemname);
		if (item != null) {
			player.getInventory().addItem(item);
		} else {
			player.sendMessage("Item \"" + itemname + "\" not found.");
		}
	}
}
