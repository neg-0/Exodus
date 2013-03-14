package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

public class ComTexture extends Command {

	ComTexture(Player player, String[] args) {
		
		if (args.length < 2) {
			player.sendMessage("Available packs: doku, coterie");
			return;
		}
		
		if (args[1].equalsIgnoreCase("doku")) {
			player.setTexturePack("http://files.enjin.com/140690/texture_packs/MiniDoku146.zip");
		} else if (args[1].equalsIgnoreCase("coterie")) {
			player.setTexturePack("http://files.enjin.com/140690/texture_packs/CoterieCraft_3.zip");
		} else {
			player.sendMessage("Available packs: doku, coterie");
		}
	}
}
