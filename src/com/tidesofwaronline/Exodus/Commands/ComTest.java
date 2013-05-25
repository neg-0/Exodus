package com.tidesofwaronline.Exodus.Commands;

import com.tidesofwaronline.Exodus.ScoreboardHandler;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComTest extends Command {

	public ComTest(ExoPlayer player, String[] args) {
		new ScoreboardHandler(player.getPlayer());
	}
}
