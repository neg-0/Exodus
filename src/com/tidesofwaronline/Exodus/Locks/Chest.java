package com.tidesofwaronline.Exodus.Locks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class Chest {
	
	public enum LockType {
		WOOD,
		IRON,
		GOLD,
		DIAMOND;
	}
	
	private UUID uuid; //UUID link to Articy
	private Location loc;
	private boolean locked = false;
	private LockType lock;
	private int size; //Custom chest size, must be multiple of 9
	
	public Chest() {
		//TODO
	}
	
	public Location getLoc() {
		return loc;
	}
	public LockType getLock() {
		return lock;
	}
	public int getSize() {
		return size;
	}
	public UUID getUUID() {
		return uuid;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void setLock(LockType lock) {
		this.lock = lock;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public static void addLockRecipes() {
		ShapelessRecipe ironLockpick = new ShapelessRecipe(new IronLockpick());
		ironLockpick.addIngredient(Material.STICK);
		ironLockpick.addIngredient(Material.IRON_INGOT);
		Bukkit.getServer().addRecipe(ironLockpick);
		
		ShapelessRecipe goldLockpick = new ShapelessRecipe(new GoldLockpick());
		goldLockpick.addIngredient(Material.STICK);
		goldLockpick.addIngredient(Material.GOLD_INGOT);
		Bukkit.getServer().addRecipe(goldLockpick);
		
		ShapelessRecipe diamondLockpick = new ShapelessRecipe(new DiamondLockpick());
		diamondLockpick.addIngredient(Material.STICK);
		diamondLockpick.addIngredient(Material.DIAMOND);
		Bukkit.getServer().addRecipe(diamondLockpick);
		
		
	}
	
}
