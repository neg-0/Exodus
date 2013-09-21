package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.CustomEntity.CustomEntityHandler;

public class ComInfo extends Command {
	
	Player player;
		
	public ComInfo(CommandPackage comPackage) {
		
		player = comPackage.getPlayer();

		if (comPackage.getArgs()[0].equalsIgnoreCase("chunk")) {
			chunk();
		}
	}
	
	public void chunk() {
		player.sendMessage("Chunk level: " + CustomEntityHandler.getChunkLevel((LivingEntity) player));
		player.sendMessage("Hash: " + player.getLocation().getChunk().hashCode());
	}
}
