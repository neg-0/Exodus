package com.tidesofwaronline.Exodus.DungeonBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonToolInfo;

public class DBInventory {
	
	static Inventory inv = Bukkit.createInventory(null, 36);

	public DBInventory () {
		
		//Tools
		inv.setItem(0, new DBInventoryIcon(InfoTool.class));
		inv.setItem(1, new ItemStack(Material.WOOD_AXE));
		
		inv.setItem(2, new DBInventoryIcon(ClickTrigger.class));
		inv.setItem(3, new DBInventoryIcon(ProximityTrigger.class));
		inv.setItem(4, new DBInventoryIcon(BlockManipulator.class));
		inv.setItem(5, new DBInventoryIcon(EffectsPlayer.class));
		inv.setItem(6, new DBInventoryIcon(EntitySpawner.class));
		inv.setItem(7, new DBInventoryIcon(InventoryEditor.class));
		inv.setItem(8, new DBInventoryIcon(CommandExecutor.class));
		inv.setItem(11, new DBInventoryIcon(RedstoneSwitch.class));
		inv.setItem(12, new DBInventoryIcon(PhysicsBlock.class));
	}

	public static Inventory getInventory() {
		return inv;
	}
	
	public static ItemStack getItem(int slot) {
		return inv.getItem(slot);
	}
	
	public static boolean isHoldingInfoTool(Player p) {
		ItemStack i = p.getItemInHand();
		return (i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("Info Tool"));
	}
	
	public static class DBInventoryIcon extends ItemStack {

		public DBInventoryIcon(Class<? extends DungeonBlock> clazz) {
			super(Material.DIRT);
			DungeonBlockInfo dbi = clazz.getAnnotation(DungeonBlockInfo.class);
			if (dbi != null) {
				ItemMeta im = this.getItemMeta();
				im.setDisplayName(dbi.name());
				im.setLore(Lists.newArrayList(dbi.description()));
				this.setItemMeta(im);
				Material mat = Material.matchMaterial(dbi.material());
				if (mat != null) {
					this.setType(mat);
				}
			}
			
			DungeonToolInfo dti = clazz.getAnnotation(DungeonToolInfo.class);
			if (dti != null) {
				ItemMeta im = this.getItemMeta();
				im.setDisplayName(dti.name());
				im.setLore(Lists.newArrayList(dti.description()));
				this.setItemMeta(im);
				Material mat = Material.matchMaterial(dti.material());
				if (mat != null) {
					this.setType(mat);
				}
			}
		}
	}
}
