package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;

@DungeonBlockInfo(description = "Changes the state of blocks in the world.", hasInput = true, hasOutput = false, material = "BRICK", name = "Block Manipulator")
public class BlockManipulator extends DungeonBlock {
	
	private List<Event> events = new ArrayList<Event>();

	public BlockManipulator() {
		super();
	}

	public BlockManipulator(Location loc) {
		super(loc);
	}

	public BlockManipulator(Map<String, Object> map) {
		super(map);
		
		@SuppressWarnings("unchecked")
		List<String> eventsList = (List<String>) map.get("Events");
		
		for (String s : eventsList) {
			addEvent(parseEvent(s));
		}
	}
	
	@DungeonBlockCommand(description = "Appends an Event to the end of the list.", example = "add /say Hello; add wait 500", syntax = "add <Event>")
	public String add(CommandPackage cp) {
		
			try {
				Class<?> clazz = Class.forName(Event.class.getName() + "$" + WordUtils.capitalize(cp.getArgs()[0]));
				Constructor<?> con = Class.forName(clazz.getName()).getConstructors()[0];
				CommandPackage com = new CommandPackage(cp.getPlugin(), cp.getPlayer(), cp.getExoPlayer(), Arrays.copyOfRange(cp.getArgs(), 1, cp.getArgs().length));
				return "Added " + addEvent((Event) con.newInstance(new Event(), com));
			} catch (IllegalArgumentException e) {
				cp.getPlayer().sendMessage(e.getMessage());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
				cp.getPlayer().sendMessage(e.getCause().getMessage());
			} catch (ClassNotFoundException e) {
				return "That's not a valid command.";
			} catch (Exception e) {
				
			}
			return "Something has happened :( Tell MastaC";
	}
	
	public Event addEvent(Event e) {
		this.events.add(e);
		return e;
	}
	
	@DungeonBlockCommand(description = "Clears the Event list.", example = "", syntax = "")
	public String clear(CommandPackage cp) {
		this.clearEvents();
		return "Events cleared.";
	}

	public void clearEvents() {
		events.clear();
	}
	
	@DungeonBlockCommand(description = "Returns a list of available Events.", example = "", syntax = "")
	public String events(CommandPackage cp) {
		List<String> list = new ArrayList<String>();
		for (Class<?> e : Event.class.getDeclaredClasses()) {
			list.add(e.getSimpleName());
		}
		return Joiner.on(", ").join(list);
	}

	public List<Event> getEvents() {
		return events;
	}

	@DungeonBlockCommand(description = "Inserts a command at the specified location.", example = "insert 4 say Hello", syntax = "insert <#> <Command>")
	public String insert(CommandPackage cp) {
		if (cp.getArgs().length < 2) {
			return "Invalid number of Arguments.";
		}
		return null;
	}

	public Event insertEvent(Event e, int i) {
		this.events.add(i, e);
		return e;
	}

	@DungeonBlockCommand(description = "Returns the list of executed commands.", example = "", syntax = "")
	public List<String> list(CommandPackage cp) {
		List<String> toReturn = new ArrayList<String>();
		for (int i = 0; i < this.getEvents().size(); i++) {
			toReturn.add((i + 1) + ". §e" + this.getEvents().get(i));
		}
		if (toReturn.size() == 0) {
			return Lists.newArrayList("Events list empty.");
		} else {
			return toReturn;
		}
	}
	
	@Override
	public void onTrigger(DungeonBlockEvent event) {
		for (Event e : getEvents()) {
			e.onTrigger(event);
		}
	}

	protected Event parseEvent(String s) {
		String command = s.split(" ")[0];
		String[] arguments = Arrays.copyOfRange(s.split(" "), 1, s.split(" ").length);
		try {
			Class<?> clazz = Class.forName(Event.class.getName() + "$" + WordUtils.capitalize(command));
			Constructor<?> con = Class.forName(clazz.getName()).getConstructors()[0];
			Event event = new Event();
			CommandPackage com = new CommandPackage(null, null, null, arguments);
			return (Event) con.newInstance(new Object[] {event, com});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			return null;
		}
		return null;
	}
	
