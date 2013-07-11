package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.DungeonBlocks.Actions.GiveItem;
import com.tidesofwaronline.Exodus.DungeonBlocks.Actions.MobSpawner;
import com.tidesofwaronline.Exodus.DungeonBlocks.Logic.DungeonSettings;
import com.tidesofwaronline.Exodus.DungeonBlocks.Triggers.ClickTrigger;
import com.tidesofwaronline.Exodus.DungeonBlocks.Triggers.ProximityTrigger;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public abstract class DungeonBlock {
	
	static HashMap<Location, DungeonBlock> DBRegistry = new HashMap<Location, DungeonBlock>();
	List<DungeonBlock> linkedBlocks = new ArrayList<DungeonBlock>();
	
	Block block;
	Location loc;
	int ID;
	int IDcount = 1;
	
	public static final DungeonBlock DUNGEON_SETTINGS = new DungeonSettings();
	//public static final DungeonBlock TOGGLE_SWITCH
	//public static final DungeonBlock REPEATER
	//public static final DungeonBlock REDSTONE_SWITCH = new RedstoneSwitch();
	//public static final DungeonBlock CLICK_TRIGGER
	public static final DungeonBlock PROXIMITY = new ProximityTrigger();
	//public static final DungeonBlock TIMER
	public static final DungeonBlock MOB_SPAWNER = new MobSpawner();
	//public static final DungeonBlock ENTITY_SPAWNER
	//public static final DungeonBlock MOB_TOSSER
	public static final DungeonBlock GIVE_ITEM = new GiveItem();
	//public static final DungeonBlock TAKE_ITEM
	//public static final DungeonBlock CAVEIN
	//public static final DungeonBlock LIGHTNING
	//public static final DungeonBlock EXPLOSION
	//public static final DungeonBlock PHYSICS
	
	public void onTrigger() {
	}
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock, Action action) {
	}
	public void onRedstoneEvent(BlockRedstoneEvent event) {
	}
	
	public DungeonBlock() {
		
	}
	
	public DungeonBlock(Location loc) {
		this.loc = loc;
		this.block = loc.getBlock();
		this.ID = assignNewID();
		registerDungeonBlock(this);
	}
	
	private int assignNewID() {
		int tempID = IDcount;
		do {
			tempID = IDcount;
			for (DungeonBlock d : DBRegistry.values()) {
				if (d.getID() == tempID) {
					IDcount++;
				}
			}
		} while (tempID != IDcount);
		
		return tempID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public static int getID(DungeonBlock d) {
		return d.getID();
	}
	
	public void getInfo(Player p) {
		List<String> output = new ArrayList<String>();
		
		String s1 = "/>-{" + this.getName() + " Info}---{ID: " + this.getID() + "}";
		String s2 = "| Linked to: ";
		for (DungeonBlock db : this.getLinkedBlocks()) {
			s2 += db.toString() + ", ";
		}
			
		String s3 = "| Linked from: ";
		for (DungeonBlock db : this.getLinkedFromBlocks()) {
			s3 += db.toString() + ", ";
		}
		
		output.add(s1);
		output.add(s2);
		output.add(s3);
		
		for (String s : output) {
			p.sendMessage(s);
		}
		
		
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public Location getLocation() {
		return this.block.getLocation();
	}

	public static void registerDungeonBlock(DungeonBlock db) {
		DBRegistry.put(db.getLocation(), db);
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
		
		if (block.getItemMeta().getDisplayName().equals("Click Trigger")) {
			new ClickTrigger(location);
		} else if (block.getItemMeta().getDisplayName().equals("Mob Spawner")) {
			new MobSpawner(location);
		}
	}

	public static void breakBlockEvent(ExoPlayer exodusPlayer, Block block) {

	}
	
	public static void clickBlockEvent(ExoPlayer exodusPlayer, Block clickedBlock, Action action) {
		
		//Left click = Block Info
		//Right click = Edit Block
		//Shift Left Click = Select Block
		//Shift Right Click = Link Block
		
		Player p = exodusPlayer.getPlayer();
		DungeonBlock cb = getDungeonBlock(clickedBlock);
		Location cbLoc = clickedBlock.getLocation();
		DungeonBlock sb = exodusPlayer.selectedBlock;
		Location sbLoc = null;
		if (sb != null) {
			sbLoc = exodusPlayer.selectedBlock.getLocation();
		}
		
		if (DBInventory.isHoldingInfoTool(exodusPlayer.getPlayer())) {
			if (DungeonBlock.isDungeonBlock(clickedBlock.getLocation())) {
				if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == false) {
					cb.getInfo(p);
					
				} else if (action == Action.RIGHT_CLICK_BLOCK && p.isSneaking() == false) {
					//Right Click
					p.sendMessage("Editing " + cb.toString());
					exodusPlayer.editingBlock = cb;
					
				} else if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == true) {
					//Select Block
					if (sbLoc != null && sbLoc.equals(cbLoc)) {
						exodusPlayer.selectedBlock = null;
						p.sendMessage("Block Deselected");
					} else if (sbLoc == null || !sbLoc.equals(cbLoc)) {
						exodusPlayer.selectedBlock = cb;
						p.sendMessage("Selected block: " + cb.getName());
					}
					
				} else if (action == Action.RIGHT_CLICK_BLOCK && p.isSneaking() == true) {
					//Link Block
					if (sbLoc == null) {
						p.sendMessage("You must first select a block!");
					} else if (sbLoc.equals(cbLoc)) {
						p.sendMessage("You cannot link a block to itself.");
					} else if (sb.isLinkedTo(cb)) {
						sb.removeLinkedBlock(cb);
						p.sendMessage("Link broken!");
					} else {
						sb.addLinkedBlock(cb);
						p.sendMessage("Link created! " + sb.getName() + " -> " + cb.getName());
					}
				}
			}
		}
		if (isDungeonBlock(clickedBlock.getLocation())) {
			getDungeonBlock(clickedBlock).onClickBlock(exodusPlayer, clickedBlock, action);	
		}
	}
	
	private boolean isLinkedTo(DungeonBlock dungeonBlock) {
		return linkedBlocks.contains(dungeonBlock);
	}

	
	public static void onRedstoneEventEvent(BlockRedstoneEvent event) {
		for (DungeonBlock d : DBRegistry.values()) {
			d.onRedstoneEvent(event);
		}
	}
	
	public void triggerLinkedBlocks() {
		for (DungeonBlock d : this.getLinkedBlocks()) {
			d.onTrigger();
		}
	}
	
	public void addLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.add(db);
	}
	
	public void removeLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.remove(db);
	}
	
	public List<DungeonBlock> getLinkedBlocks() {
		return this.linkedBlocks;
	}
	
	public static List<DungeonBlock> getLinkedBlocks(DungeonBlock db) {
		return db.linkedBlocks;
	}
	
	public List<DungeonBlock> getLinkedFromBlocks() {
		List<DungeonBlock> list = new ArrayList<DungeonBlock>();
		for (DungeonBlock d : DBRegistry.values()) {
			if (d.getLinkedBlocks().contains(this)) {
				list.add(d);
			}
		}
		return list;
	}
	
	public String getName() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).name();
	}
	
	public static String getName(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).name();
	}
	
	public boolean hasInput() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).hasInput();
	}
	
	public static boolean hasInput(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).hasInput();
	}

	public boolean hasOutput() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).hasOutput();
	}
	
	public static boolean hasOutput(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).hasOutput();
	}
	
	public Material getMaterial() {
		return Material.getMaterial(this.getClass().getAnnotation(DungeonBlockInfo.class).material());
	}
	
	public static Material getMaterial(DungeonBlock d) {
		return Material.getMaterial(d.getClass().getAnnotation(DungeonBlockInfo.class).material());
	}
	
	@Override
	public String toString() {
		return this.getName() + " " + this.getID();
	}
	public void delete() {
		this.getBlock().setTypeId(0);
		for (DungeonBlock d : this.getLinkedFromBlocks()) {
			d.removeLinkedBlock(this);
		}
		this.linkedBlocks = null;
		removeDungeonBlock(this.getLocation());
		try {
			this.finalize();
		} catch (Throwable e) {

		}
	}
}
