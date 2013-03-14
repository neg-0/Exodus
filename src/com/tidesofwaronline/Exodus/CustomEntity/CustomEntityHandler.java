package com.tidesofwaronline.Exodus.CustomEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CustomEntityHandler {

	static HashMap<UUID, CustomEntity> CEIndex = new HashMap<UUID, CustomEntity>();
	static HashMap<Integer, Integer> chunkLevels = new HashMap<Integer, Integer>();
	static double[] distances = new double[8];
	static Plugin plugin;

	public CustomEntityHandler(Plugin plugin) {
		CustomEntityHandler.plugin = plugin;
		refresh();
	}

	public static void register(LivingEntity entity, UUID uuid, CustomEntity ce) {
		setMetadata(entity, "CustomEntity", ce, plugin);
		CEIndex.put(uuid, ce);
	}

	public static void unRegister(LivingEntity key) {
		if (CEIndex.containsKey(key)) {
			CEIndex.remove(key);
		}
	}

	public static int getIndexSize() {
		return CEIndex.size();
	}

	public static void setMetadata(Player player, String key, Object value,
			Plugin plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public static void setMetadata(LivingEntity entity, String key,
			Object value, Plugin plugin) {
		entity.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public static Object getMetadata(Player player, String key, Plugin plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return value.value();
			}
		}
		return null;
	}

	public static Object getMetadata(Entity entity, String key, Plugin plugin) {
		List<MetadataValue> values = entity.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return value.value();
			}
		}
		return null;
	}

	public static CustomEntity getCustomEntity(Entity entity) {
		return CEIndex.get(entity.getUniqueId());

	}

	public static void refresh() {
		for (World w : plugin.getServer().getWorlds()) {
			for (LivingEntity l : w.getLivingEntities()) {
				if (l.getType() != EntityType.PLAYER) {
					if (!hasCustomEntity(l.getUniqueId())) {
						new CustomEntity(l, plugin);
					}
				}
			}
		}
	}

	public static boolean hasCustomEntity(UUID id) {
		return CEIndex.containsKey(id);
	}

	public static boolean isMonster(EntityType entity) {
		switch (entity) {
		case BLAZE:
		case CAVE_SPIDER:
		case CREEPER:
		case GHAST:
		case MAGMA_CUBE:
		case SILVERFISH:
		case SKELETON:
		case SLIME:
		case SPIDER:
		case WITCH:
		case WITHER:
		case ZOMBIE:
			return true;
		}
		return false;
	}

	public static int chunkLeveler(World world, int hash, int x, int z) {
		//Venturi -975 -2900
		//Elven -3000, -6300
		//Desert -4800 -800
		//Nord -3400 7000
		//Abrax 2400 -5500
		//Underwater 3100 2700
		//Science 6900 4200
		//Dwarven 6200 -800
		Location l1 = new Location(world, -975, 64, -2900);
		Location l2 = new Location(world, -3000, 64, -6300);
		Location l3 = new Location(world, -4800, 64, -800);
		Location l4 = new Location(world, -3400, 64, 7000);
		Location l5 = new Location(world, 2400, 64, -5500);
		Location l6 = new Location(world, 3100, 64, 2700);
		Location l7 = new Location(world, 6900, 64, 4200);
		Location l8 = new Location(world, 6200, 64, -800);

		distances[0] = getDistance(l1.getX(), x, l1.getX(), z);
		distances[1] = getDistance(l2.getX(), x, l2.getZ(), z);
		distances[2] = getDistance(l3.getX(), x, l3.getZ(), z);
		distances[3] = getDistance(l4.getX(), x, l4.getZ(), z);
		distances[4] = getDistance(l5.getX(), x, l5.getZ(), z);
		distances[5] = getDistance(l6.getX(), x, l6.getZ(), z);
		distances[6] = getDistance(l7.getX(), x, l7.getZ(), z);
		distances[7] = getDistance(l8.getX(), x, l8.getZ(), z);

		Arrays.sort(distances);

		int level = (int) Math.ceil(distances[0] / 75);

		chunkLevels.put(hash, level);

		return level;
	}

	public static int getChunkLevel(LivingEntity entity) {

		int level = 1;
		int hash = entity.getLocation().getChunk().hashCode();
		if (chunkLevels.containsKey(hash)) {
			level = chunkLevels.get(hash);
		} else {
			level = chunkLeveler(entity.getWorld(), hash, entity.getLocation()
					.getChunk().getX() * 16, entity.getLocation().getChunk()
					.getZ() * 16);
		}

		if (level > 50) {
			level = 50;
		}

		return level;
	}

	public static double getDistance(double x1, double x2, double z1, double z2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1));
	}

	public static void dropLevels() {
		chunkLevels.clear();
	}
}
