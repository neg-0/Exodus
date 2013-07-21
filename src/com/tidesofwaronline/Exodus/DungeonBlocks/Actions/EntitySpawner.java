package com.tidesofwaronline.Exodus.DungeonBlocks.Actions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlockCommand;

@DungeonBlockInfo(hasInput = true, hasOutput = false, name = "Mob Spawner", material = "NETHERRACK", settings = { "EntityList" })
public class EntitySpawner extends DungeonBlock {
	
	List<EntityType> spawnedEntity = new ArrayList<EntityType>();
	
	public EntitySpawner(Location loc) {
		super(loc);
	}

	public EntitySpawner() {
	}

	public void onTrigger() {
		for (EntityType e : spawnedEntity) {
			this.getBlock().getWorld().spawnEntity(this.getLocation(), e);
		}
	}
	
	@DungeonBlockCommand(example = "add zombie; add zombie spider giant", syntax = "add EntityType...")
	public boolean add(String[] e) {
		List<EntityType> toAdd = new ArrayList<EntityType>();
		for (String s : e) {
			if (EntityType.fromName(s) != null) {
				toAdd.add(EntityType.fromName(s));
			}
		}
		return spawnedEntity.addAll(toAdd);
	}
	
	@DungeonBlockCommand(example = "remove zombie; add zombie spider giant", syntax = "remove EntityType...")
	public boolean remove(String[] e) {
		List<EntityType> toRemove = new ArrayList<EntityType>();
		for (String s : e) {
			if (EntityType.fromName(s) != null) {
				toRemove.remove(EntityType.fromName(s));
			}
		}
		return spawnedEntity.removeAll(toRemove);
	}
	
	@DungeonBlockCommand(example = "", syntax = "list")
	public String list() {
		return "Spawned Entities: " + Joiner.on(", ").join(spawnedEntity);
	}
	
	@DungeonBlockCommand(example = "", syntax = "clear")
	public boolean clear() {
		spawnedEntity.clear();
		return true;
	}
}
