package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Joiner;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class CommandPackage {
	
	private Plugin plugin;
	private Player player;
	private ExoPlayer exoPlayer;
	private String[] args = null;
	
	public CommandPackage(Plugin plugin, Player player, ExoPlayer exoPlayer, String... args) {
		this.plugin = plugin;
		this.player = player;
		this.exoPlayer = exoPlayer;
		this.args = args;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public Player getPlayer() {
		return player;
	}

	public ExoPlayer getExoPlayer() {
		return exoPlayer;
	}

	public String[] getArgs() {
		return args;
	}
	
	public String[] getCommaSeparatedArguments() {
		String[] args = Joiner.on(" ").join(this.args).split(",");
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].trim();
		}
		
		return args;
	}
	
	public String[] getCommaSeparatedArguments(String s) {
		String[] args = Joiner.on(" ").join(this.args).split(",");
		args[0] = args[0].replaceFirst(s, "");
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].trim();
		}
		
		return args;
	}
	
	
	public String getArgumentsString() {
		return Joiner.on(" ").join(args);
	}
}
