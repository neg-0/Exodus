package com.tidesofwaronline.Exodus.DungeonBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class DBInventory {
	
	static Inventory inv = Bukkit.createInventory(null, 36);

	public DBInventory () {
		
		//Tools
		inv.setItem(0, new DBInventoryIcon(Material.BLAZE_ROD, "Info Tool", "Displays info on a block.", "Left click for block info.", "Right click...", "Shift-Left Click to select a block.", "Shift-Right Click to link a block."));
		
		//Logic Blocks
		inv.setItem(2, new DBInventoryIcon(Material.GOLD_BLOCK, "Dungeon Settings", "§8Dungeon Block", "Displays general dungeon information."));
		inv.setItem(11, new DBInventoryIcon(Material.PUMPKIN, "Toggle Switch", "§8Dungeon Block"));
		inv.setItem(20, new DBInventoryIcon(Material.EMERALD_BLOCK, "Repeater", "§8Dungeon Block"));
		inv.setItem(29, new DBInventoryIcon(Material.BRICK, "Redstone Switch", "§8Dungeon Block"));
		
		//Trigger Blocks
		inv.setItem(3, new DBInventoryIcon(Material.BEDROCK, "Click Trigger", "§8Dungeon Block", "Click to simulate a pulse."));
		inv.setItem(12, new DBInventoryIcon(Material.SNOW_BLOCK, "Item Proximity", "§8Dungeon Block"));
		inv.setItem(21, new DBInventoryIcon(Material.LAPIS_BLOCK, "Mob Proximity", "§8Dungeon Block"));
		inv.setItem(30, new DBInventoryIcon(Material.REDSTONE_BLOCK, "Timer", "§8Dungeon Block"));
		
		//Action Blocks
		inv.setItem(6, new DBInventoryIcon(DungeonBlock.MOB_SPAWNER.getMaterial(), DungeonBlock.MOB_SPAWNER.getName()));
		inv.setItem(7, new DBInventoryIcon(Material.ENDER_STONE, "Entity Spawner", "§8Dungeon Block"));
		inv.setItem(8, new DBInventoryIcon(Material.QUARTZ_ORE, "Mob Tosser", "§8Dungeon Block"));
		inv.setItem(24, new DBInventoryIcon(Material.DIAMOND_BLOCK, "Give Item", "§8Dungeon Block"));
		inv.setItem(25, new DBInventoryIcon(Material.WOOL, "Take Item", "§8Dungeon Block"));
		inv.setItem(26, new DBInventoryIcon(Material.IRON_BLOCK, "Cave-In", "§8Dungeon Block"));
		inv.setItem(33, new DBInventoryIcon(Material.GLASS, "Lightning", "§8Dungeon Block"));
		inv.setItem(34, new DBInventoryIcon(Material.TNT, "Explosion", "§8Dungeon Block"));
		inv.setItem(35, new DBInventoryIcon(Material.IRON_ORE, "Physics", "§8Dungeon Block"));
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

		public DBInventoryIcon(Material mat, String name, String... tooltip) {
			super(mat);
			
			ItemMeta im = this.getItemMeta();
			im.setDisplayName(name);
			im.setLore(Lists.newArrayList(tooltip));
			this.setItemMeta(im);
		}
	}
}
