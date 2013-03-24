package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComPurge extends Command {
	
	public ComPurge(Plugin plugin, Player player, String arg)	{
		
		if (arg.isEmpty()) {
			player.sendMessage("You must specify a player name.");
			return;
		}
		
		Player p = Bukkit.getPlayer(arg);
		if (p == null) {
			player.sendMessage("Player " + arg + " doesn't exist or is offline!");
			return;
		}
		
		ExoPlayer exoplayer = PlayerIndex.getExodusPlayer(player);
		exoplayer.getPlayerConfig().loadDefaults();
		exoplayer.getPlayerConfig().save();
		p.setMaxHealth(20);
		p.setHealth(20);
		PlayerIndex.removePlayer(player.getName());
		new ExoPlayer(plugin, p);
		
		player.sendMessage("Player " + p.getName() + " has been purged.");
		if (!p.getName().equals(player.getName())) {
			p.sendMessage("Your stats have been purged!");
		}
	}
}
