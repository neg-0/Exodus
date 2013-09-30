package com.tidesofwaronline.Exodus.CustomEntity.Spawner;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;

public class CustomEntitySpawnerIndex {

	static HashMap<Location, CustomEntitySpawner> spawners = new HashMap<Location, CustomEntitySpawner>();

	public static void register(Block block) {
		if (block.getType() == Material.MOB_SPAWNER) {
			spawners.put(block.getLocation(),
					new CustomEntitySpawner(block));
		}
	}

	public static void unRegister(Block block) {
		spawners.remove(block);
	}

	public static CustomEntitySpawner getSpawner(Block block) {
		return spawners.get(block.getLocation());
	}

	public static void leftClick(Player player, Block block) {
		//Info Menu
		if (spawners.containsKey(block.getLocation())) {
			infoMenu(player, block);
		} else {
			player.sendMessage("Not a registered spawner! Right click to register!");
		}
	}

	@SuppressWarnings("deprecation")
	private static void infoMenu(Player player, Block block) {
		CreatureSpawner cs = (CreatureSpawner) block.getState();
		player.sendMessage("Spawner Info");
		player.sendMessage("Location: " + block.getLocation().getBlockX()
				+ ", " + block.getLocation().getBlockY() + ", "
				+ block.getLocation().getBlockZ());
		player.sendMessage("Mob Type: " + cs.getSpawnedType().getName());
	}

	public static void rightClick(Player player, Block block) {
		Logger.getLogger("Minecraft").info("Registering MobSpawner!");
		register(block);
	}

	@SuppressWarnings("deprecation")
	public static void showSpawners(Player player) {
		for (CustomEntitySpawner c : spawners.values()) {
			player.sendBlockChange(c.getLocation(), Material.MOB_SPAWNER,
					c.getData());
		}
	}

	public static void hideSpawners(Player player) {
		// TODO Auto-generated method stub

	}

}
