package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class TagAPIListener implements Listener {
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		ExoPlayer.getExodusPlayer(event.getNamedPlayer()).getNameColor();
	}
}
