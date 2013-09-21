package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Guilds.Guild;
import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(hasInput = true, hasOutput = true, name = "Command Executor", material = "COAL_BLOCK", description = "Executes commands when triggered.")
public class CommandExecutor extends DungeonBlock {
	private List<Event> events = new ArrayList<Event>();

	public CommandExecutor() {}

	public CommandExecutor(Location loc) {
		super(loc);
	}

	public CommandExecutor(Map<String, Object> map) {
		super(map);
		
		@SuppressWarnings("unchecked")
		List<String> eventsList = (List<String>) map.get("Events");
		
		for (String s : eventsList) {
			addEvent(parseEvent(s));
		}
	}

	@SuppressWarnings("unchecked")
	@DungeonBlockCommand(description = "Appends an Event to the end of the list.", example = "add /say Hello; add wait 500", syntax = "add <Event>")
	public String add(CommandPackage cp) {
		
		if (cp.getArgs().length == 0) {
			return "You must specify an argument";
		}
		
		if (isValidEventType(cp.getArgs()[0])) {
			Class<Event> clazz = getEventType(cp.getArgs()[0]);
			try {
				Constructor<Event> con = (Constructor<Event>) clazz.getConstructors()[0];
				for (Class<?> params : con.getParameterTypes()) {
					Bukkit.broadcastMessage(params.getSimpleName());
				}
				for (Class<?> params : con.getParameterTypes()) {
					if (params.equals(String.class)) {
						Object o = Joiner.on(" ").join(Arrays.copyOfRange(cp.getArgs(), 1, cp.getArgs().length));
						return "Added §e" + addEvent(con.newInstance(o));
					} else if (params.isArray() && params.getComponentType().equals(DungeonBlock.class)) {
						List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
						List<String> notFound = new ArrayList<String>();
						for (String s : cp.getCommaSeparatedArguments(cp.getArgs()[0])) {
							if (getDungeonBlock(this.getWorld(), s) != null) {
								dungeonBlocks.add(getDungeonBlock(this.getWorld(), s));
							} else {
								notFound.add(s);
							}
						}
						if (notFound.size() != 0) {
							if (dungeonBlocks.size() == 0) {
								return "No Dungeonblocks found";
							}
							Object o = dungeonBlocks.toArray(new DungeonBlock[dungeonBlocks.size()]);
							cp.getExoPlayer().sendMessage("Added §e" + addEvent(con.newInstance(o)));
							return "DungeonBlocks not found: §3" + Joiner.on(", ").join(notFound);
						} else {
							Object o = dungeonBlocks.toArray(new DungeonBlock[dungeonBlocks.size()]);
							return "Added §e" + addEvent(con.newInstance(o));
						}
					} else if (params.equals(long.class)) {
						try {
							Object o = Long.parseLong(cp.getArgs()[1]);
							return "Added §e" + addEvent(con.newInstance(o));
						} catch (NumberFormatException e) {
							return "Invalid input: must be a number";
						}
					}
				}
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
			}
		}
		return "No such Event: \"" + cp.getArgs()[0] + "\". Try typing Events.";
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
		for (Class<Event> e : getEventTypes()) {
			list.add(e.getSimpleName());
		}
		return Joiner.on(", ").join(list);
	}

	public List<Event> getEvents() {
		return events;
	}

