package com.tidesofwaronline.Exodus.Commands;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComFilter extends Command {
	
	ComFilter(Exodus exodus, ExoPlayer player) {
		player.setFilter(!player.isFilter());
		if (player.isFilter()) {
			player.getPlayer().sendMessage("Exodus Item Filter Enabled");
		} else {
			player.getPlayer().sendMessage("Exodus Item Filter Disabled");
		}
	}
}
