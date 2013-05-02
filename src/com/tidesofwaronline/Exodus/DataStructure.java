package com.tidesofwaronline.Exodus;

public class DataStructure {
	
	
	private static String configFile = "plugins/Exodus/config.yml";
	private static String guildsFolder = "plugins/Exodus/guilds";
	private static String instancesFolder = "plugins/Exodus/instances";
	private static String playerConfigFolder = "plugins/Exodus/players";
	private static String schematicsFolder = "plugins/Exodus/schematics/";
	
	private static String spawnersFolder = "plugins/Exodus/spawners";
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
	public static String getSchematicsFolder() {
		return schematicsFolder;
	}
	public static String getSpawnersFolder() {
		return spawnersFolder;
	}
	public static String getXMLFile() {
		return XMLFile;
	}
	public static void setConfigFile(String configFile) {
		DataStructure.configFile = configFile;
	}
	public static void setGuildsFolder(String guildsFolder) {
		DataStructure.guildsFolder = guildsFolder;
	}
	public static void setInstancesFolder(String instancesFolder) {
		DataStructure.instancesFolder = instancesFolder;
	}
	public static void setPlayerConfigFolder(String playerConfigFolder) {
		DataStructure.playerConfigFolder = playerConfigFolder;
	}
	public static void setSchematicsFolder(String schematicsFolder) {
		DataStructure.schematicsFolder = schematicsFolder;
	}
	public static void setSpawnersFolder(String spawnersFolder) {
		DataStructure.spawnersFolder = spawnersFolder;
	}
	public static void setXMLFile(String xMLFile) {
		XMLFile = xMLFile;
	}
}
