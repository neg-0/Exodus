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
import com.tidesofwaronline.Exodus.Commands.ComDBEBlockCommand.CommandInfo;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Guilds.Guild;
import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(hasInput = true, hasOutput = true, name = "Command Executor", material = "COAL_BLOCK", description = "Executes commands when triggered.")
public class CommandExecutor extends DungeonBlock {
	
	public List<Event> eventList = new ArrayList<Event>();
		
	public CommandExecutor() {
		
	}
	
	public CommandExecutor(Location loc) {
		super(loc);
	}
	
	@Override
	public void onTrigger(DungeonBlockEvent event) {
		Thread runner = new Thread(new Runner(this, event));
		runner.start();
	}
	
	public List<Event> getEventList() {
		return eventList;
	}

	public Event addEvent(Event e) {
		this.eventList.add(e);
		return e;
	}
	
	public Event insertEvent(Event e, int i) {
		this.eventList.add(i, e);
		return e;
	}
	
	public void clearEvents() {
		eventList.clear();
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
		
		for (Class<?> clazz : this.getClass().getDeclaredClasses()) {
			if (clazz.getSimpleName().equals("Event")) {
				for (Class<?> clazz1 : clazz.getDeclaredClasses()) {
					for (Class<?> clazz2 : clazz1.getInterfaces()) {
						if (clazz2.isInterface() && clazz2.getSimpleName().equals("Event")) {
							toReturn.add((Class<Event>) clazz1);
						}
					 }
				}
			}
		}
		return toReturn;
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
	
	@SuppressWarnings("unchecked")
	@DungeonBlockCommand(description = "Appends an Event to the end of the list.", example = "add /say Hello; add wait 500", syntax = "add <Event>")
	public String add(CommandInfo ci) {
		if (isValidEventType(ci.getArguments()[0])) {
			Class<Event> clazz = getEventType(ci.getArguments()[0]);
			try {
				Constructor<Event> con = (Constructor<Event>) clazz.getConstructors()[0];
				Class<?> params = con.getParameterTypes()[0];
				
				if (params.equals(String.class)) {
					return "Added §e" + addEvent(con.newInstance(new Object[] {Joiner.on(" ").join(Arrays.copyOfRange(ci.getArguments(), 1, ci.getArguments().length)) }));
				} else if (params.isArray() && params.getComponentType().equals(DungeonBlock.class)) {
					List<DungeonBlock> dungeonBlocks = new ArrayList<DungeonBlock>();
					List<String> notFound = new ArrayList<String>();
					for (String s : ci.getCommaSeparatedArguments()) {
						if (getDungeonBlock(this.getWorld(), s) != null) {
							dungeonBlocks.add(getDungeonBlock(this.getWorld(), s));
						} else {
							notFound.add(s);
						}
					}
					if (notFound.size() != 0) {
						ci.getExoPlayer().sendMessage("Added §e" + addEvent(con.newInstance(dungeonBlocks.toArray())));
						return "DungeonBlocks not found: §3" + Joiner.on(", ").join(notFound);
					} else {
						return "Added §e" + addEvent(con.newInstance(dungeonBlocks.toArray()));
					}
				} else if (params.equals(long.class)) {
					try {
						long l = Long.parseLong(ci.getArguments()[1]);
						return "Added §e" + addEvent(con.newInstance(l));
					} catch (NumberFormatException e) {
						return "Invalid input: must be a number";
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
		return "No such Event: \"" + ci.getArguments()[0] + "\". Try typing Events.";
	}
	
	@DungeonBlockCommand(description = "Inserts a command at the specified location.", example = "insert 4 say Hello", syntax = "insert <#> <Command>")
	public String insert(CommandInfo ci) {
		if (ci.getArguments().length < 2) {
			return "Invalid number of Arguments.";
		}
		return null;
		

	}
	
	@DungeonBlockCommand(description = "Removes the specified indices.", example = "remove 5 2 1", syntax = "remove <Index, Index...>")
	public String remove(CommandInfo ci) {
		if (ci.getArguments().length == 0) {
			return "Invalid number of Arguments.";
		}
		List<Integer> toRemove = new ArrayList<Integer>();
		for (String s : ci.getArguments()) {
			try {
				int i = Integer.valueOf(s);
				eventList.remove(i);
				toRemove.add(i);
			} catch (NumberFormatException e) {
			}
		}
		if (toRemove.size() == 0) {
			return "No indexes were found. Nothing removed.";
		}
		String toReturn =  "Removed Command";
		if (toRemove.size() > 1) {
			toReturn += "s";
		}
		toReturn += " " + Joiner.on(", ").join(toRemove);
		toReturn += ".";
		return toReturn;
	}
	
	@DungeonBlockCommand(description = "Returns the list of executed commands.", example = "", syntax = "")
	public List<String> list(CommandInfo ci) {
		List<String> toReturn = new ArrayList<String>();
		for (int i = 0; i < this.getEventList().size(); i++) {
			toReturn.add((i + 1) + ". §e"+ this.getEventList().get(i));
		}
		if (toReturn.size() == 0) {
			return Lists.newArrayList("Events list empty.");
		} else {
			return toReturn;
		}
	}
	
	@DungeonBlockCommand(description = "Returns a list of available Events.", example = "", syntax = "")
	public String events(CommandInfo ci) {
		List<String> list = new ArrayList<String>();
		for (Class<Event> e : getEventTypes()) {
			list.add(e.getSimpleName());
		}
		return Joiner.on(", ").join(list);
	}
	
	@DungeonBlockCommand(description = "Clears the Event list.", example = "", syntax = "")
	public String clear(CommandInfo ci) {
		this.clearEvents();
		return "Events cleared.";
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Enabled", this.isEnabled());
		return map;
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
			
			try {
				for (CommandExecutor.Event e : this.commandExecutor.getEventList()) {
				e.onTrigger(event);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public abstract interface Event {
		
		public void onTrigger(DungeonBlockEvent event) throws InterruptedException;
		public String toString();
		
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
				return "Console Command " + command;
			}
		}
		
		class CCom extends ConsoleCommand implements Event {

			public CCom(String command) {
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
				return "Player Command " + command;
			}
		}
		
		class PCom extends PlayerCommand implements Event {

			public PCom(String command) {
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
				return "Party Command " + command;
			}
		}
		
		class ParCom extends PartyCommand implements Event {

			public ParCom(String command) {
				super(command);
			}
			
		}
		
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
				return "Guild Command " + command;
			}
		}
		
		class GCom extends GuildCommand implements Event {

			public GCom(String command) {
				super(command);
			}
			
		}
		
		class Wait implements Event {
			
			long milliseconds = 0;
			
			public Wait(long milliseconds) {
				this.milliseconds = milliseconds;
			}
			
			@Override
			public String toString() {
				return "Wait " + milliseconds;
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) throws InterruptedException {
				Thread.sleep(milliseconds);
				
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
	}
}
