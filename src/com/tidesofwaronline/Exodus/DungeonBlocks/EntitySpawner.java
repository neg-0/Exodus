package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Commands.ComDBEBlockCommand.CommandInfo;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;
import com.tidesofwaronline.Exodus.CustomItem.CustomItem;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;

@DungeonBlockInfo(hasInput = true, hasOutput = false, name = "Entity Spawner", material = "NETHERRACK", description = "Spawns a list of entities when triggered.")
public class EntitySpawner extends DungeonBlock {
	
	List<Object> spawnedEntities = new ArrayList<Object>();
	
	Location spawnLocation = this.getLocation();
	
	public EntitySpawner(Location loc) {
		super(loc);
	}

	public EntitySpawner() {
	}
	
	public void onTrigger(DungeonBlockEvent event) {
		for (Object e : spawnedEntities) {
			if (e instanceof EntityType) {
				Bukkit.broadcastMessage("spawn");
				spawnLocation.getWorld().spawnEntity(spawnLocation, (EntityType) e);
			} else if (e instanceof CustomEntity) {
				CustomEntity.spawn(((CustomEntity) e).getType(), spawnLocation, 10);
			} else if (e instanceof ItemStack) {
				spawnLocation.getWorld().dropItemNaturally(spawnLocation, new ItemStack((ItemStack) e));
			} else if (e instanceof CustomItem) {
				spawnLocation.getWorld().dropItemNaturally(spawnLocation, new CustomItem((CustomItem) e));
			}
		}
	}
	
	@DungeonBlockCommand(example = "add zombie; add zombie, spider, giant; add log; add 276; add Sword of Storms", syntax = "add Entity, Entity, Entity...", description = "Adds an entity to the list of spawned entities.")
	public String add(CommandInfo ci) {
		List<Object> entitiesToAdd = new ArrayList<Object>();
		
		for (String s : ci.getCommaSeparatedArguments()) {
			//Custom Items
			if (CustomItemHandler.getDefinedItem(s) != null) {
				entitiesToAdd.add(CustomItemHandler.getDefinedItem(s));
			}

			//Items be ID
			try {
				int i = Integer.parseInt(s);
				entitiesToAdd.add(new ItemStack(i));
		    } catch(NumberFormatException ex) { 
		    	
		    }
			
			//Items by name
			if (Material.getMaterial(s) != null) {
				entitiesToAdd.add(new ItemStack(Material.getMaterial(s)));
			}
			
			//Entities by name
			if (EntityType.fromName(s) != null) {
				entitiesToAdd.add(EntityType.fromName(s));
			}
		}
		
		spawnedEntities.addAll(entitiesToAdd);

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
		
		
		if (list.size() == 0) {
			return "No items added!";
		} else {
			return "Added " + Joiner.on(", ").join(list);
		}
	}
	
	@DungeonBlockCommand(example = "remove zombie; remove zombie spider giant", syntax = "remove EntityType...", description = "Removes an entity from the list of spawned entities.")
	public String remove(CommandInfo ci) {
		List<Object> entitiesToRemove = new ArrayList<Object>();
		
		for (String s : ci.getCommaSeparatedArguments()) {
			//Custom Items
			if (CustomItemHandler.getDefinedItem(s) != null) {
				entitiesToRemove.add(CustomItemHandler.getDefinedItem(s));
			}

			//Items be ID
			try {
				int i = Integer.parseInt(s);
				entitiesToRemove.add(new ItemStack(i));
		    } catch(NumberFormatException ex) { 
		    	
		    }
			
			//Items by name
			if (Material.getMaterial(s) != null) {
				entitiesToRemove.add(new ItemStack(Material.getMaterial(s)));
			}
			
			//Entities by name
			if (EntityType.fromName(s) != null) {
				entitiesToRemove.add(EntityType.fromName(s));
			}
		}
		
		spawnedEntities.removeAll(entitiesToRemove);
		
		List<String> list = new ArrayList<String>();
		for (Object et : entitiesToRemove) {
			list.add(et.toString());
		}
		
		if (list.size() == 0) {
			return "No items removed!";
		} else {
			return "Removed " + Joiner.on(", ").join(list);
		}
	}
	
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
	
	@DungeonBlockCommand(example = "", syntax = "clear", description = "Clears the list of spawned entities.")
	public boolean clear() {
		spawnedEntities.clear();
		return true;
	}
	
	@DungeonBlockCommand(description = "Sets the location to your current position.", example = "", syntax = "")
	public String setLocation(CommandInfo ci) {
		Location loc = ci.getExoPlayer().getPlayer().getLocation();
		this.spawnLocation = loc;
		return "Location set to " + loc.getWorld() + ": " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ();
	}

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
