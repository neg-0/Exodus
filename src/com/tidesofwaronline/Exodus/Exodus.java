package com.tidesofwaronline.Exodus;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tidesofwaronline.Exodus.Chests.Chest;
import com.tidesofwaronline.Exodus.Commands.CommandListener;
import com.tidesofwaronline.Exodus.Config.ConfigManager;
import com.tidesofwaronline.Exodus.Config.XMLLoader;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntityHandler;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler;
import com.tidesofwaronline.Exodus.Listeners.EntityListener;
import com.tidesofwaronline.Exodus.Listeners.LoginListener;
import com.tidesofwaronline.Exodus.Listeners.PlayerListener;
import com.tidesofwaronline.Exodus.Listeners.ProtocolListener;
import com.tidesofwaronline.Exodus.Listeners.TagAPIListener;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Worlds.ExoWorld;

public class Exodus extends JavaPlugin {

	public static Logger logger = Logger.getLogger("minecraft");
	public static Economy econ = null;
	public static boolean debugMode = false;

	public static void main(String args[]) throws Exception {
		debugMode = true;
		new XMLLoader();
	}

	@SuppressWarnings("unused")
	private ProtocolManager protocolManager;

	@Override
	public void onDisable() {
		//Scan for players already in-game (in case of reload)
		final Player[] playerlist = Bukkit.getOnlinePlayers();
		try {
			for (final Player p : playerlist) {
				ExoPlayer.getExodusPlayer(p).savePlayer();
			}
		} catch (final NullPointerException e) {
			logger.severe("Uh oh! " + e);
		}
		ExoPlayer.clear();
		CustomEntityHandler.dropLevels();
	}

	@Override
	public void onEnable() {

		new ConfigManager(this);
		PluginManager pm = getServer().getPluginManager();

		// Register Events
		pm.registerEvents(new LoginListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new EntityListener(this), this);

		//TagAPI
		Plugin tagapi = pm.getPlugin("TagAPI");
		if (tagapi != null) {
			logger.info("Hooking into TagAPI");
			pm.registerEvents(new TagAPIListener(), this);
		}

		new CustomEntityHandler(this);
		new CustomItemHandler();

		//Start the XML Loader
		new XMLLoader();

		//Register Commands
		CommandListener comListener = new CommandListener(this);

		getCommand("stats").setExecutor(comListener);
		getCommand("exo").setExecutor(comListener);
		getCommand("exospawner").setExecutor(comListener);
		getCommand("party").setExecutor(comListener);
		getCommand("guild").setExecutor(comListener);

		//ProtocolLib
		if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
			new ProtocolListener(this);
			getLogger().log(Level.INFO, "Hooked into ProtocolLib!");
		}

		//Vault
		if (!setupEconomy()) {
			logger.severe(String.format(
					"[%s] - Disabled due to no Vault dependency found!",
					getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		//Scan for players already in-game (in case of reload)
		final Player[] playerlist = Bukkit.getOnlinePlayers();
		for (final Player p : playerlist) {
			//ExodusPlayer exoplayer = 
			new ExoPlayer(this, p);
			//exoplayer.loadInventory();
		}
		
		//Create ExoWorlds
		for (World w : Bukkit.getWorlds()) {
			new ExoWorld(w);
		}
		
		//Add recipes
		Chest.addLockRecipes();
	}

	@Override
	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public static Economy getEcon() {
		return econ;
	}
}
