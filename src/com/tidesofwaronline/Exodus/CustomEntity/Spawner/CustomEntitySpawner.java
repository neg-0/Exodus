package com.tidesofwaronline.Exodus.CustomEntity.Spawner;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;

public class CustomEntitySpawner implements Listener{
	
	Location loc;
	double range = 6;
	Block block;
	LivingEntity entity;
	CustomEntity custentity;
	
	public CustomEntitySpawner(Block block) {
		this.loc = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
		this.block = block;
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() == SpawnReason.SPAWNER) {
			//if near
			if (event.getLocation().distance(this.loc) < this.range) {
				captureCreature(event.getEntity());
			}
		}
	}

	private void captureCreature(LivingEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public void setRange(int range) {
		this.range = range;
	}	
	
	public Location getLocation() {
		return this.loc;
	}

	public byte getData() {
		return block.getData();
	}
}
