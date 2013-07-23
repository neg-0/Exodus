package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;


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
	
	enum Mode {
		PLAYER,
		PARTY,
		MOB,
		ENTITY;
	}
	
	enum Reset {
		TIMED,
		SINGLE,
		INFINITE;
	}
}
