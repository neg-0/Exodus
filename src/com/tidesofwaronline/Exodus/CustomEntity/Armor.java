package com.tidesofwaronline.Exodus.CustomEntity;

import org.bukkit.Material;

public enum Armor {

	LEATHER_HELMET(2, Material.LEATHER_HELMET), LEATHER_CHESTPLATE(6, Material.LEATHER_CHESTPLATE), LEATHER_LEGGINGS(4, Material.LEATHER_LEGGINGS), LEATHER_BOOTS(2, Material.LEATHER_BOOTS),
	GOLD_HELMET(3, Material.GOLD_HELMET), GOLD_CHESTPLATE(10, Material.GOLD_CHESTPLATE), GOLD_LEGGINGS(6, Material.GOLD_LEGGINGS), GOLD_BOOTS(2, Material.GOLD_BOOTS),
	CHAIN_HELMET(4, Material.CHAINMAIL_HELMET), CHAIN_CHESTPLATE(8, Material.CHAINMAIL_CHESTPLATE), CHAIN_LEGGINGS(8, Material.CHAINMAIL_LEGGINGS), CHAIN_BOOTS(3, Material.CHAINMAIL_BOOTS),
	IRON_HELMET(5, Material.IRON_HELMET), IRON_CHESTPLATE(12, Material.IRON_CHESTPLATE), IRON_LEGGINGS(10, Material.IRON_LEGGINGS), IRON_BOOTS(5, Material.IRON_BOOTS),
	DIAMOND_HELMET(8, Material.DIAMOND_HELMET), DIAMOND_CHESTPLATE(14, Material.DIAMOND_CHESTPLATE), DIAMOND_LEGGINGS(12, Material.DIAMOND_LEGGINGS), DIAMOND_BOOTS(6, Material.DIAMOND_BOOTS),
	AIR(0, Material.AIR);

	int points;
	Material mat;

	Armor(int points, Material mat) {
		this.points = points;
		this.mat = mat;
	}

	public int getPoints() {
		return points * 2;
	}

	public static Armor getBestChestplate(int max) {
		if (DIAMOND_CHESTPLATE.points <= max) {
			return DIAMOND_CHESTPLATE;
		}
		if (IRON_CHESTPLATE.points <= max) {
			return IRON_CHESTPLATE;
		}
		if (GOLD_CHESTPLATE.points <= max) {
			return GOLD_CHESTPLATE;
		}
		if (CHAIN_CHESTPLATE.points <= max) {
			return CHAIN_CHESTPLATE;
		}
		if (LEATHER_CHESTPLATE.points <= max) {
			return LEATHER_CHESTPLATE;
		}
		return AIR;
	}

	public static Armor getBestLeggings(int max) {
		if (DIAMOND_LEGGINGS.points <= max) {
			return DIAMOND_LEGGINGS;
		}
		if (IRON_LEGGINGS.points <= max) {
			return IRON_LEGGINGS;
		}
		if (GOLD_LEGGINGS.points <= max) {
			return GOLD_LEGGINGS;
		}
		if (CHAIN_LEGGINGS.points <= max) {
			return CHAIN_LEGGINGS;
		}
		if (LEATHER_LEGGINGS.points <= max) {
			return LEATHER_LEGGINGS;
		}
		return AIR;
	}

	public static Armor getBestHelmet(int max) {
		if (DIAMOND_HELMET.points <= max) {
			return DIAMOND_HELMET;
		}
		if (IRON_HELMET.points <= max) {
			return IRON_HELMET;
		}
		if (GOLD_HELMET.points <= max) {
			return GOLD_HELMET;
		}
		if (CHAIN_HELMET.points <= max) {
			return CHAIN_HELMET;
		}
		if (LEATHER_HELMET.points <= max) {
			return LEATHER_HELMET;
		}
		return AIR;
	}

	public static Armor getBestBoots(int max) {
		if (DIAMOND_BOOTS.points <= max) {
			return DIAMOND_BOOTS;
		}
		if (IRON_BOOTS.points <= max) {
			return IRON_BOOTS;
		}
		if (GOLD_BOOTS.points <= max) {
			return GOLD_BOOTS;
		}
		if (CHAIN_BOOTS.points <= max) {
			return CHAIN_BOOTS;
		}
		if (LEATHER_BOOTS.points <= max) {
			return LEATHER_BOOTS;
		}
		return AIR;
	}
}
