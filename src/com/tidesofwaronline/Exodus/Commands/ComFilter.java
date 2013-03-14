package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;

public class ComFilter extends Command {
	
	ComFilter(Exodus exodus, Player player) {
		exodus.filter = !exodus.filter;
		if (exodus.filter) {
			player.sendMessage("Exodus Item Filter Enabled");
		} else {
			player.sendMessage("Exodus Item Filter Disabled");
		}
	}
}
