package com.tidesofwaronline.Exodus.CustomEntity;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class CustomEntitySpawnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String message;
 
    public CustomEntitySpawnEvent(String example) {
        message = example;
    }
 
    public String getMessage() {
        return message;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}