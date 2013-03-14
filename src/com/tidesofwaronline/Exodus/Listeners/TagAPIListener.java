package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class TagAPIListener implements Listener {
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		PlayerIndex.getExodusPlayer(event.getNamedPlayer()).getNameColor();
	}
}
