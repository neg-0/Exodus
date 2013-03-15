package com.tidesofwaronline.Exodus;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.tidesofwaronline.Exodus.Commands.CommandListener;
import com.tidesofwaronline.Exodus.Config.ConfigManager;
import com.tidesofwaronline.Exodus.CustomEnchantment.CustomEnchantment;
import com.tidesofwaronline.Exodus.CustomEntity.CustomEntityHandler;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler;
import com.tidesofwaronline.Exodus.GUI.GUI;
import com.tidesofwaronline.Exodus.Listeners.EntityListener;
import com.tidesofwaronline.Exodus.Listeners.LoginListener;
import com.tidesofwaronline.Exodus.Listeners.PlayerListener;
import com.tidesofwaronline.Exodus.Listeners.ProtocolListener;
import com.tidesofwaronline.Exodus.Listeners.TagAPIListener;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Player.PlayerIndex;

public class Exodus extends JavaPlugin {

	public static Logger logger = Logger.getLogger("minecraft");
	String version = null;
	Plugin dynmap;
	public boolean filter = true;

	@SuppressWarnings("unused")
	private ProtocolManager protocolManager;
	
	public static void main(String args[]) {
		new GUI();
	}

	@Override
	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
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
		
		//Register CustomEnchantments
		CustomEnchantment.enchants.put("Poison", CustomEnchantment.POISON);
		
		//Register Commands
		CommandListener comListener = new CommandListener(this);

		getCommand("stats").setExecutor(comListener);
		getCommand("exo").setExecutor(comListener);
		getCommand("exospawner").setExecutor(comListener);
		getCommand("party").setExecutor(comListener);

		//ProtocolLib
		if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
			new ProtocolListener(this);
			getLogger().log(Level.INFO, "Hooked into ProtocolLib!");
		}

		//Scan for players already in-game (in case of reload)
		final Player[] playerlist = Bukkit.getOnlinePlayers();
		for (final Player p : playerlist) {
			//ExodusPlayer exoplayer = 
			new ExoPlayer(this, p);
			//exoplayer.loadInventory();
		}
	}

	@Override
	public void onDisable() {
		//Scan for players already in-game (in case of reload)
		final Player[] playerlist = Bukkit.getOnlinePlayers();
		try {
			for (final Player p : playerlist) {
				PlayerIndex.getExodusPlayer(p).savePlayer();
			}
		} catch (final NullPointerException e) {
			logger.severe("Uh oh! " + e);
		}
		PlayerIndex.clear();
		CustomEntityHandler.dropLevels();
	}
}