package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;
import com.tidesofwaronline.Exodus.CustomItem.CustomItem;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler;

@DungeonBlockInfo(hasInput = true, hasOutput = false, name = "Entity Spawner", material = "NETHERRACK", description = "Spawns a list of entities when triggered.")
public class EntitySpawner extends DungeonBlock {
	
	List<EntityType> spawnedEntities = new ArrayList<EntityType>();
	List<CustomEntity> spawnedCustomEntities = new ArrayList<CustomEntity>();
	List<ItemStack> spawnedItemStacks = new ArrayList<ItemStack>();
	List<CustomItem> spawnedCustomItems = new ArrayList<CustomItem>();
	
	public EntitySpawner(Location loc) {
		super(loc);
	}

	public EntitySpawner() {
	}

	public void onTrigger() {
		for (EntityType e : spawnedEntities) {
			this.getBlock().getWorld().spawnEntity(this.getLocation(), e);
		}
		
		for (CustomEntity e : spawnedCustomEntities) {
			CustomEntity.spawn(e.getType(), this.getLocation(), 10);
		}
		
		for (ItemStack i : spawnedItemStacks) {
			this.getBlock().getWorld().dropItemNaturally(this.getLocation(), new ItemStack(i));
		}
		
		for (CustomItem i : spawnedCustomItems) {
			this.getBlock().getWorld().dropItemNaturally(this.getLocation(), new CustomItem(i));
		}
	}
	
	@DungeonBlockCommand(example = "add zombie; add zombie, spider, giant; add log; add 276; add Sword of Storms", syntax = "add Entity, Entity, Entity...", description = "Adds an entity to the list of spawned entities.")
	public String add(String[] e) {
		List<EntityType> entitiesToAdd = new ArrayList<EntityType>();
		List<CustomEntity> customEntitiesToAdd = new ArrayList<CustomEntity>();
		List<ItemStack> itemStacksToAdd = new ArrayList<ItemStack>();
		List<CustomItem> customItemsToAdd = new ArrayList<CustomItem>();
				
		String[] args = Joiner.on(" ").join(e).split(",");
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].trim();
		}
		
		for (String s : args) {
			//Custom Items
			if (CustomItemHandler.getDefinedItem(s) != null) {
				customItemsToAdd.add(CustomItemHandler.getDefinedItem(s));
			}

			//Items be ID
			try {
				int i = Integer.parseInt(s);
				itemStacksToAdd.add(new ItemStack(i));
		    } catch(NumberFormatException ex) { 
		    	
		    }
			
			//Items by name
			if (Material.getMaterial(s) != null) {
				itemStacksToAdd.add(new ItemStack(Material.getMaterial(s)));
			}
			
			//Entities by name
			if (EntityType.fromName(s) != null) {
				entitiesToAdd.add(EntityType.fromName(s));
			}
		}
		
		spawnedEntities.addAll(entitiesToAdd);
		spawnedCustomEntities.addAll(customEntitiesToAdd);
		spawnedItemStacks.addAll(itemStacksToAdd);
		spawnedCustomItems.addAll(customItemsToAdd);
		
		List<String> list = new ArrayList<String>();
		for (EntityType et : entitiesToAdd) {
			list.add(et.getName());
		}
		
		for (CustomEntity ce : customEntitiesToAdd) {
			list.add(ce.getType().getName());
		}
		
		for (ItemStack i : itemStacksToAdd) {
			list.add(i.getType().toString());
		}
		
		for (CustomItem i : customItemsToAdd) {
			list.add(i.getName());
		}
		
		if (list.size() == 0) {
			return "No items added!";
		} else {
			return "Added " + Joiner.on(", ").join(list);
		}
	}
	
	@DungeonBlockCommand(example = "remove zombie; remove zombie spider giant", syntax = "remove EntityType...", description = "Removes an entity from the list of spawned entities.")
	public String remove(String[] e) {
		List<EntityType> entitiesToRemove = new ArrayList<EntityType>();
		List<CustomEntity> customEntitiesToRemove = new ArrayList<CustomEntity>();
		List<ItemStack> itemStacksToRemove = new ArrayList<ItemStack>();
		List<CustomItem> customItemsToRemove = new ArrayList<CustomItem>();
				
		String[] args = Joiner.on(" ").join(e).split(",");
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].trim();
		}
		
		for (String s : args) {
			//Custom Items
			if (CustomItemHandler.getDefinedItem(s) != null) {
				customItemsToRemove.add(CustomItemHandler.getDefinedItem(s));
			}

			//Items be ID
			try {
				int i = Integer.parseInt(s);
				itemStacksToRemove.add(new ItemStack(i));
		    } catch(NumberFormatException ex) { 
		    	
		    }
			
			//Items by name
			if (Material.getMaterial(s) != null) {
				itemStacksToRemove.add(new ItemStack(Material.getMaterial(s)));
			}
			
			//Entities by name
			if (EntityType.fromName(s) != null) {
				entitiesToRemove.add(EntityType.fromName(s));
			}
		}
		
		spawnedEntities.removeAll(entitiesToRemove);
		spawnedCustomEntities.removeAll(customEntitiesToRemove);
		spawnedItemStacks.removeAll(itemStacksToRemove);
		spawnedCustomItems.removeAll(customItemsToRemove);
		
		List<String> list = new ArrayList<String>();
		for (EntityType et : entitiesToRemove) {
			list.add(et.getName());
		}
		
		for (CustomEntity ce : customEntitiesToRemove) {
			list.add(ce.getType().getName());
		}
		
		for (ItemStack i : itemStacksToRemove) {
			list.add(i.getType().toString());
		}
		
		for (CustomItem i : customItemsToRemove) {
			list.add(i.getName());
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
		for (EntityType et : spawnedEntities) {
			list.add(et.getName());
		}
		
		for (CustomEntity ce : spawnedCustomEntities) {
			list.add(ce.getType().getName());
		}
		
		for (ItemStack i : spawnedItemStacks) {
			list.add(i.getType().toString());
		}
		
		for (CustomItem i : spawnedCustomItems) {
			list.add(i.getName());
		}
		
		return "Spawned Entities: " + Joiner.on(", ").join(list);
	}
	
	@DungeonBlockCommand(example = "", syntax = "clear", description = "Clears the list of spawned entities.")
	public boolean clear() {
		spawnedEntities.clear();
		spawnedCustomEntities.clear();
		spawnedItemStacks.clear();
		spawnedCustomItems.clear();
		return true;
	}
}
