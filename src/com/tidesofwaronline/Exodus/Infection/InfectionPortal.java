package com.tidesofwaronline.Exodus.Infection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.FilenameException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.tidesofwaronline.Exodus.DataStructure;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntity;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntityHandler;
import com.tidesofwaronline.Exodus.Util.TerrainManager;

public class InfectionPortal {

	private Location loc;
	private List<CustomEntity> mobs = new ArrayList<CustomEntity>();
	private List<Location> changedBlocks = new ArrayList<Location>();

	private Random ran = new Random();
	World world;
	TerrainManager tm;
	WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer()
			.getPluginManager().getPlugin("WorldEdit");
	File portalFile = new File(DataStructure.getSchematicsFolder() + "Portal.schematic");

	public InfectionPortal(World world, Location loc) {
		this.world = world;
		this.loc = loc;

		this.tm = new TerrainManager(worldEdit, world);
		createPortal();
		createMycel();
		//new Thread(new SpawnMobs(this));
	}

	public Location getLocation() {
		return loc;
	}

	private void createPortal() {

		Location pasteLoc = new Location(this.world, loc.getX() - 9,
				loc.getY(), loc.getZ() - 9);

		try {
			tm.loadSchematicNoAir(portalFile, pasteLoc);
		} catch (FilenameException e) {
			e.printStackTrace();
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		} catch (EmptyClipboardException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createMycel() {

	}

	private Entity spawnEntity(EntityType type, Location loc) {
		mobs.add(CustomEntity.spawn(type, loc,
				CustomEntityHandler.getChunkLevel(loc)));
		return this.loc.getWorld().spawnEntity(loc, type);
	}

	public List<Location> getChangedBlocks() {
		return changedBlocks;
	}

	public class SpawnMobs extends Thread {

		public boolean active = true;
		private double size = 5;
		private double maxSize = 25;
		private Location loc;

		public SpawnMobs(InfectionPortal infectionPortal) {
			this.loc = infectionPortal.loc;
		}

		public void run() {
			try {
				while (this.active) {
					sleep(5000);

					if (this.size < this.maxSize) {
						this.size += 0.1;
					}

					if (mobs.size() < Math.pow(this.size, 2) / 5) {

						int random = ran.nextInt(100);
						Location spawnLoc = this.loc.add(0, 2, 0);

						if (random < 5) {
							spawnEntity(EntityType.GHAST, spawnLoc);
						} else if (random < 10) {
							spawnEntity(EntityType.BLAZE, spawnLoc);
						} else if (random < 20) {
							spawnEntity(EntityType.ENDERMAN, spawnLoc);
						} else if (random < 40) {
							spawnEntity(EntityType.CREEPER, spawnLoc);
						} else if (random < 55) {
							spawnEntity(EntityType.SPIDER, spawnLoc);
						} else if (random < 75) {
							spawnEntity(EntityType.SKELETON, spawnLoc);
						} else {
							spawnEntity(EntityType.ZOMBIE, spawnLoc);
						}
					}
				}
			} catch (InterruptedException e) {

			}
		}
	}
}
