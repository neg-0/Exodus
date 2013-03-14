package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntityHandler;

public class EntityListener implements Listener {

	Exodus plugin;
	
	public EntityListener(Exodus plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	//Capture Spawns
	public void mobSpawn(CreatureSpawnEvent event) {
		if (CustomEntityHandler.isMonster(event.getEntityType())) {
			CustomEntity mob = new CustomEntity(event.getEntity(), plugin);
			CustomEntityHandler.register(event.getEntity(), event.getEntity().getUniqueId(), mob);
		}
	}

	@EventHandler
	//Capture Death
	public void mobDeath(EntityDeathEvent event) {
		event.setDroppedExp(0);
		if (CustomEntityHandler.getCustomEntity(event.getEntity()) != null) {
			CustomEntityHandler.getCustomEntity(event.getEntity()).onDeath(event);
		}
	}
}
