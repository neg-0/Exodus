package com.tidesofwaronline.Exodus.Commands;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.entity.Player;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExoPlayer.ExoGameMode;
import com.tidesofwaronline.Exodus.Worlds.ExoWorld;

public class ComDBE extends Command {
	
	public ComDBE(CommandPackage comPackage) {
		
		Player player = comPackage.getPlayer();
		String[] args = comPackage.getArgs();
		
		if (player.isOp() || player.hasPermission("exo.dungeoneditor")) {
			
			ExoPlayer ep = ExoPlayer.getExodusPlayer(player);
			
			if (args.length != 0) {
				String command = args[0];
				
				if (command.equalsIgnoreCase("on")) {
					ep.setExoGameMode(ExoGameMode.DBEDITOR);
					ep.setEditingBlock(null);
				} else if (command.equalsIgnoreCase("off")) {
					ep.setExoGameMode(ExoGameMode.BUILD);
					ep.setEditingBlock(null);
				} else if (command.equalsIgnoreCase("save")) {
					player.sendMessage("Saving this world's Dungeon Blocks to disk.");
					ExoWorld.getExoWorld(player.getWorld()).saveDungeonBlocks();
				} else if (command.equalsIgnoreCase("load")) {
					player.sendMessage("Loading this world's Dungeon Blocks from disk.");
					ExoWorld.getExoWorld(player.getWorld()).loadDungeonBlocks();
				} else if (command.equalsIgnoreCase("list")) {
					final Collection<DungeonBlock> list = DungeonBlock.getDungeonBlocks(player.getWorld());
					if (list != null) {
						if (list.size() > 0) {
							player.sendMessage(Joiner.on(", ").join(DungeonBlock.getDungeonBlocks(player.getWorld())));
						} else {
							player.sendMessage("There are no DungeonBlocks in this world.");
						}
					}
				} else if (command.equalsIgnoreCase("deleteall")) {
					Iterator<DungeonBlock> i = DungeonBlock.getDungeonBlocks(player.getWorld()).iterator();
					while (i.hasNext()) {
						DungeonBlock db = i.next();
						i.remove();
						db.delete();
					}
				} else {
					player.sendMessage("/dbe on, off, save, load, list, heartbeat, deleteall");
				}
			} else {
				player.sendMessage("/dbe on, off, save, load, list, heartbeat, deleteall");
			}
		}
	}

}
