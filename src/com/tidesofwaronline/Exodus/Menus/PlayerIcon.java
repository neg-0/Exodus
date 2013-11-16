package com.tidesofwaronline.Exodus.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class PlayerIcon extends DynamicIcon {
	
	List<String> lore = new ArrayList<String>();
	ExoPlayer exoPlayer;
	
	PlayerIcon(ExoPlayer exoPlayer) {
		super(new ItemStack(Material.SKULL), exoPlayer);
		this.setName("Level " + exoPlayer.getLevel() + " " + exoPlayer.getPlayerClass());
	}
}