	@DungeonBlockCommand(description = "Removes the specified indices.", example = "remove 5 2 1", syntax = "remove <Index, Index...>")
	public String remove(CommandPackage cp) {
		if (cp.getArgs().length == 0) {
			return "Invalid number of Arguments.";
		}
		List<Integer> toRemove = new ArrayList<Integer>();
		for (String s : cp.getArgs()) {
			try {
				int i = Integer.valueOf(s);
				events.remove(i);
				toRemove.add(i);
			} catch (NumberFormatException e) {}
		}
		if (toRemove.size() == 0) {
			return "No indexes were found. Nothing removed.";
		}
		String toReturn = "Removed Command";
		if (toRemove.size() > 1) {
			toReturn += "s";
		}
		toReturn += " " + Joiner.on(", ").join(toRemove);
		toReturn += ".";
		return toReturn;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.putAll(super.serialize());

		List<String> eventsList = new ArrayList<String>();
		for (Event e : getEvents()) {
			eventsList.add(e.toString());
		}

		map.put("Events", eventsList);

		return map;
	}

	public class Event {
		
		Selection selection;

		public Event() {}

		public Event(CommandPackage cp) {
			
		}

		public void onTrigger(DungeonBlockEvent event) {
		}

		@Override
		public String toString() {
			return "";
		}
		
		@EventInfo(arguments = { "selection" }, name = "Set")
		public class Set extends Event {
			
			Vector min;
			Vector max;
			BlockType mat;
			byte data = -1;
			
