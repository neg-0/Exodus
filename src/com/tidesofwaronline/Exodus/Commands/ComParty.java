package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComParty extends Command {

	public ComParty(CommandPackage comPackage) {

		Player player = comPackage.getPlayer();
		ExoPlayer exoPlayer = comPackage.getExoPlayer();
		String[] args = comPackage.getArgs();
		Party party = exoPlayer.getParty();

		if (party == null && args.length == 0) {
			player.sendMessage("You are not in a party!");
		} else if (party != null && args.length == 0) {
			party.info(player);
			return;
		} else if (args.length != 0) {
			String command = args[0];

			if (command.equalsIgnoreCase("invite")) {
				if (args.length != 2) {
					player.sendMessage("Invalid number of arguments.");
					return;
				}
				ExoPlayer toInvite = ExoPlayer.getExodusPlayer(args[1]);

				if (toInvite == null) {
					player.sendMessage("Player not found!");
					return;
				} else {
					if (party == null) {
						party = new Party(exoPlayer);
						party.invite(toInvite);
						player.sendMessage("Party invite sent to " + toInvite.getPlayer().getName());
						return;
					} else if (party.isLeader(exoPlayer)) {
						party.invite(toInvite);
						player.sendMessage("Party invite sent to " + toInvite.getPlayer().getName());
						return;
					} else if (!party.isPrivate()) {
						party.invite(toInvite);
						player.sendMessage("Party invite sent to " + toInvite.getPlayer().getName());
						return;
					} else {
						player.sendMessage("You do not have permission to invite players!");
					}
					
				}
			} else if (command.equalsIgnoreCase("accept")) {
				for (Party p : Party.getParties()) {
					if (p.hasInvite(exoPlayer)) {
						p.partyAccept(exoPlayer);
						return;
					}
				}
				player.sendMessage("You have no party invites.");
			}

			else if (command.equalsIgnoreCase("leave")) {
				party.removeMember(exoPlayer);
				return;
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
				party.disband();
			}
			
			else if (command.equalsIgnoreCase("private")) {
				if (party.isLeader(exoPlayer)) {
					party.setPrivate(true);
					player.sendMessage("Party set to private. Type \"/party public\" to make the party public.");
					return;
				}
				
				player.sendMessage("You must be the party leader to set private/public.");
			} else if (command.equalsIgnoreCase("public")) {
				if (party.isLeader(exoPlayer)) {
					party.setPrivate(false);
					player.sendMessage("Party set to open. Type \"/party private\" to make the party private.");
					return;
				}
				
				player.sendMessage("You must be the party leader to set private/public.");
			}
		}
	}
}
