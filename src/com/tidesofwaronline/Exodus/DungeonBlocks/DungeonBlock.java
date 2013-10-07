package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.beans.Introspector;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExoPlayer.ExoGameMode;
import com.tidesofwaronline.Exodus.Util.MessageUtil;
import com.tidesofwaronline.Exodus.Util.SerializableLocation;
import com.tidesofwaronline.Exodus.Worlds.ExoWorld;

public abstract class DungeonBlock implements ConfigurationSerializable {
	static HashMap<ExoWorld, HashMap<Location, DungeonBlock>> DBRegistry = new HashMap<ExoWorld, HashMap<Location, DungeonBlock>>();

	public static void clickBlockEvent(ExoPlayer exoPlayer, Block clickedBlock, Action action) {
		if (clickedBlock == null) {
			return;
		}
		//Left click = Block Info
		//Right click = Edit Block
		//Shift Left Click = Select Block
		//Shift Right Click = Link Block
		Player p = exoPlayer.getPlayer();
		Location cbLoc = clickedBlock.getLocation();
		DungeonBlock cb = getDungeonBlock(cbLoc);
		DungeonBlock sb = exoPlayer.getSelectedBlock();
		Location sbLoc = null;
		if (sb != null) {
			sbLoc = exoPlayer.getSelectedBlock().getLocation();
		}
		if (DBInventory.isHoldingInfoTool(exoPlayer.getPlayer())) {
			if (DungeonBlock.isDungeonBlock(cbLoc)) {
				if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == false) {
					cb.getInfo(cb, p);
				} else if (action == Action.RIGHT_CLICK_BLOCK && p.isSneaking() == false) {
					//Right Click
					if (exoPlayer.getEditingBlock() != null && exoPlayer.getEditingBlock().equals(cb)) {
						p.sendMessage("No longer editing " + cb.toString() + ".");
						exoPlayer.setEditingBlock(null);
					} else {
						p.sendMessage("Editing " + cb.toString() + ". Type §ehelp §fif you don't know what you're doing!");
						exoPlayer.setEditingBlock(cb);
					}
				} else if (action == Action.LEFT_CLICK_BLOCK && p.isSneaking() == true) {
					//Select Block
					if (sbLoc != null && sbLoc.equals(cbLoc)) {
						exoPlayer.setSelectedBlock(null);
						p.sendMessage("Block Deselected");
					} else if (sbLoc == null || !sbLoc.equals(cbLoc)) {
						exoPlayer.setSelectedBlock(cb);
						p.sendMessage("Selected block: " + cb.toString());
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
						p.sendMessage("Link created! " + sb.toString() + " -> " + cb.toString());
					}
				}
			}
		}
		if (isDungeonBlock(cbLoc)) {
			getDungeonBlock(cbLoc).onClickBlock(exoPlayer, cbLoc, action);
		}
	}

	public static HashMap<Location, DungeonBlock> getDBRegistry(ExoWorld exoWorld) {
		return DBRegistry.get(exoWorld);
	}

	public static HashMap<Location, DungeonBlock> getDBRegistry(World world) {
		return DBRegistry.get(ExoWorld.getExoWorld(world));
	}

	public static DungeonBlock getDungeonBlock(Location loc) {
		return getDBRegistry(loc.getWorld()).get(loc);
	}

	public static DungeonBlock getDungeonBlock(World world, String s) {
		for (DungeonBlock d : getDBRegistry(world).values()) {
			if (d.toString().equalsIgnoreCase(s.trim())) {
				return d;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends DungeonBlock> getDungeonBlockClass(String s) {
		try {
			Class<?> c = Class.forName(DungeonBlock.class.getPackage().getName() + "." + s.replaceAll(" ", ""));
			if (c.getAnnotation(DungeonBlockInfo.class) != null) {
				return (Class<? extends DungeonBlock>) c;
			} else {
				return null;
			}
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static Collection<DungeonBlock> getDungeonBlocks(ExoWorld exoWorld) {
		if (getDBRegistry(exoWorld) != null) {
			return getDBRegistry(exoWorld).values();
		} else {
			return null;
		}
	}

	public static Collection<DungeonBlock> getDungeonBlocks(World world) {
		if (getDBRegistry(world) != null) {
			return getDBRegistry(world).values();
		} else {
			return null;
		}
	}

	public static int getID(DungeonBlock d) {
		return d.getID();
	}

	public static List<DungeonBlock> getLinkedBlocks(DungeonBlock db) {
		return db.linkedBlocks;
	}

	public static Material getMaterial(DungeonBlock d) {
		return Material.getMaterial(d.getClass().getAnnotation(DungeonBlockInfo.class).material());
	}

	public static String getName(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).name();
	}

	public static boolean hasInput(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).hasInput();
	}

	public static boolean hasOutput(DungeonBlock d) {
		return d.getClass().getAnnotation(DungeonBlockInfo.class).hasOutput();
	}

	public static void initWorldRegistries(ExoWorld exoWorld) {
		if (getDBRegistry(exoWorld) == null) {
			DBRegistry.put(exoWorld, new HashMap<Location, DungeonBlock>());
		}
	}

	public static boolean isDungeonBlock(Location loc) {
		if (getDBRegistry(loc.getWorld()) != null) {
			return getDBRegistry(loc.getWorld()).containsKey(loc);
		} else {
			return false;
		}
	}

	public static void onRedstoneEventEvent(BlockRedstoneEvent event) {
		for (DungeonBlock d : getDBRegistry(event.getBlock().getWorld()).values()) {
			d.onRedstoneEvent(event);
		}
	}

	public static void placeBlock(ExoPlayer exoPlayer, ItemStack block, Location location) {
		try {
			Class<? extends DungeonBlock> c = getDungeonBlockClass(block.getItemMeta().getDisplayName());
			if (c != null) {
				DungeonBlock db = c.getConstructor(Location.class).newInstance(location);
				exoPlayer.getPlayer().sendMessage("Placed and editing §3" + db + "§f.");
				exoPlayer.setEditingBlock(db);
			} else {
				exoPlayer.getPlayer().sendMessage("This Dungeon Block is improperly configured: Missing Annotation.");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void registerConfigurationSerializable() {
		ConfigurationSerialization.registerClass(DungeonBlock.class);
	}

	public static void registerDungeonBlock(World world, DungeonBlock db) {
		if (getDBRegistry(world) != null) {
			getDBRegistry(world).put(db.getLocation(), db);
		} else {
			Exodus.logger.severe("getDBRegistry(world) returned null");
		}
	}

	public static void removeDungeonBlock(Location loc) {
		getDBRegistry(loc.getWorld()).remove(loc);
	}

	public List<DungeonBlock> linkedBlocks = new ArrayList<DungeonBlock>();
	public Location location;
	public int ID;
	public boolean enabled = true;
	private boolean isValid = true;

	public DungeonBlock() {}

	public DungeonBlock(Location loc) {
		this();
		this.location = loc;
		this.ID = assignNewID();
		registerDungeonBlock(loc.getWorld(), this);
		
		//heartBeat = new HeartBeat(this);
		//heartBeat.start();
	}

	@SuppressWarnings({ "unchecked" })
	public DungeonBlock(Map<String, Object> map) {
		for (Field f : (Field[]) ArrayUtils.addAll(this.getClass().getFields(), this.getClass().getDeclaredFields())) {
			try {
				if (!Modifier.isPrivate(f.getModifiers()) && map.containsKey(f.getName())) {
					if (f.getType().equals(Location.class)) {
						f.set(this, SerializableLocation.fromString(map.get(f.getName()).toString()).toLocation());
					} else if (f.getType().isEnum()) {
						f.set(this, Enum.valueOf(f.getType().asSubclass(Enum.class), map.get(f.getName()).toString()));
					} else {
						f.set(this, map.get(f.getName()));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (this.location != null) {
			registerDungeonBlock(this.location.getWorld(), this);
			//heartBeat = new HeartBeat(this);
			//heartBeat.start();
			
		} else {
			Exodus.logger.severe("Error initializing Dungeon Block; Location null or invalid.");
		}
		
		
	}
	
	public void addLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.add(db);
	}

	private int assignNewID() {
		int tempID = 1;
		while (!IDIsFree(tempID)) {
			tempID++;
		}
		return tempID;
	}

	@DungeonBlockCommand(example = "", syntax = "commands", description = "Returns a list of commands for the selected Dungeon Block.")
	public List<String> commands() {
		Method[] methods = this.getClass().getMethods();
		List<String> commands = new ArrayList<String>();
		commands.add("Commands for §3" + this.getName() + "§f: <§especific§f> <§7general§f>");
		for (Method m : methods) {
			//Bukkit.broadcastMessage(m.getDeclaringClass().getName());
			if (m.isAnnotationPresent(DungeonBlockCommand.class)) {
				if (m.getDeclaringClass().getName().equals(this.getClass().getSuperclass().getName())) {
					commands.add("§7" + m.getName() + "§f: " + m.getAnnotation(DungeonBlockCommand.class).description());
				} else {
					commands.add("§e" + m.getName() + "§f: " + m.getAnnotation(DungeonBlockCommand.class).description());
				}
			}
		}
		return commands;
	}

	public void delete() {
		for (DungeonBlock d : this.getLinkedFromBlocks()) {
			d.removeLinkedBlock(this);
		}
		this.linkedBlocks = null;
		this.setValid(false);
		removeDungeonBlock(this.getLocation());
		try {
			this.finalize();
		} catch (Throwable e) {}
	}
	
	@SuppressWarnings("deprecation")
	public void delete(Player p) {
		this.setValid(false);
		p.sendBlockChange(this.location, Material.AIR, (byte) 0);
		delete();
	}

	@DungeonBlockCommand(example = "delete; delete Entity Spawner 4; delete all", syntax = "delete <all, Dungeon Block>", description = "Deletes the selected Dungeon Block, specified Dungeon Block(s), or all DungeonBlocks.")
	public String delete(CommandPackage cp) {
		this.delete(cp.getPlayer());
		cp.getExoPlayer().setEditingBlock(null);
		return "§3" + this.toString() + "§f has been deleted and removed. No longer editing §3" + this.toString() + "§f.";
	}

	@DungeonBlockCommand(example = "", syntax = "", description = "Gives a description of the specified command.")
	public String description(CommandPackage cp) {
		if (cp != null) {
			if (cp.getArgs().length == 0) {
				DungeonBlockInfo dbi = this.getClass().getAnnotation(DungeonBlockInfo.class);
				if (dbi != null) {
					if (dbi.description().isEmpty()) {
						return "This Dungeon Block has no description.";
					} else {
						return "§3" + this.getName() + "§f: " + dbi.description();
					}
				}
			} else if (cp.getArgs().length > 0) {
				for (Method m : this.getClass().getMethods()) {
					if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(cp.getArgs()[0])) {
						String description = m.getAnnotation(DungeonBlockCommand.class).description();
						if (description.isEmpty()) {
							return "This command has no description.";
						} else {
							return "§e" + cp.getArgs()[0] + "§f: " + description;
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

	@DungeonBlockCommand(description = "Disables a Dungeon Block.", example = "", syntax = "")
	public String disable(CommandPackage cp) {
		this.enabled = false;
		onDisable(cp);
		return this + " disabled.";
	}
	
	public void onDisable(CommandPackage cp) {
		
	}

	@DungeonBlockCommand(description = "Enables a Dungeon Block.", example = "", syntax = "")
	public String enable(CommandPackage cp) {
		this.enabled = true;
		onEnable(cp);
		return this + " enabled.";
	}
	
	public void onEnable(CommandPackage cp) {
		
	}

	@DungeonBlockCommand(example = "", syntax = "example <command>", description = "Gives an example for the specified command.")
	public String example(CommandPackage cp) {
		if (cp.getArgs().length > 0) {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(cp.getArgs()[0])) {
					String example = m.getAnnotation(DungeonBlockCommand.class).example();
					if (example.isEmpty()) {
						return "This command has no examples.";
					} else {
						return "Example for command " + cp.getArgs()[0] + ": " + example;
					}
				}
			}
		}
		return "You must specify a command to show examples.";
	}

	@DungeonBlockCommand(description = "Exits the Dungeon Block Editor.", example = "", syntax = "")
	public String exit(CommandPackage cp) {
		String block = cp.getExoPlayer().getEditingBlock().toString();
		cp.getExoPlayer().setEditingBlock(null);
		return "No longer editing " + block;
	}

	public int getID() {
		return this.ID;
	}

	public void getInfo(DungeonBlock db, Player p) {
		List<String> output = new ArrayList<String>();
		String line1 = "/>-{§3" + this.getName() + "§f}---{ID: §3" + this.getID() + "§f}---{";
		if (this.isEnabled()) {
			line1 += "§2Enabled";
		} else {
			line1 += "§4Disabled";
		}
		line1 += "§f}\\";
		output.add(line1);
		output.add("| Linked to: §3" + Joiner.on("§f, §3").join(this.getLinkedBlocks()));
		output.add("| Linked from: §3" + Joiner.on("§f, §3").join(this.getLinkedFromBlocks()));
		
		for (Class<?> clazz : db.getClass().getDeclaredClasses()) {
			if (clazz.getSimpleName().equals("Event")) {
				output.add("| Events:");
				for (int i = 0; i < this.getEvents().size(); i++) {
					output.add("| " + (i + 1) + ". §e" + this.getEvents().get(i));
				}
			}
		}
		
		String bottom = "\\>";
		for(int i = 17; i < MessageUtil.getStringWidth(MessageUtil.removeColourCode(line1)); i+=6) {
			bottom += "-";
		}
		bottom += "/";
		output.add(bottom);
		
		for (String s : output) {
			p.sendMessage(s);
		}
	}
	
	public abstract List<String> getAdditionalInfo();
	
	public List<?> getEvents() {
		return null;
	}

	public List<DungeonBlock> getLinkedBlocks() {
		return this.linkedBlocks;
	}

	public List<DungeonBlock> getLinkedFromBlocks() {
		if (getDBRegistry(this.getWorld()) != null) {
			List<DungeonBlock> list = new ArrayList<DungeonBlock>();
			for (DungeonBlock d : getDBRegistry(this.getWorld()).values()) {
				if (d.getLinkedBlocks().contains(this)) {
					list.add(d);
				}
			}
			return list;
		} else {
			return null;
		}
	}

	public Location getLocation() {
		return this.location;
	}

	public Material getMaterial() {
		return Material.getMaterial(this.getClass().getAnnotation(DungeonBlockInfo.class).material());
	}

	public String getName() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).name();
	}

	public World getWorld() {
		return this.getLocation().getWorld();
	}

	public boolean hasInput() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).hasInput();
	}

	public boolean hasOutput() {
		return this.getClass().getAnnotation(DungeonBlockInfo.class).hasOutput();
	}

	@DungeonBlockCommand(example = "help add; help remove", syntax = "help <command>", description = "Displays help and information for a specified command or the selected Dungeon Block.")
	public List<String> help(CommandPackage cp) {
		List<String> output = new ArrayList<String>();
		if (cp.getArgs().length == 0) {
			String s = "This is a";
			char letter = this.getName().toLowerCase().toCharArray()[0];
			if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') {
				s += "n";
			}
			s += " §3" + this.getName() + "§f. It " + Introspector.decapitalize(this.description(null)) + " To execute a command, type it into the chat box. For a list of §ecommands§f, type §ecommands§f.";
			output.add(s);
		} else {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(cp.getArgs()[0])) {
					DungeonBlockCommand dbc = m.getAnnotation(DungeonBlockCommand.class);
					String s = "The §e" + m.getName() + "§f command";
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
		if (output.isEmpty()) {
			output.add("Command §e" + cp.getArgs()[0] + "§f not found.");
		}
		return output;
	}

	public boolean IDIsFree(int id) {
		boolean free = true;
		for (DungeonBlock d : getDungeonBlocks(this.getWorld())) {
			if (d.getID() == id) {
				free = false;
			}
		}
		return free;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	private boolean isLinkedTo(DungeonBlock dungeonBlock) {
		return linkedBlocks.contains(dungeonBlock);
	}

	@DungeonBlockCommand(description = "Creates a link to or from another Dungeon Block.", example = "link to Entity Spawner 1", syntax = "link <to/from> DungeonBlock, DungeonBlock, DungeonBlock...")
	public String link(CommandPackage cp) {
		if (cp.getArgs().length < 2) {
			return "Invalid Arguments. See syntax and/or example.";
		}
		List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
		String[] dungeonBlockStrings = Joiner.on(" ").join(Arrays.copyOfRange(cp.getArgs(), 1, cp.getArgs().length)).split(",");
		for (String s : dungeonBlockStrings) {
			DungeonBlock db = getDungeonBlock(this.getWorld(), s.trim());
			if (db != null) {
				dungeonBlocks.add(db);
			}
		}
		if (dungeonBlocks.isEmpty()) {
			return "No valid Dungeon Blocks found.";
		}
		if (cp.getArgs()[0].equalsIgnoreCase("to")) {
			for (DungeonBlock db : dungeonBlocks) {
				this.addLinkedBlock(db);
			}
			return "Linked this Dungeon Block to §3" + Joiner.on("§f, §3").join(dungeonBlocks);
		} else if (cp.getArgs()[0].equalsIgnoreCase("from")) {
			for (DungeonBlock db : dungeonBlocks) {
				db.addLinkedBlock(this);
			}
			return "Linked §3" + Joiner.on("§f, §3").join(dungeonBlocks) + "§f to this Dungeon Block.";
		}
		return "";
	}

	public void onClickBlock(ExoPlayer exodusPlayer, Location clickedBlock, Action action) {}

	public void onRedstoneEvent(BlockRedstoneEvent event) {}

	public void onTrigger(DungeonBlockEvent event) {}

	public void removeLinkedBlock(DungeonBlock db) {
		this.linkedBlocks.remove(db);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field f : (Field[]) ArrayUtils.addAll(this.getClass().getFields(), this.getClass().getDeclaredFields())) {
			try {
				if (!Modifier.isPrivate(f.getModifiers())) {
					if (f.getType().equals(Location.class)) {
						map.put(f.getName(), new SerializableLocation((Location) f.get(this)).toString());
					} else if (f.getType().isEnum()) {
						map.put(f.getName(), f.get(this).toString());
					} else {
						map.put(f.getName(), f.get(this));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@DungeonBlockCommand(example = "", syntax = "syntax", description = "Displays the syntax for the specified command.")
	public String syntax(CommandPackage cp) {
		if (cp.getArgs().length > 0) {
			for (Method m : this.getClass().getMethods()) {
				if (m.isAnnotationPresent(DungeonBlockCommand.class) && m.getName().equalsIgnoreCase(cp.getArgs()[0])) {
					String syntax = m.getAnnotation(DungeonBlockCommand.class).syntax();
					if (syntax.isEmpty()) {
						return "Syntax for command " + cp.getArgs()[0] + ": " + cp.getArgs()[0];
					} else {
						return "Syntax for command " + cp.getArgs()[0] + ": " + syntax;
					}
				}
			}
		}
		return "You must specify a command to show examples.";
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getID();
	}

	public String toFormattedString() {
		return "§3" + this.getName() + " " + this.getID() + "§f";
	}

	
	
	/**
	 * If this Dungeon Block is enabled, triggers all enabled Dungeon Blocks.
	 * @param event DungeonBlockEvent
	 */
	public void triggerLinkedBlocks(DungeonBlockEvent event) {
		if (this.isEnabled()) {
			for (DungeonBlock d : this.getLinkedBlocks()) {
				if (d.isEnabled()) {
					d.onTrigger(event);
				}
			}
		}
	}

	@DungeonBlockCommand(description = "Removes a link to or from another Dungeon Block.", example = "unlink Entity Spawner 1", syntax = "unlink DungeonBlock, DungeonBlock, DungeonBlock...")
	public String unlink(CommandPackage cp) {
		if (cp.getArgs().length < 1) {
			return "Invalid Arguments. See syntax and/or example.";
		}
		if (cp.getArgs()[0].equalsIgnoreCase("all")) {
			for (DungeonBlock db : this.getLinkedBlocks()) {
				this.removeLinkedBlock(db);
			}
			for (DungeonBlock db : this.getLinkedFromBlocks()) {
				db.removeLinkedBlock(this);
			}
		}
		List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
		String[] dungeonBlockStrings = Joiner.on(" ").join(Arrays.copyOfRange(cp.getArgs(), 1, cp.getArgs().length)).split(",");
		for (String s : dungeonBlockStrings) {
			DungeonBlock db = getDungeonBlock(this.getWorld(), s.trim());
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
		return "Unlinked this Dungeon Block from §3" + Joiner.on("§f, §3").join(dungeonBlocks);
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(value = ElementType.METHOD)
	public @interface DungeonBlockCommand {
		String description();

		String example();

		String syntax();
	}

	public class DungeonBlockEvent {
		Entity entity;
		DungeonBlock dungeonBlock;

		public DungeonBlockEvent(DungeonBlock dungeonBlock, Entity entity) {
			this.dungeonBlock = dungeonBlock;
			this.entity = entity;
		}

		public DungeonBlockEvent(DungeonBlock dungeonBlock) {
			this.dungeonBlock = dungeonBlock;
		}

		public DungeonBlock getDungeonBlock() {
			return dungeonBlock;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setDungeonBlock(DungeonBlock dungeonBlock) {
			this.dungeonBlock = dungeonBlock;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DungeonBlockInfo {
		public String description();

		public boolean hasInput();

		public boolean hasOutput();

		public String material();

		public String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DungeonToolInfo {
		String[] description();

		String material();

		String name();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface CommandAlias {
		String value();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface EventInfo {
		String[] arguments();
		String name();
	}

	public void refreshBlock(Chunk[] chunks, Player player) {
		if (ExoPlayer.getExodusPlayer(player).getExoGameMode() != ExoGameMode.DBEDITOR) {
			return;
		}
		for (Chunk c : chunks) {
			if (this.getLocation().getChunk().equals(c)) {
				Bukkit.getScheduler().runTaskLater(Exodus.getPlugin(), new UpdateBlock(player, this), 5L);
			}
		}
	}
	
	class UpdateBlock extends BukkitRunnable {
		
		Player player;
		DungeonBlock dungeonBlock;

		public UpdateBlock(Player player, DungeonBlock dungeonBlock) {
			this.dungeonBlock = dungeonBlock;
			this.player = player;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			player.sendBlockChange(dungeonBlock.getLocation(), dungeonBlock.getMaterial(), (byte) 0);
		}
		
	}
}
