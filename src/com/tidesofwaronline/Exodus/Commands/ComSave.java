package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComSave extends Command {
	
	public ComSave(CommandPackage comPackage) {
		
		Player player = comPackage.getPlayer();
		
		if (ExoPlayer.getExodusPlayer(player).savePlayer())
		player.sendMessage("Player Saved");
	}
}
