package com.tidesofwaronline.Exodus.Worlds;

import java.util.HashMap;

import org.bukkit.World;

public class ExoWorld {
	
	World world;
	Thread timelock;
	static HashMap<World, ExoWorld> registry = new HashMap<World, ExoWorld>();
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public ExoWorld(World world) {
		this.world = world;
		register(this.world, this);
	}
	
	private void register(World w, ExoWorld e) {
		registry.put(w, e);
	}
	
	public static ExoWorld getExoWorld(World world) {
		return registry.get(world);
	}
	
	public void lockTime(long time) {
		timelock = new TimeLock(world, time);
		timelock.start();
	}
	
	public void unlockTime() {
		timelock.interrupt();
		timelock = null;
	}
	
	public class TimeLock extends Thread {

		World world;
		long time;
		boolean locked = true;

		public TimeLock(World world, long time) {
			this.world = world;
			this.time = time;
		}

		public void run() {
			
			while (locked) {
				world.setTime(time);
				try {
					Thread.sleep(50);
				}
				catch (InterruptedException e) {
					locked = false;
				}

				if (Thread.interrupted()) {
					locked = false;
				}
				
				if (!locked) {
					break;
				}
			}
		}
	}
}