	public Class<Event> getEventType(String s) {
		for (Class<Event> clazz : getEventTypes()) {
			if (clazz.getSimpleName().equalsIgnoreCase(s)) {
				return clazz;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Class<Event>> getEventTypes() {
		List<Class<Event>> toReturn = new ArrayList<Class<Event>>();
		for (Class<?> clazz : Event.class.getDeclaredClasses()) {
			if (Event.class.isAssignableFrom(clazz)) {
				toReturn.add((Class<Event>) clazz);
			}
		}
		return toReturn;
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

	public boolean isValidEventType(String s) {
		List<String> list = new ArrayList<String>();
		for (Class<?> clazz : getEventTypes()) {
			list.add(clazz.getSimpleName().toLowerCase());
		}
		if (list.contains(s.toLowerCase())) {
			return true;
		} else {
			return false;
		}
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
		Thread runner = new Thread(new Runner(this, event));
		runner.start();
	}

	@SuppressWarnings("unchecked")
	private Event parseEvent(String s) {
		String command = s.split(" ")[0];
		String[] commaSeparated = Joiner.on(" ").join(Arrays.copyOfRange(s.split(" "), 1, s.split(" ").length)).split(", ");
		String[] arguments = Arrays.copyOfRange(s.split(" "), 1, s.split(" ").length);
		if (isValidEventType(command)) {
			Class<Event> clazz = getEventType(command);
			try {
				Constructor<Event> con = (Constructor<Event>) clazz.getConstructors()[0];
				for (Class<?> params : con.getParameterTypes()) {
					Bukkit.broadcastMessage(params.getSimpleName());
				}
				for (Class<?> params : con.getParameterTypes()) {
					if (params.equals(String.class)) {
						Object o = Joiner.on(" ").join(arguments);
						return con.newInstance(o);
					} else if (params.isArray() && params.getComponentType().equals(DungeonBlock.class)) {
						List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
						for (String st : commaSeparated) {
							if (getDungeonBlock(this.getWorld(), st) != null) {
								dungeonBlocks.add(getDungeonBlock(this.getWorld(), st));
							}
						}
						return con.newInstance(new Object[] {dungeonBlocks.toArray(new DungeonBlock[dungeonBlocks.size()])});
					} else if (params.equals(long.class)) {
						try {
							Object o = Long.parseLong(arguments[0]);
							return con.newInstance(o);
						} catch (NumberFormatException e) {
						}
					}
				}
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
			}
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

	public abstract interface Event {

		public void onTrigger(DungeonBlockEvent event);
		public String toString();

		class CCom extends ConsoleCommand {
			public CCom(String command) {
				super(command);
			}
		}

		class ConsoleCommand implements Event {
			String command;

			public ConsoleCommand(String command) {
				this.command = command;
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}

			@Override
			public String toString() {
				return "ConsoleCommand " + command;
			}
		}

		class Disable implements Event {
			List<DungeonBlock> toDisable = new ArrayList<DungeonBlock>();

			public Disable(DungeonBlock... dungeonBlocks) {
				for (DungeonBlock d : dungeonBlocks) {
					toDisable.add(d);
				}
			}

			public void onTrigger(DungeonBlockEvent event) {
				for (DungeonBlock d : toDisable) {
					d.disable(null);
				}
			}

			@Override
			public String toString() {
				return "Disable " + Joiner.on(", ").join(toDisable);
			}
		}

		class Enable implements Event {
			List<DungeonBlock> toEnable = new ArrayList<DungeonBlock>();

			public Enable(DungeonBlock... dungeonBlocks) {
				for (DungeonBlock d : dungeonBlocks) {
					toEnable.add(d);
				}
			}

			public void onTrigger(DungeonBlockEvent event) {
				for (DungeonBlock d : toEnable) {
					d.enable(null);
				}
			}

			@Override
			public String toString() {
				return "Enable " + Joiner.on(", ").join(toEnable);
			}
		}

		class GCom extends GuildCommand {
			public GCom(String command) {
				super(command);
			}
		}

		@CommandAlias("GCom")
		class GuildCommand implements Event {
			String command;

			public GuildCommand(String command) {
				this.command = command;
			}

			public void onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					Guild g = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getGuild();
					for (ExoPlayer ep : g.getMembers()) {
						if (ep.getPlayer().isOnline()) {
							ep.getPlayer().performCommand(command);
						}
					}
				}
			}

			@Override
			public String toString() {
				return "GuildCommand " + command;
			}
		}

		class ParCom extends PartyCommand {
			public ParCom(String command) {
				super(command);
			}
		}

		class PartyCommand implements Event {
			String command;

			public PartyCommand(String command) {
				this.command = command;
			}

			public void onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					Party p = ExoPlayer.getExodusPlayer((Player) event.getEntity()).getParty();
					for (ExoPlayer ep : p.getMembers()) {
						if (ep.getPlayer().isOnline()) {
							ep.getPlayer().performCommand(command);
						}
					}
				}
			}

			@Override
			public String toString() {
				return "PartyCommand " + command;
			}
		}

		class PCom extends PlayerCommand {
			public PCom(String command) {
				super(command);
			}
		}

		class PlayerCommand implements Event {
			String command;

			public PlayerCommand(String command) {
				this.command = command;
			}

			public void onTrigger(DungeonBlockEvent event) {
				if (event.getEntity() instanceof Player) {
					((Player) event.getEntity()).performCommand(command);
				}
			}

			@Override
			public String toString() {
				return "PlayerCommand " + command;
			}
		}

		class Pulse implements Event {
			List<DungeonBlock> toPulse = new ArrayList<DungeonBlock>();

			public Pulse(DungeonBlock... dungeonBlocks) {
				for (DungeonBlock d : dungeonBlocks) {
					toPulse.add(d);
				}
			}

			public void onTrigger(DungeonBlockEvent event) {
				for (DungeonBlock d : toPulse) {
					if (d.isEnabled()) {
						d.onTrigger(event);
					}
				}
			}

			@Override
			public String toString() {
				return "Pulse " + Joiner.on(", ").join(toPulse);
			}
		}

		class Wait implements Event {
			long milliseconds = 0;

			public Wait(long milliseconds) {
				this.milliseconds = milliseconds;
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				try {
					Thread.sleep(milliseconds);
				} catch (InterruptedException e) {}
			}

			@Override
			public String toString() {
				return "Wait " + milliseconds;
			}
		}
	}


	public class Runner extends CommandExecutor implements Runnable {
		DungeonBlockEvent event;
		CommandExecutor commandExecutor;

		Runner(CommandExecutor commandExecutor, DungeonBlockEvent event) {
			this.event = event;
			this.commandExecutor = commandExecutor;
		}

		@Override
		public void run() {
			for (CommandExecutor.Event e : this.commandExecutor.getEvents()) {
				e.onTrigger(event);
			}
		}
	}
}
