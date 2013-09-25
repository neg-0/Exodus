package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Effect;
import org.bukkit.Location;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Util.SerializableLocation;

@DungeonBlockInfo(description = "Plays an effect, explosion, or lightning.", hasInput = true, hasOutput = false, material = "IRON_BLOCK", name = "Effects Player")
public class EffectsPlayer extends DungeonBlock {
	
	private List<Event> events = new ArrayList<Event>();

	public EffectsPlayer() {
		super();
	}

	public EffectsPlayer(Location loc) {
		super(loc);
	}

	public EffectsPlayer(Map<String, Object> map) {
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
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				return "That's not a valid command.";
			} catch (Exception e) {
				
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
		
		SerializableLocation location;
		Effect effect;
		boolean damage = true;
		int radius = 1;
		int dataValue = 0;
		int blockID = 1;
		
		public Event() {}
		
		public Event(CommandPackage cp) {
			
			//sample:
			//add lightning safe here
			//add explosion unsafe at 100 64 200


			for (int i = 0; i < cp.getArgs().length; i++) {
				if (cp.getArgs()[i].equalsIgnoreCase("damage")) {
					damage = Boolean.parseBoolean(cp.getArgs()[i + 1]);
				} else if (cp.getArgs()[i].equalsIgnoreCase("here")) {
					location = new SerializableLocation(cp.getPlayer().getLocation());
				} else if (cp.getArgs()[i].equalsIgnoreCase("at")) {
					try {
						location = SerializableLocation.fromString(cp.getPlayer().getWorld().getName() + " " + Joiner.on(" ").join(Arrays.copyOfRange(cp.getArgs(), i + 1, i + 4)));
					} catch (ArrayIndexOutOfBoundsException e) {
						throw new IllegalArgumentException("Invalid number of Location arguments. Type the X Y and Z without the world name, letters, or characters.");
					}
				} else if (cp.getArgs()[i].contains("radius")) {
					try {
						radius = Integer.parseInt(cp.getArgs()[i + 1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Radius must be an integer");
					}
				} else if (cp.getArgs()[i].contains("datavalue")) {
					try {
						dataValue = Integer.parseInt(cp.getArgs()[i + 1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Data Value must be an integer");
					}
				} else if (cp.getArgs()[i].contains("blockID")) {
					try {
						blockID = Integer.parseInt(cp.getArgs()[i + 1]);
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Block ID must be an integer");
					}
				}
			}
		}

		public void onTrigger(DungeonBlockEvent event) {
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(); 
			sb.append(this.getClass().getSimpleName());
			sb.append(" ");
			sb.append("damage ");
			sb.append(damage);
			sb.append("radius ");
			sb.append(radius);
			sb.append("blockID ");
			sb.append(blockID);
			sb.append("dataValue ");
			sb.append(dataValue);
			sb.append(" at ");
			sb.append(location.getWorldName());
			sb.append(" ");
			sb.append(location.getX());
			sb.append(" ");
			sb.append(location.getY());
			sb.append(" ");
			sb.append(location.getZ());
			return sb.toString();
		}
		
		@EventInfo(arguments = { "" }, name = "BlazeShoot")
		public class Blazeshoot extends Event {
			
			public Blazeshoot(CommandPackage cp) {
				super(cp);
			}
			
			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.BLAZE_SHOOT, 1);
			}
		}

		@EventInfo(arguments = { "" }, name = "BowFire")
		public class Bowfire extends Event {

			public Bowfire(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.BOW_FIRE, 1);
			}
		}
		
		public class Click1 extends Event {

			public Click1(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.CLICK1, 1);
			}
		}
		
		public class Click2 extends Event {

			public Click2(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.CLICK2, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "DoorToggle")
		public class Doortoggle extends Event {

			public Doortoggle(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.DOOR_TOGGLE, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "EnderSignal")
		public class Endersignal extends Event {

			public Endersignal(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.ENDER_SIGNAL, 1);
			}
		}
		
		@EventInfo(arguments = { "damage", "radius" }, name = "Explosion")
		public class Explosion extends Event {
			
			public Explosion(CommandPackage cp) {
				super(cp);
			}
			
			@Override
			public void onTrigger(DungeonBlockEvent event) {
				if (damage) {
					location.getWorld().createExplosion(location.toLocation(), 4.0F, damage);
				} else {
					location.getWorld().createExplosion(location.toLocation(), 0.0F, damage);
				}
			}
		}
		
		public class Extinguish extends Event {

			public Extinguish(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.EXTINGUISH, 1);
			}
		}

		@EventInfo(arguments = { "damage" }, name = "Lightning")
		public class Lightning extends Event {
			
			public Lightning(CommandPackage cp) {
				super(cp);
			}
			
			@Override
			public void onTrigger(DungeonBlockEvent event) {
				if (damage) {
					location.getWorld().strikeLightning(location.toLocation());
				} else {
					location.getWorld().strikeLightningEffect(location.toLocation());
				}
			}
		}
		
		@EventInfo(arguments = { "" }, name = "GhastShoot")
		public class Ghastshoot extends Event {

			public Ghastshoot(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.GHAST_SHOOT, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "GhastShriek")
		public class Ghastshriek extends Event {

			public Ghastshriek(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.GHAST_SHRIEK, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "MobSpawnerFlames")
		public class Mobspawnerflames extends Event {

			public Mobspawnerflames(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.MOBSPAWNER_FLAMES, null);
			}
		}
		
		@EventInfo(arguments = { "DataValue" }, name = "PotionBreak")
		public class Potionbreak extends Event {

			public Potionbreak(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.POTION_BREAK, dataValue);
			}
		}
		
		@EventInfo(arguments = { "DataValue" }, name = "RecordPlay")
		public class Recordplay extends Event {

			public Recordplay(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.RECORD_PLAY, dataValue);
			}
		}
		
		public class Smoke extends Event {

			public Smoke(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.SMOKE, 0);
			}
		}
		
		@EventInfo(arguments = { "blockID" }, name = "StepSound")
		public class Stepsound extends Event {

			public Stepsound(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.STEP_SOUND, dataValue);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "ZombieChewIronDoor")
		public class Zombiechewirondoor extends Event {

			public Zombiechewirondoor(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "ZombieChewWoodenDoor")
		public class Zombiechewwoodendoor extends Event {

			public Zombiechewwoodendoor(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.ZOMBIE_CHEW_WOODEN_DOOR, 1);
			}
		}
		
		@EventInfo(arguments = { "" }, name = "ZombieDestroyDoor")
		public class Zombiedestroydoor extends Event {

			public Zombiedestroydoor(CommandPackage cp) {
				super(cp);
			}

			@Override
			public void onTrigger(DungeonBlockEvent event) {
				location.getWorld().playEffect(location.toLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
			}
		}
	}
}
