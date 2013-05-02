package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.Guild;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComGuild extends Command {

	public ComGuild(Exodus plugin, Player player, String[] args) {

		ExoPlayer exop = PlayerIndex.getExodusPlayer(player);

		Guild guild = exop.getGuild();
		
		if (guild == null) {
			player.sendMessage("You are not in a guild!");
		}

		if (args.length == 0) {
			guild.info(player);
			return;
		}
		String command = args[0];

		if (command.equalsIgnoreCase("invite")) {
			ExoPlayer toInvite = PlayerIndex.getExodusPlayer(args[1]);

			if (toInvite == null) {
				player.sendMessage("Player not found!");
				return;
			} else {
				guild.invite(toInvite);
			}
		}

		else if (command.equalsIgnoreCase("leave")) {
			guild.removeMember(exop);
		}

		else if (command.equalsIgnoreCase("kick")) {
			ExoPlayer member = PlayerIndex.getExodusPlayer(args[1]);
			if (member != null) {
				guild.removeMember(member);
			} else {
				player.sendMessage("Player not found!");
			}
		}

		else if (command.equalsIgnoreCase("disband")) {

		} else {
			guild.info(player);
		}
	}
}
