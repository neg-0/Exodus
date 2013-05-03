package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComLoad extends Command {
	
	public ComLoad(Player player) {
		ExoPlayer.getExodusPlayer(player).inventoryLoad();
	}
}