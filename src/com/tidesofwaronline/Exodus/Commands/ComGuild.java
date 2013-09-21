package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Guilds.Guild;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComGuild extends Command {

	public ComGuild(CommandPackage comPackage) {

		ExoPlayer exoPlayer = comPackage.getExoPlayer();
		Player player = comPackage.getPlayer();
		String[] args = comPackage.getArgs();
		Guild guild = exoPlayer.getGuild();

		if (guild == null) {
			player.sendMessage("You are not in a guild!");
		} else if (args.length == 0) {
			player.sendMessage(guild.toString());
			guild.info(player);
			return;
		}

		if (args.length != 0) {
			String command = args[0];

			if (command.equalsIgnoreCase("invite")) {
				ExoPlayer toInvite = ExoPlayer.getExodusPlayer(args[1]);

				if (toInvite == null) {
					player.sendMessage("Player not found!");
					return;
				} else {
					guild.invite(toInvite);
				}
			}

			else if (command.equalsIgnoreCase("leave")) {
				guild.removeMember(exoPlayer);
			}

			else if (command.equalsIgnoreCase("kick")) {
				ExoPlayer member = ExoPlayer.getExodusPlayer(args[1]);
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
}
