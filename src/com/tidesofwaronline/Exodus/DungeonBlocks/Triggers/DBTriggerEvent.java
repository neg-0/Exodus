package com.tidesofwaronline.Exodus.DungeonBlocks.Triggers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;

public class DBTriggerEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    
    public DBTriggerEvent(DungeonBlock db) {
    	
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
