package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComSave extends Command {
	
	public ComSave(Player player) {
		if (PlayerIndex.getExodusPlayer(player).savePlayer())
		player.sendMessage("Player Saved");
	}
}
