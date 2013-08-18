package com.tidesofwaronline.Exodus.Config;

import org.bukkit.plugin.Plugin;

import com.tidesofwaronline.Exodus.Exodus;

public class ConfigManager {
	
	private Plugin plugin;
	


		
	public ConfigManager(Exodus exodus) {
		this.plugin = exodus;
		
		//Save default config if not already there
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
	}
}