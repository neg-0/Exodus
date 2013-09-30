package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;

@DungeonBlockInfo(description = "Pulses when entities come within range.", hasInput = false, hasOutput = true, material = "LAPIS_LAZULI_BLOCK", name = "Proximity Trigger")
public class ProximityTrigger extends DungeonBlock {
	
	private List<EntityType> entityTypes = new ArrayList<EntityType>();
	int proximity = 10;
	private Mode mode = Mode.DISABLED;
	private Thread runner;
	
	public ProximityTrigger() {
		super();
		if (this.isEnabled()) {
			startRunner();
		}
	}

	public ProximityTrigger(Location loc) {
		super(loc);
		if (this.isEnabled()) {
			startRunner();
		}
	}

	public ProximityTrigger(Map<String, Object> map) {
		super(map);
		
		@SuppressWarnings("unchecked")
		List<String> eventsList = (List<String>) map.get("entityTypes");
		
		for (String s : eventsList) {
			addEntity(EntityType.valueOf(s));
		}
		
		mode = Mode.valueOf((String) map.get("Mode"));
		
		if (this.isEnabled()) {
			startRunner();
		}
	}
	
	private void addEntity(EntityType e) {
		entityTypes.add(e);
	}

	private void startRunner() {
		runner = new Runner(this);
		runner.start();
	}
	
	@SuppressWarnings("deprecation")
	@DungeonBlockCommand(description = "", example = "", syntax = "")
	public void add(CommandPackage cp) {
		EntityType et = EntityType.fromName(cp.getArgs()[0]);
		if (et == null) {
			try {
				et = EntityType.fromId(Integer.parseInt(cp.getArgs()[0]));
			} catch (NumberFormatException e) {}
		}
		
		if (et != null) {
			entityTypes.add(et);
			cp.getPlayer().sendMessage("Added " + et.getName());
		} else {
			cp.getPlayer().sendMessage("No EntityType found with that name or ID.");
			return;
		}
	}
	
	@DungeonBlockCommand(description = "Clears the Entity list.", example = "", syntax = "")
	public String clear(CommandPackage cp) {
		this.clearEntities();
		return "Entities cleared.";
	}
	
	public void clearEntities() {
		this.entityTypes.clear();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.putAll(super.serialize());

		List<String> toAdd = new ArrayList<String>();
		for (EntityType e : entityTypes) {
			toAdd.add(e.getName());
		}
		
		map.put("entityTypes", toAdd);
		map.put("Mode", mode);

		return map;
	}

	private class Runner extends Thread { 
		
		ProximityTrigger p;
		
		public Runner(ProximityTrigger p) {
			this.p = p;
		}

		@Override
		public void run() {
			try {
				Bukkit.broadcastMessage("Runner running");
				while(p.isEnabled()) {
					Thread.sleep(1000);
					
					if (!p.isEnabled()) {
						this.interrupt();
						try {
							this.finalize();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					Class<?>[] classes = new Class<?>[entityTypes.size()];
					
					for (int i = 0; i < entityTypes.size(); i++) {
						classes[i] = entityTypes.get(i).getEntityClass();
					}

					for(Entity en : getLocation().getWorld().getEntitiesByClasses(classes)) {
						if(entityTypes.contains(en.getType())) {
							double distance = en.getLocation().distance(getLocation());
							if (distance < proximity) {
								for (DungeonBlock d : p.getLinkedBlocks()) {
									Bukkit.broadcastMessage("Detected a " + en.getType());
									d.onTrigger(new DungeonBlockEvent(p, en));
								}
							}
						}
					}
				}
			} catch (InterruptedException e) {

			}
		}
	}

	enum Mode {
		DISABLED,
		PLAYER,
		PARTY,
		MOB,
		ENTITY;
	}
	
	enum Reset {
		TIMED,
		SINGLE,
		INFINITE;
	}
}
