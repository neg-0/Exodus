package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExperienceHandler;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComStats extends Command {
	
	public ComStats(String[] args, Player player) {
		
		ExoPlayer exoplayer;
		
		if (player.getName() == args[1]) {
			exoplayer = PlayerIndex.getExodusPlayer(player);
		} else {
			exoplayer = PlayerIndex.getExodusPlayer(args[1]);
		}
		
		if (exoplayer == null) {
			player.sendMessage("Player " + args[1] + " doesn't exist or is offline!");
			return;
		}
		
			player.sendMessage("Showing stats of " + args[1]);
			player.sendMessage("Level: "
					+ exoplayer.getAttribute("level")
					+ " - XP: "
					+ ExperienceHandler.XPThisLevel(
							(Integer) exoplayer.getAttribute("xp"),
							(Integer) exoplayer.getAttribute("level"))
					+ "/"
					+ ExperienceHandler
							.totalXPThisLevel((Integer) exoplayer
									.getAttribute("level"))
					+ " - Skill Points: "
					+ exoplayer.getAttribute("skillpoints"));
			player.sendMessage("War: "
					+ exoplayer.getAttribute("stats.warrior") + " - Rog: "
					+ exoplayer.getAttribute("stats.rogue") + " - Ran: "
					+ exoplayer.getAttribute("stats.ranger") + " - Cle: "
					+ exoplayer.getAttribute("stats.cleric") + " - Mag: "
					+ exoplayer.getAttribute("stats.mage") + " - War: "
					+ exoplayer.getAttribute("stats.warlock"));
			return;
	}

}
