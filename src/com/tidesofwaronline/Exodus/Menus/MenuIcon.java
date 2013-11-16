package com.tidesofwaronline.Exodus.Menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class MenuIcon extends DynamicIcon {
	
	enum IconType {
		PLAYER_INFO(Material.SKULL_ITEM),
		SPELLBOOK(Material.BOOK),
		HEARTHSTONE(Material.EYE_OF_ENDER),
		REPUTATION(Material.LAPIS_BLOCK),
		OPTIONS(Material.REDSTONE_TORCH_ON);

		Material material;

		IconType(Material material) {
			this.material = material;
		}
		
		Material getMaterial() {
			return this.material;
		}
	}
	
	IconType iconType;

	public MenuIcon(IconType iconType, ExoPlayer exoPlayer) {
		super(new ItemStack(iconType.getMaterial()), exoPlayer);
		this.iconType = iconType;
	}
	
	public IconType getIconType() {
		return this.iconType;
	}
}
