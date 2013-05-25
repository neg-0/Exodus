package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.earth2me.essentials.DescParseTickFormat;
import com.tidesofwaronline.Exodus.Worlds.ExoWorld;

public class ComTime extends Command {

	public ComTime(Player p, String[] args) {

		long time = p.getWorld().getTime();

		if (args.length == 1) {
			p.sendMessage(DescParseTickFormat.format(time));
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("lock")) {
			ExoWorld.getExoWorld(p.getWorld()).lockTime(time);
			p.sendMessage("Time locked at " + DescParseTickFormat.format(time));
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("unlock")) {
			ExoWorld.getExoWorld(p.getWorld()).unlockTime();
			p.sendMessage("Time unlocked at "
					+ DescParseTickFormat.format(time));
		}
	}
}