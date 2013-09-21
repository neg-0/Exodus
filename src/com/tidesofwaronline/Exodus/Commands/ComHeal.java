package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ComHeal extends Command {

	public ComHeal(CommandPackage comPackage) {
		
		Player player = comPackage.getPlayer();
		String[] args = comPackage.getArgs();
		
		if (args.length > 0) {
			Player p = Bukkit.getPlayerExact(args[0]);
			if (p != null) {
				p.setHealth(p.getMaxHealth());
				player.sendMessage("Healed player " + p.getDisplayName());
			} else {
				player.sendMessage("Player " + args[0]
						+ " does not exist or is offline!");
			}

		} else {
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.sendMessage("You've been healed!");
		}
	}
}
