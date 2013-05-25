package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComParty extends Command {

	public ComParty(Exodus plugin, Player player, String[] args) {

		ExoPlayer exop = ExoPlayer.getExodusPlayer(player);

		Party party = exop.getParty();

		if (party == null) {
			player.sendMessage("You are not in a party!");
		} else if (args.length == 0) {
			party.info(player);
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
					party.invite(toInvite);
				}
			}

			else if (command.equalsIgnoreCase("leave")) {
				party.removeMember(exop);
			}

			else if (command.equalsIgnoreCase("kick")) {
				ExoPlayer member = ExoPlayer.getExodusPlayer(args[1]);
				if (member != null) {
					party.removeMember(member);
				} else {
					player.sendMessage("Player not found!");
				}
			}

			else if (command.equalsIgnoreCase("disband")) {

			} else {
				party.info(player);
			}
		}
	}
}
