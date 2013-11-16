package com.tidesofwaronline.Exodus.Config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.ExoPlayer.ExoGameMode;
import com.tidesofwaronline.Exodus.Util.DataStructure;
import com.tidesofwaronline.Exodus.Util.MessageUtil;

public class PlayerConfig {

	private YamlConfiguration playerConfig;
	private YamlConfiguration inventoryConfig;
	private File configFile;
	private File inventoryFile;
	private String playerName;
	private ExoPlayer exoPlayer;

	public PlayerConfig(ExoPlayer exoPlayer) {
		this.exoPlayer = exoPlayer;
	}

	public void initialize() {
		this.playerName = exoPlayer.getPlayer().getName();
		load();
	}

	public void dispose() {
		playerConfig = null;
		configFile = null;
	}

	public void load() {
		configFile = new File(DataStructure.getPlayerConfigFolder() + playerName
				+ "/config.yml");
		inventoryFile = new File(DataStructure.getPlayerConfigFolder() + playerName
				+ "/inventory.yml");

		playerConfig = YamlConfiguration.loadConfiguration(configFile);
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);

		playerConfig.options().indent(2);
		playerConfig.options().header(playerName + " Player Configuation");

		inventoryConfig.options().indent(2);
		inventoryConfig.options().header(playerName + " Player Inventory");

		if (!configFile.exists()) {
			loadDefaults();
			MessageUtil.log("Creating new config .yml for " + playerName);
		}
	}

	public void loadDefaults() {

		set("maxhp", 20);
		set("hp", 20);
		set("xp", 0);
		set("level", 1);
		set("ExoGameMode", ExoGameMode.BUILD);

		set("stats.warrior", 4);
		set("stats.rogue", 4);
		set("stats.ranger", 4);
		set("stats.cleric", 4);
		set("stats.mage", 4);
		set("stats.warlock", 4);
		set("skillpoints", 8);

		set("race", "None");

		set("repVenturi", 0);
		set("repDia'ab", 0);
		set("repNordic", 0);
		set("repElven", 0);
		set("repAbraxian", 0);
		set("repNagrath", 0);
		set("repScience", 0);
		set("repDwarves", 0);

		set("spelllist", null);

		save();
	}

	public void set(String key, Object obj) {
		playerConfig.set(key, obj);
	}

	public Boolean save() {
		try {
			playerConfig.save(configFile);
			inventoryConfig.save(inventoryFile);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public YamlConfiguration getConfig() {
		return playerConfig;
	}

	public YamlConfiguration getInventoryConfig() {
		return inventoryConfig;
	}

	public Boolean saveInventory(Inventory inv) {

		if (inv == null) {
			Exodus.logger.info("Inventory Null");
			return false;
		}

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				if (CustomItemHandler.isCustomItem(item)) {
					inventoryConfig.createSection("custom." + i,
							new CustomItem(item).serialize());
				} else {
					inventoryConfig.set("inv." + i, inv.getItem(i));
				}
			} else {
				inventoryConfig.set("custom." + i, null);
				inventoryConfig.set("inv." + i, null);
			}
		}

		if (save()) {
			return true;
		} else {
			return false;
		}
	}

	public Inventory loadInventory(Player player) {		//Load Bottom Inventory from file

		Inventory playerInv = Bukkit.createInventory(player,
				InventoryType.PLAYER);

		for (int i = 0; i < 36; i++) {
			if (inventoryConfig.contains("inv." + String.valueOf(i))) {

				ItemStack item = inventoryConfig.getItemStack("inv."
						+ String.valueOf(i));
				playerInv.setItem(i, item);

			}
		}

		for (int i = 0; i < 36; i++) {
			if (inventoryConfig.contains("custom." + i)) {
				CustomItem item = new CustomItem(inventoryConfig
						.getConfigurationSection("custom." + i).getValues(true));
				playerInv.setItem(i, item);
			}
		}
		return playerInv;
	}

	public CustomItem getEquippedMelee() {
		if (!playerConfig.contains("equippedmelee")) {
			return null;
		}
		ConfigurationSection cs = playerConfig.getConfigurationSection("equippedmelee");
		
		if (cs == null) {
			System.out.println("cs is null");
		}
		if (cs != null && cs.isConfigurationSection("equippedmelee")) {
			System.out.println("2");
		}
		
			return new CustomItem(cs.getValues(true));
	}

	public void setEquippedItem(String path, CustomItem item) {
		playerConfig.createSection(path, item.serialize());
	}
	
	public CustomItem getEquippedRanged() {
		if (!playerConfig.contains("equippedranged")) {
			return null;
		}
		ConfigurationSection cs = playerConfig
				.getConfigurationSection("equippedranged");
		if (cs != null) {
			return new CustomItem(cs.getValues(true));
		} else {
			return null;
		}
	}

	public CustomItem getEquippedArrow() {
		if (!playerConfig.contains("equippedarrow"))
			return null;
		return new CustomItem(playerConfig.getConfigurationSection(
				"equippedarrow").getValues(true));
	}
}
