package com.tidesofwaronline.Exodus.Locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IronLock extends ItemStack {

	public IronLock() {
		super(Material.IRON_INGOT);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName("Iron Lock");
		this.setItemMeta(im);
	}
}
