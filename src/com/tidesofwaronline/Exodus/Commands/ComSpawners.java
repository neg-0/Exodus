package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.CustomEntity.Spawner.CustomEntitySpawnerIndex;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class ComSpawners extends Command {

	public ComSpawners(Player player) {
		if (!PlayerIndex.getExodusPlayer(player).showSpawners) {
			CustomEntitySpawnerIndex.showSpawners(player);
		} else {
			CustomEntitySpawnerIndex.hideSpawners(player);
		}
	}

}
