package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComLoad extends Command {
	
	public ComLoad(Player player) {
		PlayerIndex.getExodusPlayer(player).inventoryLoad();
	}
}