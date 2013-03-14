package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.Party;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComParty extends Command {

	public ComParty(Exodus plugin, Player player, String[] args) {

		ExoPlayer exop = PlayerIndex.getExodusPlayer(player);

		Party party = exop.getParty();

		if (args.length == 0) {
			party.info(player);
			return;
		}
		String command = args[0];

		if (command.equalsIgnoreCase("invite")) {
			ExoPlayer toInvite = PlayerIndex.getExodusPlayer(args[1]);

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
			ExoPlayer member = PlayerIndex.getExodusPlayer(args[1]);
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
