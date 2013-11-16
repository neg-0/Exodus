package com.tidesofwaronline.Exodus.Menus;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class DynamicIcon {
	
	String name;
	List<String> lore;
	ItemStack icon;
	ExoPlayer exoPlayer;
	
	public DynamicIcon(ItemStack icon) {
		this.icon = icon;
		this.name = icon.getType().name();
		update();
	}
	
	public DynamicIcon(ItemStack icon, ExoPlayer exoPlayer) {
		this(icon);
		this.exoPlayer = exoPlayer;
	}
	
	public DynamicIcon setName(String name) {
		this.name = name;
		return this;
	}
	
	public DynamicIcon setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public String getName() {
		return name;
	}
	
	public DynamicIcon update() {
		icon.getItemMeta().setDisplayName(this.name);
		icon.getItemMeta().setLore(this.lore);
		return this;
	}
	
}