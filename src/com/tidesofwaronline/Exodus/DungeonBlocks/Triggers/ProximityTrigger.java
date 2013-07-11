package com.tidesofwaronline.Exodus.DungeonBlocks.Triggers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ProximityTrigger extends DungeonBlock implements Runnable {
	
	List<EntityType> entityTypes = new ArrayList<EntityType>();
	int proximity = 10;

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			
			Block block = this.getBlock();
	        for(Entity en : block.getWorld().getEntities()) {
	            if(entityTypes.contains(en.getType())) {
	                double distance = en.getLocation().distance(block.getLocation());
	                if(distance < proximity) {
	                	for (DungeonBlock d : this.getLinkedBlocks()) {
	                		d.onTrigger();
	                	}
	                }
	            }
	        }
		} catch (InterruptedException e) {
			
		}
		
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

	@Override
	public void onTrigger() {
		// TODO Auto-generated method stub
		
	}

}
