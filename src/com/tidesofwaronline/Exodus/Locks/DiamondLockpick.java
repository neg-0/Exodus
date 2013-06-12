package com.tidesofwaronline.Exodus.Locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DiamondLockpick extends ItemStack {

	public DiamondLockpick() {
		super(Material.DIAMOND);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName("Diamond Lockpick");
		this.setItemMeta(im);
	}
}
