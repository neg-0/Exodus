package com.tidesofwaronline.Exodus.DungeonBlocks.Actions;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class MobSpawner extends DungeonBlock {
	
	EntityType spawnMob = EntityType.ZOMBIE;

	public void onTrigger() {
		this.getBlock().getWorld().spawnEntity(this.getLocation(), spawnMob);
	}

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock,
			Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		// TODO Auto-generated method stub
		
	}
}
