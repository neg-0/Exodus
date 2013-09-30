package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;

@DungeonBlockInfo(hasInput = true, hasOutput = false, name = "Entity Spawner", material = "NETHERRACK", description = "Spawns a list of entities when triggered.")
public class EntitySpawner extends DungeonBlock {
	
	private List<Object> spawnedEntities = new ArrayList<Object>();
	
	List<String> spawnedEntityList = new ArrayList<String>();
	
	int spawnVelocity = 0;

	Vector spawnVector = this.getLocation().toVector();
	public EntitySpawner() {
	}
	
	public EntitySpawner(Location loc) {
		super(loc);
	}

	@SuppressWarnings("unchecked")
	public EntitySpawner(Map<String, Object> map) {
		super(map);
		for (String s : (ArrayList<String>) map.get("spawnedEntityList")) {
			spawnedEntityList.add(s);
			spawnedEntities.add(stringToObject(s));
		}
	}
	
	@DungeonBlockCommand(example = "add zombie; add zombie, spider, giant; add log; add 276; add Sword of Storms", syntax = "add Entity, Entity, Entity...", description = "Adds an entity to the list of spawned entities.")
	public String add(CommandPackage cp) {
		
		List<Object> entitiesToAdd = new ArrayList<Object>();
		
		for (String s : cp.getCommaSeparatedArguments()) {
			entitiesToAdd.add(stringToObject(s));
		}
		
		spawnedEntities.addAll(entitiesToAdd);
		
		List<String> list = new ArrayList<String>();
		for (Object e : entitiesToAdd) {
			list.add(objectToString(e));
		}
		
		spawnedEntityList.addAll(list);
		
		if (list.size() == 0) {
			return "No items added!";
		} else {
			return "Added " + Joiner.on(", ").join(list);
		}
	}
	
	@DungeonBlockCommand(example = "", syntax = "clear", description = "Clears the list of spawned entities.")
	public boolean clear() {
		spawnedEntities.clear();
		spawnedEntityList.clear();
		return true;
	}
	
	public Location getSpawnLocation() {
		return spawnVector.toLocation(this.getWorld());
	}
	
	@SuppressWarnings("deprecation")
	@DungeonBlockCommand(example = "", syntax = "list", description = "Lists spawned entities.")
	public String list() {
		List<String> list = new ArrayList<String>();
		for (Object e : spawnedEntities) {
			if (e instanceof EntityType) {
				list.add(((EntityType) e).getName());
			} else if (e instanceof CustomEntity) {
				list.add(((CustomEntity) e).getType().toString());
			} else if (e instanceof CustomItem) {
				list.add(((CustomItem) e).getName());
			} else if (e instanceof ItemStack) {
				list.add(((ItemStack) e).getType().toString());
			} 
		}
		return "Spawned Entities: " + Joiner.on(", ").join(list);
	}
	
	@DungeonBlockCommand(description = "", example = "", syntax = "")
	public String listSize(CommandPackage cp) {
		return "" + this.spawnedEntityList.size();
	}
	
	@SuppressWarnings("deprecation")
	private String objectToString(Object o) {
		if (o instanceof EntityType) {
			return ((EntityType) o).getName();
		} else if (o instanceof CustomEntity) {
			return ((CustomEntity) o).getType().toString();
		} else if (o instanceof CustomItem) {
			return ((CustomItem) o).getName();
		} else if (o instanceof ItemStack) {
			return ((ItemStack) o).getType().toString();
		} else {
			return null;
		}
	}
	
	public void onTrigger(DungeonBlockEvent event) {		
		for (Object e : spawnedEntities) {
			if (e instanceof EntityType) {
				getSpawnLocation().getWorld().spawnEntity(getSpawnLocation(), (EntityType) e);
			} else if (e instanceof CustomEntity) {
				CustomEntity.spawn(((CustomEntity) e).getType(), getSpawnLocation(), 10);
			} else if (e instanceof ItemStack) {
				getSpawnLocation().getWorld().dropItemNaturally(getSpawnLocation(), new ItemStack((ItemStack) e));
			} else if (e instanceof CustomItem) {
				getSpawnLocation().getWorld().dropItemNaturally(getSpawnLocation(), new CustomItem((CustomItem) e));
			}
		}
	}
	
	@DungeonBlockCommand(example = "remove zombie; remove zombie spider giant", syntax = "remove EntityType...", description = "Removes an entity from the list of spawned entities.")
	public String remove(CommandPackage cp) {
		List<Object> entitiesToRemove = new ArrayList<Object>();
		
		for (String s : cp.getCommaSeparatedArguments()) {
			entitiesToRemove.add(stringToObject(s));
		}
		
		spawnedEntities.removeAll(entitiesToRemove);
		
		List<String> list = new ArrayList<String>();
		for (Object e : entitiesToRemove) {
			list.add(objectToString(e));
		}
		
		spawnedEntityList.removeAll(list);
		
		if (list.size() == 0) {
			return "No items removed!";
		} else {
			return "Removed " + Joiner.on(", ").join(list);
		}
	}

	@DungeonBlockCommand(description = "Sets the location to your current position.", example = "", syntax = "")
	public String setLocation(CommandPackage cp) {
		Location loc = cp.getExoPlayer().getPlayer().getLocation();
		setSpawnVector(loc.toVector().multiply(spawnVelocity));
		return "Location set to " + loc.getWorld() + ": " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ();
	}
	
	private void setSpawnVector(Vector vector) {
		this.spawnVector = vector;
	}

	@SuppressWarnings("deprecation")
	private Object stringToObject(String s) {
		if (CustomItemHandler.getDefinedItem(s) != null) {
			return CustomItemHandler.getDefinedItem(s);
		}

		//Items be ID
		try {
			int i = Integer.parseInt(s);
			return new ItemStack(i);
		} catch(NumberFormatException ex) { 

		}

		//Items by name
		if (Material.getMaterial(s) != null) {
			return new ItemStack(Material.getMaterial(s));
		}

		//Entities by name
		if (EntityType.fromName(s) != null) {
			return EntityType.fromName(s);

		} 
		return null;
	}

	@DungeonBlockCommand(description = "", example = "", syntax = "")
	public String velocity(CommandPackage cp) {
		if (cp.getArgs()[0].equalsIgnoreCase("clear")) {
			this.spawnVector = this.spawnVector.toLocation(this.getWorld()).toVector();
			return "Velocity cleared.";
		} else {
			try {
				this.spawnVelocity = Integer.valueOf(cp.getArgs()[0]);
				this.spawnVector = this.spawnVector.multiply(this.spawnVelocity);
				return "Velocity set to " + cp.getArgs()[0];
			} catch (NumberFormatException e) {
				return "Velocity must be a number or \"clear\"!";
			}
		}
	}
}