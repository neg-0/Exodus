package com.tidesofwaronline.Exodus.Commands;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComTest extends Command {

	public ComTest(ExoPlayer player, String[] args) {
		
		for (DungeonBlock d : DungeonBlock.getDungeonBlocks(player.getPlayer().getWorld())) {
			player.sendMessage(d.toString());
		}
	}
}
