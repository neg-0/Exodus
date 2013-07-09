package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComDBE extends Command {
	
	Plugin plugin;

	public ComDBE(Plugin plugin, Player player, String[] args) {
		this.plugin = plugin;
		
		if (player.isOp() || player.hasPermission("exo.dungeoneditor")) {
			
			ExoPlayer ep = ExoPlayer.getExodusPlayer(player);
			
			if (args.length != 0) {
				String command = args[0];
				
				if (command.equalsIgnoreCase("on")) {
					ep.setInDBEditorMode(true);
				}
				
				if (command.equalsIgnoreCase("off")) {
					ep.setInDBEditorMode(false);
				}
			}
		}
	}

}
