package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ComHelp extends Command {
	
	public ComHelp(Plugin plugin, final Player player) {
		player.sendMessage("Exodus version "
				+ plugin.getDescription().getVersion());
	}

}
