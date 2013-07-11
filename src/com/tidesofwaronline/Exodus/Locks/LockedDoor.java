package com.tidesofwaronline.Exodus.Locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LockedDoor extends ItemStack {

	public LockedDoor() {
		super(Material.WOOD_DOOR);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName("Locked Door");
		this.setItemMeta(im);
	}
}
