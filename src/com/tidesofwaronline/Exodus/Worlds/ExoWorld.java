package com.tidesofwaronline.Exodus.Worlds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.tidesofwaronline.Exodus.DataStructure;
import com.tidesofwaronline.Exodus.DungeonBlocks.CommandExecutor.Event;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;

public class ExoWorld implements ConfigurationSerializable {
	
	World world;
	Thread timelock;
	static HashMap<World, ExoWorld> registry = new HashMap<World, ExoWorld>();
	
	File worldFile;
	File dungeonBlockFile;
	
	static File dungeonBlockClassesFile;
	static YamlConfiguration dungeonBlockClassesConfig;
	
	YamlConfiguration worldConfig;
	YamlConfiguration dungeonBlockConfig;
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public ExoWorld(World world) {
		this.world = world;
		register(this.world, this);
		
		DungeonBlock.initWorldRegistries(this);
		
		worldFile = new File(DataStructure.getWorldsFolder() + world.getName() + "/world.yml");
		dungeonBlockFile = new File(DataStructure.getWorldsFolder() + world.getName() + "/dungeonblocks.yml");
		
		worldConfig = YamlConfiguration.loadConfiguration(worldFile);
		dungeonBlockConfig = YamlConfiguration.loadConfiguration(dungeonBlockFile);
		
		load();
	}
	
	private void register(World w, ExoWorld e) {
		registry.put(w, e);
	}
	
	public static ExoWorld getExoWorld(World world) {
		return registry.get(world);
	}
	
	public void lockTime(long time) {
		timelock = new TimeLock(world, time);
		timelock.start();
	}
	
	public void unlockTime() {
		timelock.interrupt();
		timelock = null;
	}
	
	public class TimeLock extends Thread {

		World world;
		long time;
		boolean locked = true;

		public TimeLock(World world, long time) {
			this.world = world;
			this.time = time;
		}

		public void run() {
			
			while (locked) {
				world.setTime(time);
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException e) {
					locked = false;
				}

				if (Thread.interrupted()) {
					locked = false;
				}
				
				if (!locked) {
					break;
				}
			}
		}
	}
	
	public void load() {
		//loadDungeonBlocks();
	}
	
	public void loadDungeonBlocks() {
		
		Collection<DungeonBlock> dbList = DungeonBlock.getDungeonBlocks(this);
		Iterator<DungeonBlock> i = dbList.iterator();
		
		//Remove Dungeon Blocks in-game that do not exist in the dungeonBlockConfig
		while (i.hasNext()) {
			DungeonBlock db = i.next();
			if (!dungeonBlockConfig.contains(db.getName() + " " + db.getID())) {
				i.remove();
				db.delete();
			}
		}
		
		dungeonBlockConfig = YamlConfiguration.loadConfiguration(dungeonBlockFile);
	}

	public void save() {
		saveWorld();
		saveDungeonBlocks();
	}
	
	public void saveWorld() {
		worldConfig.createSection(getWorld().getName(), this.serialize());
		try {
			worldConfig.save(worldFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDungeonBlocks() {		
		if (DungeonBlock.getDungeonBlocks(this) != null) {
			Collection<DungeonBlock> dbList = DungeonBlock.getDungeonBlocks(this);
			
			if (!dungeonBlockConfig.getValues(false).isEmpty()) {
				for (String s : dungeonBlockConfig.getValues(false).keySet()) {
					if (!dbList.contains(s)) {
						dungeonBlockConfig.set(s, null);
					}
				}
			}
			
			for (DungeonBlock d : dbList) {
				if (d != null) {
					//dungeonBlockConfig.createSection(d.getName() + " " + d.getID(), d.serialize());
					dungeonBlockConfig.set(d.getName() + " " + d.getID(), d);
				}
			}
		}
		try {
			dungeonBlockConfig.save(dungeonBlockFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checkDungeonBlockClasses(this);
	}
	
	public static void checkDungeonBlockClasses(ExoWorld world) {
		if (DungeonBlock.getDungeonBlocks(world) != null) {
			for (DungeonBlock db : DungeonBlock.getDungeonBlocks(world)) {
				if (!dungeonBlockClassesConfig.contains(db.getName())) {
					dungeonBlockClassesConfig.set(db.getName(), db.getClass().getName());
				}
			}
		}
		try {
			dungeonBlockClassesConfig.save(dungeonBlockClassesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Class<? extends DungeonBlock>> getDungeonBlockClasses() {
		
		dungeonBlockClassesFile = new File(DataStructure.getWorldsFolder()+ "dungeonblockclasses.yml");
		dungeonBlockClassesConfig = YamlConfiguration.loadConfiguration(dungeonBlockClassesFile);

		List<Class<? extends DungeonBlock>> list = new ArrayList<Class<? extends DungeonBlock>>();
		for (String s : dungeonBlockClassesConfig.getKeys(false)) {
			try {
				list.add((Class<? extends DungeonBlock>) Class.forName(dungeonBlockClassesConfig.getString(s)));
			} catch (ClassNotFoundException e) {
				dungeonBlockClassesConfig.set(s, null);
			}
		}
		return list;
	}
	


	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		//TODO
		
		
		return map;
	}

	public static void checkClass(Event event) {
		if (!dungeonBlockClassesConfig.contains(event.getClass().getSimpleName())) {
			dungeonBlockClassesConfig.set(event.getClass().getSimpleName(), event.getClass().getName());
		}
	}
}
