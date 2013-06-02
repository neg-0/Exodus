package com.tidesofwaronline.Exodus.Chests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IronLockpick extends ItemStack {

	public IronLockpick() {
		super(Material.IRON_INGOT);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName("Iron Lockpick");
		this.setItemMeta(im);
	}
}
