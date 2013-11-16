package com.tidesofwaronline.Exodus.CustomEntity;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class CustomEntity {

	public String name;
	public int level = 0;
	public LivingEntity entity;
	public Random random = new Random();
	@SuppressWarnings("unused")
	private Plugin plugin;	

	public CustomEntity(LivingEntity entity, Plugin plugin) {
		this.entity = entity;
		this.level = getZoneLevel();
		this.plugin = plugin;
		
		CustomEntityHandler.register(this.entity, this.entity.getUniqueId(),
				this);
		if (entity.getType() == EntityType.ZOMBIE
				|| entity.getType() == EntityType.SKELETON) {
			this.setLeveledArmor();
		}
		this.autoLevel();
	}

	public static CustomEntity spawn(EntityType type, Location loc, int level) {
		Entity entity = loc.getWorld().spawnEntity(loc, type);
		CustomEntityHandler.getCustomEntity(entity).setLevel(level);
		return CustomEntityHandler.getCustomEntity(entity);
	}

	public CustomEntity autoLevel() {
		// Level Health
		int health = (int) Math.round(Math.pow(level, 2) * 1.1 + 19);
		this.setMaxHealth(health);
		this.setHealth(health);
		return this;
	}

	public int getZoneLevel() {
		return 7;
		//return CustomEntityHandler.getChunkLevel(this.entity);
	}

	public void setLevel(int l) {
		this.level = l;
		autoLevel();
	}

	public void setHealth(int health) {
		entity.setHealth(health);
	}

	public void setMaxHealth(int health) {
		entity.setMaxHealth(health);
	}

	public double getHealth() {
		return entity.getHealth();
	}

	public int getLevel() {
		return level;
	}

	public EntityType getType() {
		return entity.getType();
	}

	public EntityEquipment getEquipment() {
		return entity.getEquipment();
	}

	public void setLeveledArmor() {
		EntityEquipment equipment = this.getEquipment();
		int count = level;
		equipment.setChestplate(new ItemStack(Armor
				.getBestChestplate(count / 2).mat));
		count -= Armor.getBestChestplate(count).points;
		equipment.setLeggings(new ItemStack(
				Armor.getBestLeggings(count / 2).mat));
		count -= Armor.getBestLeggings(count).points;
		equipment.setHelmet(new ItemStack(Armor.getBestHelmet(count).mat));
		count -= Armor.getBestHelmet(count).points;
		equipment.setBoots(new ItemStack(Armor.getBestBoots(count).mat));
		count -= Armor.getBestBoots(count).points;

		equipment.setChestplateDropChance(0F);
		equipment.setLeggingsDropChance(0F);
		equipment.setHelmetDropChance(0F);
		equipment.setBootsDropChance(0F);
	}

	@SuppressWarnings("unused")
	public void onDeath(EntityDeathEvent event) {
		CustomEntityHandler.unRegister(this.entity);
		Location loc = this.entity.getLocation();
		dropExp(entity, level * 6);
	}

	public void dropExp(LivingEntity entity, int amount) {
		Location loc = entity.getLocation();
		ExperienceOrb orb = (ExperienceOrb) entity.getWorld().spawnEntity(loc,
				EntityType.EXPERIENCE_ORB);
		orb.setExperience(level);

		//Old EXP Drop method
		/*for (double x = 10; x > 0; x--){		
			int e = (int) Math.round(amount*Math.pow(0.5041379, x));
			if (e > 0) {
				ExperienceOrb orb = (ExperienceOrb) entity.getWorld().spawnEntity(loc, EntityType.EXPERIENCE_ORB);
				orb.setExperience(e);
				orb.setMetadata("level", new FixedMetadataValue(plugin, this.level));
			}
		}*/
	}

	public void destroy() {
		entity.remove();
		CustomEntityHandler.unRegister(this.entity);
		this.name = null;
		this.entity = null;
		this.random = null;
		this.plugin = null;
	}

	public void setVelocity(Vector velocity) {
		entity.setVelocity(velocity);
	}
}
