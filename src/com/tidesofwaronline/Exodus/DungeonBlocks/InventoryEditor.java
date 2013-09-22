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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Guilds.Guild;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;
import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(description = "Edits a player's inventory.", hasInput = true, hasOutput = true, material = "DIAMOND_BLOCK", name = "Inventory Editor")
public class InventoryEditor extends DungeonBlock {
	
	private List<Event> events = new ArrayList<Event>();

	public InventoryEditor() {
		super();
	}

	public InventoryEditor(Location loc) {
		super(loc);
	}

	public InventoryEditor(Map<String, Object> map) {
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
				cp.getPlayer().sendMessage(cp.getArgumentsString());
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
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				return "That's not a valid command.";
			}
			return "nope";
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
		boolean trigger = true;
		for (Event e : getEvents()) {
			if (!e.onTrigger(event)) {
				trigger = false;
			}
		}
		if (trigger == true) {
			triggerLinkedBlocks(event);
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
		
		Mode mode = Mode.PLAYER;
		String player = "";
		CustomItem item;
		int quantity;
		
		public Event() {}
		
		public Event(CommandPackage cp) {
			
			//sample:
			//add give player mastac 64 diamond
			
			if (cp.getArgs().length != 4) {
				throw new IllegalArgumentException("Wrong number of arguments; must be 4");
			}
			
			mode = Mode.valueOf(cp.getArgs()[0].toUpperCase());
			player = cp.getArgs()[1];
			try {
				quantity = Integer.parseInt(cp.getArgs()[2]);
			} catch (NumberFormatException e) {
				quantity = 1;
			}
			item = CustomItemHandler.getDefinedItem(Joiner.on(" ").join(Arrays.copyOfRange(cp.getArgs(), 3, cp.getArgs().length)));
			if (item == null) {
				item = new CustomItem(Material.matchMaterial(cp.getArgs()[3]));
			}
						
			if (item != null) {
				item.setAmount(quantity);
			}
		}

		public boolean onTrigger(DungeonBlockEvent event) {
			if (event.getEntity() instanceof Player) {
				switch (this.mode){
				case PLAYER: {
					ExoPlayer.giveItem(ExoPlayer.getExodusPlayer((Player) event.getEntity()), item);
					return true;
				}
				case PARTY: {
					Party party = ((ExoPlayer) event.getEntity()).getParty();
					if (party != null) {
						for (ExoPlayer ep : party.getMembers()) {
							ExoPlayer.giveItem(ep, item);
							return true;
						}
					}
				}
				case GUILD: {
					Guild guild = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getGuild();
					if (guild != null) {
						for (ExoPlayer ep : guild.getMembers()) {
							ExoPlayer.giveItem(ep, item);
							return true;
						}
					}
				}
				default: return false;
				}

			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return this.getClass().getSimpleName() + " " + mode.toString().toLowerCase() + " " + player + " " + quantity + " " + item.getName().toLowerCase();
		}
		
		public class Check extends Event {
						
			public Check(CommandPackage cp) {
				super(cp);
			}

			@Override
			public boolean onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					switch (this.mode){
					case PLAYER: {
						return ((Player) event.getEntity()).getInventory().containsAtLeast(item, quantity);
					}
					case PARTY: {
						Party party = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getParty();
						if (party != null) {
							for (ExoPlayer ep : party.getMembers()) {
								if (!ep.getPlayer().getInventory().containsAtLeast(item, quantity)) {
									return false;
								}
							}
							return true;
						}
					}
					case GUILD: {
						Guild guild = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getGuild();
						if (guild != null) {
							for (ExoPlayer ep : guild.getMembers()) {
								if (!ep.getPlayer().getInventory().containsAtLeast(item, quantity)) {
									return false;
								}
							}
							return true;
						}
					}
					default: return false;
					}

				} else {
					return false;
				}
			}
		}
		
		class Gift extends Event {
			
			public Gift(CommandPackage cp) {
				super(cp);
			}

			@Override
			public boolean onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					switch (this.mode){
					case PLAYER: {
						ExoPlayer.giveItem(ExoPlayer.getExodusPlayer((Player) event.getEntity()), item);
						return true;
					}
					case PARTY: {
						Party party = ((ExoPlayer) event.getEntity()).getParty();
						if (party != null) {
							for (ExoPlayer ep : party.getMembers()) {
								ExoPlayer.giveItem(ep, item);
								return true;
							}
						}
					}
					case GUILD: {
						Guild guild = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getGuild();
						if (guild != null) {
							for (ExoPlayer ep : guild.getMembers()) {
								ExoPlayer.giveItem(ep, item);
								return true;
							}
						}
					}
					default: return false;
					}

				} else {
					return false;
				}
			}			
		}
		
		class Give extends Event {
			
			public Give(CommandPackage cp) {
				super(cp);
			}

			@Override
			public boolean onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					switch (this.mode){
					case PLAYER: {
						HashMap<Integer, ItemStack> couldNotGive = ((Player) event.getEntity()).getInventory().addItem(item);
						return !couldNotGive.isEmpty();
					}
					case PARTY: {
						Party party = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getParty();
						if (party != null) {
							for (ExoPlayer ep : party.getMembers()) {
								ep.getPlayer().getInventory().addItem(item);
								return true;
							}
						}
					}
					case GUILD: {
						Guild guild = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getGuild();
						if (guild != null) {
							for (ExoPlayer ep : guild.getMembers()) {
								ep.getPlayer().getInventory().addItem(item);
								return true;
							}
						}
					}
					default: return false;
					}

				} else {
					return false;
				}
			}			
		}
		
		class Take extends Event {
			
			public Take(CommandPackage cp) {
				super(cp);
			}

			@Override
			public boolean onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					
				}
				return false;
			}
			
		}
	}

	enum Mode {
		PLAYER,
		PARTY,
		GUILD;
	}
}
