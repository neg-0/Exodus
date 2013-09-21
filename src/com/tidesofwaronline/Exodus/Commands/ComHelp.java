package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ComHelp extends Command {
	
	public ComHelp(CommandPackage comPackage) {
		Plugin plugin = comPackage.getPlugin();
		Player player = comPackage.getPlayer();
		
		player.sendMessage("Exodus version "
				+ plugin.getDescription().getVersion());
	}
}
