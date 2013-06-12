package com.tidesofwaronline.Exodus.Locks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldLockpick extends ItemStack {
	
	public GoldLockpick() {
		super(Material.GOLD_INGOT);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName("Gold Lockpick");
		this.setItemMeta(im);
	}

}
