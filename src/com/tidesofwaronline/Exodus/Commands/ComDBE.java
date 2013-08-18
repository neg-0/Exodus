package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExoPlayer.ExoGameMode;
import com.tidesofwaronline.Exodus.Worlds.ExoWorld;

public class ComDBE extends Command {
	
	Plugin plugin;

	public ComDBE(Plugin plugin, Player player, String[] args) {
		this.plugin = plugin;
		
		if (player.isOp() || player.hasPermission("exo.dungeoneditor")) {
			
			ExoPlayer ep = ExoPlayer.getExodusPlayer(player);
			
			if (args.length != 0) {
				String command = args[0];
				
				if (command.equalsIgnoreCase("on")) {
					ep.setExoGameMode(ExoGameMode.DBEDITOR);
					ep.setEditingBlock(null);
				}
				
				if (command.equalsIgnoreCase("off")) {
					ep.setExoGameMode(ExoGameMode.BUILD);
					ep.setEditingBlock(null);
				}
				
				if (command.equalsIgnoreCase("save")) {
					player.sendMessage("Saving this world's Dungeon Blocks to disk.");
					ExoWorld.getExoWorld(player.getWorld()).saveDungeonBlocks();
				}
				
				if (command.equalsIgnoreCase("load")) {
					player.sendMessage("Loading this world's Dungeon Blocks from disk.");
					ExoWorld.getExoWorld(player.getWorld()).loadDungeonBlocks();
				}
				
				if (command.equalsIgnoreCase("list")) {
					if (DungeonBlock.getDungeonBlocks(player.getWorld()) != null) {
						player.sendMessage(Joiner.on(", ").join(DungeonBlock.getDungeonBlocks(player.getWorld())));
					}
				}
			}
		}
	}

}
