package com.tidesofwaronline.Exodus;

public class DataStructure {

	private static String configFile = "plugins/Exodus/config.yml";
	private static String giftsFile = "plugins/Exodus/players/gifts.yml";
	
	private static String pluginFolder = "plugins/Exodus/";

	private static String guildsFolder = "plugins/Exodus/guilds/";
	private static String worldsFolder = "plugins/Exodus/worlds/";
	private static String instancesFolder = "plugins/Exodus/instances/";
	private static String playerConfigFolder = "plugins/Exodus/players/";
	private static String schematicsFolder = "plugins/Exodus/schematics/";
	private static String spawnersFolder = "plugins/Exodus/spawners/";

	private static String XMLFile = "plugins/Exodus/Exodus.xml";

	public static String getConfigFile() {
		return configFile;
	}

	public static String getGuildsFolder() {
		return guildsFolder;
	}

	public static String getInstancesFolder() {
		return instancesFolder;
	}

	public static String getPlayerConfigFolder() {
		return playerConfigFolder;
	}

	public static String getPluginFolder() {
		return pluginFolder;
	}

	public static String getSchematicsFolder() {
		return schematicsFolder;
	}

	public static String getSpawnersFolder() {
		return spawnersFolder;
	}

	public static String getWorldsFolder() {
		return worldsFolder;
	}

	public static String getXMLFile() {
		return XMLFile;
	}
	
	public static String getGiftsFile() {
		return giftsFile;
	}
}
