package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;

@DungeonBlockInfo(description = "Pulses when entities come within range.", hasInput = false, hasOutput = true, material = "LAPIS_LAZULI_BLOCK", name = "Proximity Trigger")
public class ProximityTrigger extends DungeonBlock implements Runnable {
	
	List<EntityType> entityTypes = new ArrayList<EntityType>();
	int proximity = 10;
	Mode mode = Mode.DISABLED;

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			
	        for(Entity en : getLocation().getWorld().getEntities()) {
	            if(entityTypes.contains(en.getType())) {
	                double distance = en.getLocation().distance(getLocation());
	                if(distance < proximity) {
	                	for (DungeonBlock d : this.getLinkedBlocks()) {
	                		d.onTrigger(new DungeonBlockEvent(this, en));
	                	}
	                }
	            }
	        }
		} catch (InterruptedException e) {
			
		}
		
	}
	
	
	
	enum Mode {
		DISABLED,
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

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
