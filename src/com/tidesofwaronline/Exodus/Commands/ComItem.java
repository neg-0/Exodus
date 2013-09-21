package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;

public class ComItem extends Command {

	public ComItem(CommandPackage comPackage) {
		
		Player player = comPackage.getPlayer();
		String[] args = comPackage.getArgs();
		
		String itemname = Joiner.on("").join(args);
		CustomItem item = CustomItemHandler.getDefinedItem(itemname);

		if (item != null) {
			player.getInventory().addItem(item);
		} else {
			player.sendMessage("Item \"" + itemname + "\" not found.");
			player.sendMessage("Number of defined items: "
					+ CustomItemHandler.getDefinedItemsSize());
			for (int i = 0; i < CustomItemHandler.getDefinedItemsSize(); i++) {
				for (String s : CustomItemHandler.getDefinedItems()) {
					player.sendMessage(s);
				}
			}
		}
	}
}
