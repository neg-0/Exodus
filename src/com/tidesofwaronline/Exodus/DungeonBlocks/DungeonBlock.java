package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Commands.ComDBEBlockCommand.CommandInfo;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public abstract class DungeonBlock {
	
	static HashMap<Location, DungeonBlock> DBRegistry = new HashMap<Location, DungeonBlock>();
	List<DungeonBlock> linkedBlocks = new ArrayList<DungeonBlock>();
	
	Block block;
	Location loc;
	int ID;
	int IDcount = 1;

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
		
		output.add("/>-{�3" + this.getName() + "�f}---{ID: �3" + this.getID() + "�f}");
		output.add("| Linked to: �e" + Joiner.on(", ").join(this.getLinkedBlocks()));
		output.add("| Linked from: �e" + Joiner.on(", ").join(this.getLinkedFromBlocks()));
		
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
	
	public static DungeonBlock getDungeonBlock(String s) {
		for (DungeonBlock d : DBRegistry.values()) {
			if (d.toString().equalsIgnoreCase(s)) {
				return d;
			}
		}
		return null;
	}
	
	public static boolean isDungeonBlock(Location loc) {
		return DBRegistry.containsKey(loc);
	}

	public static void placeBlock(ExoPlayer exoPlayer, ItemStack block, Location location) {		
		try {
			Class<?> c = Class.forName(DungeonBlock.class.getPackage().getName() + "." + block.getItemMeta().getDisplayName().replaceAll(" ", ""));
			if (c.getAnnotation(DungeonBlockInfo.class) != null) {
				DungeonBlock db = (DungeonBlock) c.getConstructor(Location.class).newInstance(location);
				exoPlayer.getPlayer().sendMessage("Placed �3" + db + "�f.");
			} else {
				exoPlayer.getPlayer().sendMessage("This Dungeon Block is improperly configured: Missing Annotation.");
			}
		} catch (ClassNotFoundException e) {
			exoPlayer.getPlayer().sendMessage("This is not a valid Dungeon Block.");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*if (block.getItemMeta().getDisplayName().equals("Click Trigger")) {
			new ClickTrigger(location);
		} else if (block.getItemMeta().getDisplayName().equals("Entity Spawner")) {
			new EntitySpawner(location);
		}*/
	}

	public static void breakBlockEvent(ExoPlayer exodusPlayer, Block block) {
		removeDungeonBlock(getDungeonBlock(block).getLocation());
	}
	
	public static void clickBlockEvent(ExoPlayer exodusPlayer, Block clickedBlock, Action action) {
		
		//Left click = Block Info
		//Right click = Edit Block
		//Shift Left Click = Select Block
		//Shift Right Click = Link Block
		
		Player p = exodusPlayer.getPlayer();
		DungeonBlock cb = getDungeonBlock(clickedBlock);
		Location cbLoc = clickedBlock.getLocation();
		DungeonBlock sb = exodusPlayer.getSelectedBlock();
		Location sbLoc = null;
		if (sb != null) {
			sbLoc = exodusPlayer.getSelectedBlock().getLocation();
		}
		
		if (DBInventory.isHoldingInfoTool(exodusPlayer.getPlayer())) {
			if (DungeonBlock.isDungeonBlock(clickedBlock.getLocation())) {
				if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == false) {
					cb.getInfo(p);
					
				} else if (action == Action.RIGHT_CLICK_BLOCK && p.isSneaking() == false) {
					//Right Click
					if (exodusPlayer.getEditingBlock() != null && exodusPlayer.getEditingBlock().equals(cb)) {
						p.sendMessage("No longer editing " + cb);
						exodusPlayer.setEditingBlock(null);
					} else {
						p.sendMessage("Editing �3" + cb + "�f. Type �ehelp �fif you don't know what you're doing!");
						exodusPlayer.setEditingBlock(cb);
					}
					
				} else if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == true) {
					//Select Block
					if (sbLoc != null && sbLoc.equals(cbLoc)) {
						exodusPlayer.setSelectedBlock(null);
						p.sendMessage("Block Deselected");
					} else if (sbLoc == null || !sbLoc.equals(cbLoc)) {
						exodusPlayer.setSelectedBlock(cb);
						p.sendMessage("Selected block: �3" + cb);
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
						p.sendMessage("Link created! " + sb + " -> " + cb);
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
		return "�3" + this.getName() + " " + this.getID() + "�f";
	}
	
	@DungeonBlockCommand(example = "delete; delete Entity Spawner 4; delete all", syntax = "delete <all, Dungeon Block>", description = "Deletes the selected Dungeon Block, specified Dungeon Block(s), or all DungeonBlocks.")
	public String delete(CommandInfo ci) {
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
		
		ci.getExoPlayer().setEditingBlock(null);
		return "�3" + this.toString() + "�f has been deleted and removed. No longer editing �3" + this.toString() + "�f.";
	}
	
	@DungeonBlockCommand(example = "", syntax = "commands", description = "Returns a list of commands for the selected Dungeon Block.")
	public String commands() {
		
		Method[] methods = this.getClass().getMethods();
		
		List<String> commands = new ArrayList<String>();
		
		for (Method m : methods) {
			//Bukkit.broadcastMessage(m.getDeclaringClass().getName());
			if (m.isAnnotationPresent(DungeonBlockCommand.class)) {
				if (m.getDeclaringClass().getName().equals(this.getClass().getSuperclass().getName())) {
					commands.add("�7" + m.getName());
				} else {
					commands.add("�e" + m.getName());
					commands.add("�eexit");
				}
			}
		}
		return "Available commands for �3" + this.getName() + "�f: " + Joiner.on("�f, ").join(commands);
	}
	
	@DungeonBlockCommand(example = "help add; help remove", syntax = "help <command>", description = "Displays help and information for a specified command or the selected Dungeon Block.")
	public List<String> help(CommandInfo ci) {
		List<String> output = new ArrayList<String>();
		if (ci.getArguments().length == 0) {
			String s = "This is a";
			char letter = this.getName().toLowerCase().toCharArray()[0];
			if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') {
				s += "n";
			}
			s += " �3" + this.getName() + "�f. It " + Introspector.decapitalize(this.description(null)) +
			" To execute a �ecommand �f, type it into the chat box. For a list of �ecommands �f, type �ecommands �f.";
			output.add(s);
		} else {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(ci.getArguments()[0])) {
					DungeonBlockCommand dbc = m.getAnnotation(DungeonBlockCommand.class);
					String s = "The �e" + m.getName() + "�f command";
					if (!dbc.description().isEmpty()) {
						s += " " + Introspector.decapitalize(dbc.description());
					}
					
					output.add(s);
					
					if (!dbc.example().isEmpty()) {
						output.add("Example: " + dbc.example());
					} else {
						output.add("There are no available examples for this command.");
					}
					
					if (!dbc.syntax().isEmpty()) {
						output.add("Syntax: " + dbc.syntax());
					} else {
						output.add("There is no available syntax for this command.");
					}
				}
			}
		}
		return output;
	}
	
	@DungeonBlockCommand(example = "", syntax = "", description = "Gives a description of the specified command")
	public String description(CommandInfo ci) {
		if (ci != null) {
			if (ci.getArguments().length == 0) {
				DungeonBlockInfo dbi = this.getClass().getAnnotation(DungeonBlockInfo.class);
				if (dbi != null) {
					if (dbi.description().isEmpty()) {
						return "This Dungeon Block has no description.";
					} else {
						return "�3" + this.getName() + "�f: " + dbi.description();
					}
				}
			} else if (ci.getArguments().length > 0) {
				for (Method m : this.getClass().getMethods()) {
					if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(ci.getArguments()[0])) {
						String description = m.getAnnotation(DungeonBlockCommand.class).description();
						if (description.isEmpty()) {
							return "This command has no description.";
						} else {
							return "�e" + ci.getArguments()[0] + "�f: " + description;
						}
					}
				}
			}
		} else {
			DungeonBlockInfo dbi = this.getClass().getAnnotation(DungeonBlockInfo.class);
			if (dbi != null) {
				if (dbi.description().isEmpty()) {
					return "This Dungeon Block has no description.";
				} else {
					return dbi.description();
				}
			}
		}
		return "This command/block has no description";
	}
	
	@DungeonBlockCommand(example = "", syntax = "example <command>", description = "Gives an example for the specified command.")
	public String example(CommandInfo ci) {
		if (ci.getArguments().length > 0) {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(ci.getArguments()[0])) {
					String example = m.getAnnotation(DungeonBlockCommand.class).example();
					if (example.isEmpty()) {
						return "This command has no examples.";
					} else {
						return "Example for command " + ci.getArguments()[0] + ": " + example;
					}
				}
			}
		}
		return "You must specify a command to show examples.";
	}
	
	@DungeonBlockCommand(example = "", syntax = "syntax", description = "Displays the syntax for the specified command.")
	public String syntax(CommandInfo ci) {
		if (ci.getArguments().length > 0) {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(ci.getArguments()[0])) {
					String syntax = m.getAnnotation(DungeonBlockCommand.class).syntax();
					if (syntax.isEmpty()) {
						return "Syntax for command " + ci.getArguments()[0] + ": " + ci.getArguments()[0];
					} else {
						return "Syntax for command " + ci.getArguments()[0] + ": " + syntax;
					}
				}
			}
		}
		return "You must specify a command to show examples.";
	}
	
	@DungeonBlockCommand(description = "Creates a link to or from another Dungeon Block.", example = "link to Entity Spawner 1", syntax = "link <to/from> DungeonBlock, DungeonBlock, DungeonBlock...")
	public String link(CommandInfo ci) {
		if (ci.getArguments().length < 2) {
			return "Invalid Arguments. See syntax and/or example.";
		}
		
		List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
		String[] dungeonBlockStrings = Joiner.on(" ").join(Arrays.copyOfRange(ci.getArguments(), 1, ci.getArguments().length)).split(",");
		
		for (String s : dungeonBlockStrings) {
			DungeonBlock db = getDungeonBlock(s.trim());
			if (db != null) {
				dungeonBlocks.add(db);
			}
		}
		
		if (dungeonBlocks.isEmpty()) {
			return "No valid Dungeon Blocks found.";
		}
		
		if (ci.getArguments()[0].equalsIgnoreCase("to")) {
			for (DungeonBlock db : dungeonBlocks) {
				this.addLinkedBlock(db);
			}
			return "Linked this Dungeon Block to �3" + Joiner.on("�f, �3").join(dungeonBlocks);
		} else if (ci.getArguments()[0].equalsIgnoreCase("from")) {
			for (DungeonBlock db : dungeonBlocks) {
				db.addLinkedBlock(this);
			}
			return "Linked �3" + Joiner.on("�f, �3").join(dungeonBlocks) + "�f to this Dungeon Block.";
		}
		return "";
	}
	
	@DungeonBlockCommand(description = "Removes a link to or from another Dungeon Block.", example = "unlink Entity Spawner 1", syntax = "unlink DungeonBlock, DungeonBlock, DungeonBlock...")
	public String unlink(CommandInfo ci) {
		if (ci.getArguments().length < 1) {
			return "Invalid Arguments. See syntax and/or example.";
		}
		
		if (ci.getArguments()[0].equalsIgnoreCase("all")) {
			for (DungeonBlock db : this.getLinkedBlocks()) {
				this.removeLinkedBlock(db);
			}
			
			for (DungeonBlock db : this.getLinkedFromBlocks()) {
				db.removeLinkedBlock(this);
			}
		}
		
		List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
		String[] dungeonBlockStrings = Joiner.on(" ").join(Arrays.copyOfRange(ci.getArguments(), 1, ci.getArguments().length)).split(",");
		
		for (String s : dungeonBlockStrings) {
			DungeonBlock db = getDungeonBlock(s.trim());
			if (db != null) {
				dungeonBlocks.add(db);
			}
		}
		
		if (dungeonBlocks.isEmpty()) {
			return "No valid Dungeon Blocks found.";
		}
		
		for (DungeonBlock db : dungeonBlocks) {
			this.removeLinkedBlock(db);
			db.removeLinkedBlock(this);
		}
		return "Unlinked this Dungeon Block from �3" + Joiner.on("�f, �3").join(dungeonBlocks);

	}
}
