package com.tidesofwaronline.Exodus.Menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ItemIcon extends DynamicIcon{
	
	enum IconType {
		
		HEAD(Material.LEATHER_HELMET),
		CHEST(Material.LEATHER_CHESTPLATE),
		LEGS(Material.LEATHER_LEGGINGS),
		FEET(Material.LEATHER_BOOTS),
		MELEE(Material.IRON_SWORD),
		UNIQUE(Material.BOW),
		REGEANT(Material.ARROW),
		TRINKET(Material.NETHER_STAR),
		RING1(Material.ENDER_PEARL),
		RING2(Material.ENDER_PEARL);
		
		private Material material;
		
		IconType(Material material) {
			this.material = material;
		}
		
		Material getMaterial() {
			return material;
		}
	}

	public ItemIcon(IconType iconType, ExoPlayer exoPlayer) {
		super(new ItemStack(iconType.getMaterial()), exoPlayer);
	}}
