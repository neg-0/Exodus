package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ComHeal extends Command {

	public ComHeal(Player player, String[] arg) {
		if (arg.length > 1) {
			Player p = Bukkit.getPlayerExact(arg[1]);
			if (p != null) {
				p.setHealth(p.getMaxHealth());
				player.sendMessage("Healed player " + p.getDisplayName());
			} else {
				player.sendMessage("Player " + arg[1]
						+ " does not exist or is offline!");
			}

		} else {
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.sendMessage("You've been healed!");
		}
	}
}
