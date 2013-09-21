package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComPurge extends Command {
	
	public ComPurge(CommandPackage comPackage)	{
		
		Plugin plugin = comPackage.getPlugin();
		Player player = comPackage.getPlayer();
		String[] args = comPackage.getArgs();
		
		if (args.length == 0) {
			player.sendMessage("You must specify a player name.");
			return;
		}
		
		Player p = Bukkit.getPlayer(args[0]);
		if (p == null) {
			player.sendMessage("Player " + args[0] + " doesn't exist or is offline!");
			return;
		}
		
		ExoPlayer exoplayer = ExoPlayer.getExodusPlayer(player);
		exoplayer.getPlayerConfig().loadDefaults();
		exoplayer.getPlayerConfig().save();
		p.setMaxHealth(20);
		p.setHealth(20);
		ExoPlayer.removePlayer(player.getName());
		new ExoPlayer(plugin, p);
		
		player.sendMessage("Player " + p.getName() + " has been purged.");
		if (!p.getName().equals(player.getName())) {
			p.sendMessage("Your stats have been purged!");
		}
	}
}
