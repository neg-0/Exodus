package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public abstract class DungeonBlock {
	
	static HashMap<Location, DungeonBlock> DBRegistry = new HashMap<Location, DungeonBlock>();
	List<DungeonBlock> linkedBlocks = new ArrayList<DungeonBlock>();
	
	Block block;
	
	public DungeonBlock() {
		
	}
	
	public DungeonBlock(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public Location getLocation() {
		return this.block.getLocation();
	}

	public static void registerDungeonBlock(Location loc, DungeonBlock db) {
		DBRegistry.put(loc, db);
	}
	
	public static void removeDungeonBlock(Location loc) {
		DBRegistry.remove(loc);
	}
	
	public static DungeonBlock getDungeonBlock(Location loc) {
		return DBRegistry.get(loc);
	}
	
	public static DungeonBlock getDungeonBlock(Block b) {
		return DBRegistry.get(b.getLocation());
	}
	
	public static boolean isDungeonBlock(Location loc) {
		return DBRegistry.containsKey(loc);
	}

	public static void placeBlock(ExoPlayer exoPlayer, ItemStack block, Location location) {
		exoPlayer.getPlayer().sendMessage("Placed a " + block.getItemMeta().getDisplayName() + ".");
	}

	public static void breakBlockEvent(ExoPlayer exodusPlayer, Block block) {

	}
	
	public static void clickBlockEvent(ExoPlayer exodusPlayer, Block clickedBlock, Action action) {
				
		if (DBInventory.isHoldingInfoTool(exodusPlayer.getPlayer())) {
			if (DungeonBlock.isDungeonBlock(clickedBlock.getLocation())) {
				if (action == Action.LEFT_CLICK_BLOCK) {
				
				}
			
				if (action == Action.RIGHT_CLICK_BLOCK) {
					if (exodusPlayer.hasBlockSelected) {
						getDungeonBlock(exodusPlayer.selectedBlock).addLinkedBlock(getDungeonBlock(clickedBlock));
						exodusPlayer.getPlayer().sendMessage("Link created!");
					} else {
						exodusPlayer.selectedBlock = clickedBlock;
						exodusPlayer.getPlayer().sendMessage("Dungeon Block selected. Right click another DB to link.");
					}
				}
			}
		}
		
		for (DungeonBlock d : DBRegistry.values()) {
			d.onClickBlock(exodusPlayer, clickedBlock, action);
		}
		
	}
	
	public abstract void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock, Action action);
	
	public static void onRedstoneEventEvent(BlockRedstoneEvent event) {
		for (DungeonBlock d : DBRegistry.values()) {
			d.onRedstoneEvent(event);
		}
	}
	
	public abstract void onRedstoneEvent(BlockRedstoneEvent event);
	
	public abstract void onTrigger();
	
	public void addLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.add(db);
	}
	
	public void removeLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.remove(db);
	}
	
	public List<DungeonBlock> getLinkedBlocks(DungeonBlock db) {
		return this.linkedBlocks;
	}
}