			public Set(CommandPackage cp) {
				super(cp);
				
				if (cp.getArgs()[0].contains(":")) {
					try {
						mat = BlockType.lookup(cp.getArgs()[0].split(":")[0]);
						data = Byte.parseByte(cp.getArgs()[0].split(":")[1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Invalid Material Data. Must be integer.");
					} 

				} else {
					mat = BlockType.lookup(cp.getArgs()[0]);
				}

				if (cp.getPlayer() != null) {
					selection = Exodus.getWorldEditPlugin().getSelection(cp.getPlayer());
				} else {
					for (int i = 0; i < cp.getArgs().length; i++) {
						if (cp.getArgs()[i].equalsIgnoreCase("from")) {
							try {
								min = new Vector(Double.parseDouble(cp.getArgs()[i + 1]), Double.parseDouble(cp.getArgs()[i + 2]), Double.parseDouble(cp.getArgs()[i + 3]));
							} catch (NumberFormatException e) {
								throw new IllegalArgumentException("Invalid 'From' Location.");
							}
						}
						if (cp.getArgs()[i].equalsIgnoreCase("to")) {
							try {
								max = new Vector(Double.parseDouble(cp.getArgs()[i + 1]), Double.parseDouble(cp.getArgs()[i + 2]), Double.parseDouble(cp.getArgs()[i + 3]));
							} catch (NumberFormatException e) {
								throw new IllegalArgumentException("Invalid 'To' Location.");
							}
						}
					}
				}

				if (selection instanceof CuboidSelection) {
					min = selection.getNativeMinimumPoint();
                    max = selection.getNativeMaximumPoint();
				} else {
					throw new IllegalArgumentException("You must first select a region");
				}
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				if (min != null) {
					for(int x = min.getBlockX();x <= max.getBlockX(); x=x+1){
						for(int y = min.getBlockY();y <= max.getBlockY(); y=y+1){
							for(int z = min.getBlockZ();z <= max.getBlockZ(); z=z+1){
								Location tmpblock = new Location(selection.getWorld(), x, y, z);
								tmpblock.getBlock().setTypeId(mat.getID());
								if (data != -1) {
									tmpblock.getBlock().setData(data);
								}
							}
						}
					}
				}
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder(); 
				sb.append(this.getClass().getSimpleName());
				sb.append(" ");
				sb.append(mat.getID());
				if (data != -1) {
					sb.append(":");
					sb.append(data);
				}
				sb.append(" from ");
				sb.append(min.getX());
				sb.append(" ");
				sb.append(min.getY());
				sb.append(" ");
				sb.append(min.getZ());
				sb.append(" to ");
				sb.append(max.getX());
				sb.append(" ");
				sb.append(max.getY());
				sb.append(" ");
				sb.append(max.getZ());
				return sb.toString();
			}
		}
		
		@EventInfo(arguments = { "selection" }, name = "Replace")
		public class Replace extends Event {
			
			Vector min;
			Vector max;
			BlockType mat;
			byte data = -1;
			BlockType replaceMat;
			byte replaceData = -1;
			
			public Replace(CommandPackage cp) {
				super(cp);
				
				if (cp.getArgs()[0].contains(":")) {
					try {
						replaceMat = BlockType.lookup(cp.getArgs()[0].split(":")[0]);
						replaceData = Byte.parseByte(cp.getArgs()[0].split(":")[1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Invalid Material Data. Must be integer.");
					} 

				} else {
					replaceMat = BlockType.lookup(cp.getArgs()[0]);
				}
				
				if (cp.getArgs()[1].contains(":")) {
					try {
						mat = BlockType.lookup(cp.getArgs()[1].split(":")[0]);
						data = Byte.parseByte(cp.getArgs()[1].split(":")[1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Invalid Material Data. Must be integer.");
					} 

				} else {
					mat = BlockType.lookup(cp.getArgs()[1]);
				}

				if (cp.getPlayer() != null) {
					selection = Exodus.getWorldEditPlugin().getSelection(cp.getPlayer());
				} else {
					for (int i = 0; i < cp.getArgs().length; i++) {
						if (cp.getArgs()[i].equalsIgnoreCase("from")) {
							try {
								min = new Vector(Double.parseDouble(cp.getArgs()[i + 1]), Double.parseDouble(cp.getArgs()[i + 2]), Double.parseDouble(cp.getArgs()[i + 3]));
							} catch (NumberFormatException e) {
								throw new IllegalArgumentException("Invalid 'From' Location.");
							}
						}
						if (cp.getArgs()[i].equalsIgnoreCase("to")) {
							try {
								max = new Vector(Double.parseDouble(cp.getArgs()[i + 1]), Double.parseDouble(cp.getArgs()[i + 2]), Double.parseDouble(cp.getArgs()[i + 3]));
							} catch (NumberFormatException e) {
								throw new IllegalArgumentException("Invalid 'To' Location.");
							}
						}
					}
				}

				if (selection instanceof CuboidSelection) {
					min = selection.getNativeMinimumPoint();
                    max = selection.getNativeMaximumPoint();
				} else {
					throw new IllegalArgumentException("You must first select a region");
				}
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				if (min != null) {
					for(int x = min.getBlockX();x <= max.getBlockX(); x=x+1){
						for(int y = min.getBlockY();y <= max.getBlockY(); y=y+1){
							for(int z = min.getBlockZ();z <= max.getBlockZ(); z=z+1){
								Location tmpblock = new Location(selection.getWorld(), x, y, z);
								if (tmpblock.getBlock().getTypeId() == replaceMat.getID()){
									if (replaceData != -1) {
										if (tmpblock.getBlock().getData() == replaceData) {
											tmpblock.getBlock().setTypeId(mat.getID());


											if (data != -1) {
												tmpblock.getBlock().setData(data);
											}
										}
									} else {
										tmpblock.getBlock().setTypeId(mat.getID());

										if (data != -1) {
											tmpblock.getBlock().setData(data);
										}
									}
								}
							}
						}
					}
				}
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder(); 
				sb.append(this.getClass().getSimpleName());
				sb.append(" ");
				sb.append(replaceMat.getID());
				if (replaceData != -1) {
					sb.append(":");
					sb.append(replaceData);
				}
				sb.append(" ");
				sb.append(mat.getID());
				if (data != -1) {
					sb.append(":");
					sb.append(data);
				}
				sb.append(" from ");
				sb.append(min.getX());
				sb.append(" ");
				sb.append(min.getY());
				sb.append(" ");
				sb.append(min.getZ());
				sb.append(" to ");
				sb.append(max.getX());
				sb.append(" ");
				sb.append(max.getY());
				sb.append(" ");
				sb.append(max.getZ());
				return sb.toString();
			}
		}
	}
}
