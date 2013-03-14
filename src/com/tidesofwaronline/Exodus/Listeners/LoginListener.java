package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class LoginListener implements Listener {

	private final Exodus plugin;

	public LoginListener(final Exodus exodus) {
		this.plugin = exodus;
	}

	@EventHandler
	public void normalLogin(final PlayerLoginEvent event) {
		//Create new player instance
		new ExoPlayer(plugin, event.getPlayer());
	}
	
	@EventHandler
	public void playerJoin(final PlayerJoinEvent event) {
		PlayerIndex.getExodusPlayer(event.getPlayer()).inventoryLoad();
	}

	@EventHandler
	public void normalLogout(final PlayerQuitEvent event) {
		//Save player
		PlayerIndex.getExodusPlayer(event.getPlayer()).savePlayer();
	}
}
