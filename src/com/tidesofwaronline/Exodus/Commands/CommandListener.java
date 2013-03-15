package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class CommandListener implements CommandExecutor {

	private final Exodus plugin;

	public CommandListener(final Exodus plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		
		if (!(sender instanceof Player)) return false;
		
		final Player player = (Player) sender;

		if (command.getName().equalsIgnoreCase("stats")) {
			stats(player);
		}
		if (command.getName().equalsIgnoreCase("exo")) {
			exo(player, args);
		}
		if (command.getName().equalsIgnoreCase("party")) {
			new ComParty(plugin, player, args);
		}

		return true;
	}

	public void stats(final Player player) {
		PlayerIndex.getExodusPlayer(player).openStatsMenu();
	}

	private void exo(final Player player, final String[] args) {

		if (args.length == 0) {
			new ComHelp(plugin, player);
			return;
		}
		
		String command = args[0];
		
		if (command.equalsIgnoreCase("stats")) {
			new ComStats(args, player);
		}
		
		if (command.equalsIgnoreCase("setrace")) {
			setrace(player, args[1]);
		}

		if (command.equalsIgnoreCase("test")) {
			new ComTest(player, args);
		}
		
		if (command.equalsIgnoreCase("PURGE")) {
			new ComPurge(plugin, player, args[1]);
		}
		
		if (command.equalsIgnoreCase("info")) {
			new ComInfo(player, args);
		}
		
		if (command.equalsIgnoreCase("spawners")) {
			new ComSpawners(player);
		}
		
		if (command.equalsIgnoreCase("heal")) {
			new ComHeal(player, args);
		}
		
		if (command.equalsIgnoreCase("save")) {
			new ComSave(player);
		}
		
		if (command.equalsIgnoreCase("load")) {
			new ComLoad(player);
		}
		
		if (command.equalsIgnoreCase("filter")) {
			new ComFilter(plugin, player);
		}
		
		if (command.equalsIgnoreCase("text")) {
			new ComTexture(player, args);
		}
	}
	
	private void setrace(Player player, String string) {
		PlayerIndex.getExodusPlayer(player).setRace(string);
	}
	
	public boolean isPlayer(final CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			return false;
		}
	}
}