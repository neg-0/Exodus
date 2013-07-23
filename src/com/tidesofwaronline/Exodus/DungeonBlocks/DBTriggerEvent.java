package com.tidesofwaronline.Exodus.DungeonBlocks;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


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
