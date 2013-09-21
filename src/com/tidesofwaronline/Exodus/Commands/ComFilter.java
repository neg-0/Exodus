package com.tidesofwaronline.Exodus.Commands;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComFilter extends Command {
	
	ComFilter(CommandPackage comPackage) {
		ExoPlayer exoPlayer = comPackage.getExoPlayer();
		exoPlayer.setFilter(!exoPlayer.isFilter());
		if (exoPlayer.isFilter()) {
			exoPlayer.getPlayer().sendMessage("Exodus Item Filter Enabled");
		} else {
			exoPlayer.getPlayer().sendMessage("Exodus Item Filter Disabled");
		}
	}
}
