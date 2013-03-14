package com.tidesofwaronline.Exodus.Player;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;


public class PlayerIndex {

	private static Map<String, ExoPlayer> playerIndex = new HashMap<String, ExoPlayer>();

	public static void registerPlayer(ExoPlayer exodusplayer) {
		playerIndex.put(exodusplayer.getPlayer().getName(), exodusplayer);
	}

	public static ExoPlayer getExodusPlayer(Player player) {
		return playerIndex.get(player.getName());
	}
	
	public static ExoPlayer getExodusPlayer(String player) {
		return playerIndex.get(player);
	}

	public static ExoPlayer getExodusPlayer(HumanEntity player) {
		return playerIndex.get(player.getName());
	}
	
	public static void removePlayer(String player) {
		playerIndex.remove(player);
	}
	
	public static void clear() {
		playerIndex.clear();
	}
}
