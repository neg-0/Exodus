package com.tidesofwaronline.Exodus.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.kitteh.tag.TagAPI;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Config.PlayerConfig;
import com.tidesofwaronline.Exodus.DungeonBlocks.DBInventory;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Effects.PlayerLevelUpEffect;
import com.tidesofwaronline.Exodus.Guilds.Guild;
import com.tidesofwaronline.Exodus.Items.CustomItem;
import com.tidesofwaronline.Exodus.Items.CustomItemHandler;
import com.tidesofwaronline.Exodus.Parties.Party;
import com.tidesofwaronline.Exodus.Quests.Quest;
import com.tidesofwaronline.Exodus.Races.Races.Race;
import com.tidesofwaronline.Exodus.Spells.Spell;
import com.tidesofwaronline.Exodus.Spells.Spellbook;
import com.tidesofwaronline.Exodus.Util.IconMenu;
import com.tidesofwaronline.Exodus.Util.ItemUtil;

public class ExoPlayer {

	public enum ExoGameMode {
		BUILD, COMBAT, DBEDITOR;
	}
	
	private static Plugin plugin;
	private final Player player;
	private final PlayerConfig config = new PlayerConfig(this);
	private IconMenu statsMenu;

	private Economy econ = Exodus.getEcon();
	//Double Click Stuff
	private long sneakLastPressTime;
	private long inventoryLastPressTime;
	private boolean crouched;
	private static final long doublePressInterval = 250;
	private CustomItem pickeditem;

	private int slot;
	//Inventories
	private ExoGameMode exoGameMode = ExoGameMode.BUILD;
	private Inventory buildInventory;
	private CustomItem equippedmelee = new CustomItem(0);
	private CustomItem equippedranged = new CustomItem(0);
	private ArrayList<ItemStack> giftsList = new ArrayList<ItemStack>();

	private CustomItem equippedarrow = new CustomItem(Material.ARROW, 64);
	//Gameplay
	private int level;
	private Party party = null;
	private Guild guild = null;
	private Spellbook spellbook = new Spellbook(this);
	private ChatColor namecolor = ChatColor.WHITE;

	private ArrayList<Quest> quests = new ArrayList<Quest>();
	//Utility
	private boolean showSpawners = false;
	
	private boolean filter = true;
	//DungeonBlocks
	private DungeonBlock selectedBlock = null;
	private DungeonBlock editingBlock = null;

	private static Map<String, ExoPlayer> playerIndex = new HashMap<String, ExoPlayer>();

	public static void clear() {
		playerIndex.clear();
	}

	public static ExoPlayer getExodusPlayer(HumanEntity player) {
		return playerIndex.get(player.getName().toLowerCase());
	}

	public static ExoPlayer getExodusPlayer(Player player) {
		return playerIndex.get(player.getName().toLowerCase());
	}

	public static ExoPlayer getExodusPlayer(String player) {
		return playerIndex.get(player.toLowerCase());
	}

	public static void registerPlayer(ExoPlayer exodusplayer) {
		playerIndex.put(exodusplayer.getPlayer().getName().toLowerCase(),
				exodusplayer);
	}

	public static void removePlayer(String player) {
		playerIndex.remove(player.toLowerCase());
	}

	public ExoPlayer(final Plugin plugin, final Player player) {

		ExoPlayer.plugin = plugin;
		this.player = player;

		registerPlayer(this);

		buildInventory = Bukkit.createInventory(player, InventoryType.PLAYER);

		config.initialize();

		this.level = config.getConfig().getInt("level", 1);
		this.setEquippedmelee(config.getEquippedMelee());
		this.setEquippedranged(config.getEquippedRanged());

		createStatsMenu();
		recalcXPBar();
		
		setExoGameMode(ExoGameMode.valueOf(config().getString("ExoGameMode")));
	}

	public Quest addQuest(Quest quest) {
		quests.add(quest);
		quest.alert(this.getPlayer());
		return quest;
	}

	public boolean addToInventory(ItemStack i) {
		HashMap<Integer, ItemStack> extra = buildInventory.addItem(i);
		if (extra.size() != 0) {
		}
		return true;
	}

	public void addXP(final int amount) {
		setAttribute("xp", (Integer) getAttribute("xp") + amount);
		gainXPEvent();
	}

	private int calculateHealth() {
		return (int) Math.round((this.level * this.level) * 1.25 + 19);
	}

	public void cast(String spell, Action action) {
		player.sendMessage("Casting");
	}

	private void click(IconMenu.OptionClickEvent event) {
		final String icon = event.getName();

		if (icon.equalsIgnoreCase("increase rogue")) {
			increaseStat("stats.rogue", 1, false);
		}
		if (icon.equalsIgnoreCase("increase ranger")) {
			increaseStat("stats.ranger", 1, false);
		}
		if (icon.equalsIgnoreCase("increase warrior")) {
			increaseStat("stats.warrior", 1, false);
		}
		if (icon.equalsIgnoreCase("increase cleric")) {
			increaseStat("stats.cleric", 1, false);
		}
		if (icon.equalsIgnoreCase("increase mage")) {
			increaseStat("stats.mage", 1, false);
		}
		if (icon.equalsIgnoreCase("increase warlock")) {
			increaseStat("stats.warlock", 1, false);
		}

		refreshOptions();
		statsMenu.rebuildMenu();
	}

	public boolean combatSwitchCheck() {
		// Get current time in nano seconds.
		final long pressTime = System.currentTimeMillis();
		crouched = !crouched;

		if (crouched == false) {//if standing
			// If double click...
			if (pressTime - sneakLastPressTime <= doublePressInterval) {
				if (this.getExoGameMode() == ExoGameMode.BUILD) {
					this.setExoGameMode(ExoGameMode.COMBAT);
				} else if (this.getExoGameMode() == ExoGameMode.COMBAT) {
					this.setExoGameMode(ExoGameMode.BUILD);
				}
			} else {     // If not double click....
			}
			// record the last time the menu button was pressed.
			sneakLastPressTime = pressTime;
		}
		return true;
	}

	public YamlConfiguration config() {
		return this.config.getConfig();
	}

	private void createStatsMenu() {
		//if (this.statsMenu != null) {this.statsMenu.destroy();}
		//Set up stats menu
		statsMenu = new IconMenu(this.player.getDisplayName() + "'s Stats", 54,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(
							final IconMenu.OptionClickEvent event) {
						click(event);
						event.setWillClose(false);
					}
				}, plugin);
		refreshOptions();
	}

	@SuppressWarnings("deprecation")
	public void doWeaponSwap(final InventoryClickEvent event) {
		// Get current time in nano seconds.
		final long pressTime = System.currentTimeMillis();

		if (pressTime - inventoryLastPressTime <= doublePressInterval) {

			pickeditem = new CustomItem(event.getCursor());

			if (event.getSlot() == slot) {
				if (pickeditem.getTypeId() == 261) { //if it's a bow
					ItemStack old = getEquippedranged();
					setRanged(pickeditem);
					player.setItemOnCursor(new ItemStack(0));
					player.getInventory().setItem(slot, old);
					player.updateInventory();
				}

				boolean onlist = false;
				for (int i1 : ItemUtil.weapons) { //if it's a weapon
					if (pickeditem.getTypeId() == i1) {
						onlist = true;
					}
				}
				for (int i1 : ItemUtil.tools) { //if it's a tool
					if (pickeditem.getTypeId() == i1) {
						onlist = true;
					}
				}

				if (onlist == true) {
					ItemStack old = getEquippedmelee();
					setMelee(pickeditem);
					player.setItemOnCursor(new ItemStack(0));
					player.getInventory().setItem(slot, old);
					player.updateInventory();
				}
			}

		} else {
			slot = event.getSlot();
		}

		inventoryLastPressTime = pressTime;

	}

	public Economy econ() {
		return econ;
	}

	private void gainXPEvent() {
		final int xp = (Integer) getAttribute("xp");
		int level = config.getConfig().getInt("level");
		player.sendMessage("XP this level: "
				+ ExperienceHandler.XPThisLevel(xp, level) + "/"
				+ ExperienceHandler.totalXPThisLevel(level));
		while (ExperienceHandler.XPThisLevel(xp, level) >= ExperienceHandler
				.totalXPThisLevel(level)) {
			levelUp();
			level++;
		}
		recalcXPBar();
		config.set("xp", xp);
	}

	public Object getAttribute(final String string) {
		final Object toreturn = config.getConfig().get(string);
		if (toreturn != null) {
			return toreturn;
		}
		return 0;
	}

	public Inventory getBuildInventory() {
		return buildInventory;
	}

	public DungeonBlock getEditingBlock() {
		return editingBlock;
	}

	public CustomItem getEquippedarrow() {
		return equippedarrow;
	}

	public CustomItem getEquippedmelee() {
		return equippedmelee;
	}

	public CustomItem getEquippedranged() {
		return equippedranged;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public int getMeleeDamage() {
		if (this.getExoGameMode() == ExoGameMode.COMBAT) {
			ItemStack is = player.getInventory().getItemInHand();
			if (CustomItemHandler.isCustomItem(is)) {
				CustomItem i = new CustomItem(is);
				return i.getDamage();
			}
		}
		return 1;
	}

	public org.bukkit.ChatColor getNameColor() {
		return this.namecolor;
	}

	public Party getParty() {
		return this.party;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerConfig getPlayerConfig() {
		return this.config;
	}

	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public HashMap<Race, Integer> getRacesByRep() {
		HashMap<Race, Integer> races = new HashMap<Race, Integer>();

		races.put(Race.VENTURI,
				Integer.valueOf(getAttribute("repVenturi").toString()));
		races.put(Race.DIAAB,
				Integer.valueOf(getAttribute("repDia'ab").toString()));
		races.put(Race.NORDIC,
				Integer.valueOf(getAttribute("repNordic").toString()));
		races.put(Race.ELVEN,
				Integer.valueOf(getAttribute("repElven").toString()));
		races.put(Race.ABRAXIAN,
				Integer.valueOf(getAttribute("repAbraxian").toString()));
		races.put(Race.NAGRATH,
				Integer.valueOf(getAttribute("repNagrath").toString()));
		races.put(Race.SCIENCE,
				Integer.valueOf(getAttribute("repScience").toString()));
		races.put(Race.DWARVES,
				Integer.valueOf(getAttribute("repDwarves").toString()));

		List<Race> mapKeys = new ArrayList<Race>(races.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(races.values());
		Collections.sort(mapValues);
		Collections.reverse(mapValues);
		Collections.sort(mapKeys);
		Collections.reverse(mapKeys);

		LinkedHashMap<Race, Integer> sortedMap = new LinkedHashMap<Race, Integer>();

		Iterator<Integer> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator<Race> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = races.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					races.remove(key);
					mapKeys.remove(key);
					sortedMap.put((Race) key, (Integer) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	/* Disabled due to client not sending InventoryOpenEvent
	public boolean inventorySwitchCheck() {
		// Get current time in nano seconds.
		final long pressTime = System.currentTimeMillis();

		//if standing
		// If double click...
		if (pressTime - inventoryLastPressTime <= DOUBLE_PRESS_INTERVAL) {
			openStatsMenu();
		}
		// record the last time the menu button was pressed.
		inventoryLastPressTime = pressTime;

		return true;
	}*/

	public int[] getReputation() {
		int[] a = new int[8];
		a[0] = Integer.valueOf(getAttribute("repVenturi").toString());
		a[1] = Integer.valueOf(getAttribute("repDia'ab").toString());
		a[2] = Integer.valueOf(getAttribute("repNordic").toString());
		a[3] = Integer.valueOf(getAttribute("repElven").toString());
		a[4] = Integer.valueOf(getAttribute("repAbraxian").toString());
		a[5] = Integer.valueOf(getAttribute("repNagrath").toString());
		a[6] = Integer.valueOf(getAttribute("repScience").toString());
		a[7] = Integer.valueOf(getAttribute("repDwarves").toString());

		Arrays.sort(a);

		return a;
	}

	public Integer getReputation(Race race) {
		switch (race) {
		case VENTURI:
			return Integer.valueOf(getAttribute("repVenturi").toString());
		case DIAAB:
			return Integer.valueOf(getAttribute("repDia'ab").toString());
		case NORDIC:
			return Integer.valueOf(getAttribute("repNordic").toString());
		case ELVEN:
			return Integer.valueOf(getAttribute("repElven").toString());
		case ABRAXIAN:
			return Integer.valueOf(getAttribute("repAbraxian").toString());
		case NAGRATH:
			return Integer.valueOf(getAttribute("repNagrath").toString());
		case SCIENCE:
			return Integer.valueOf(getAttribute("repScience").toString());
		case DWARVES:
			return Integer.valueOf(getAttribute("repDwarves").toString());
		default:
			return null;
		}
	}

	public DungeonBlock getSelectedBlock() {
		return selectedBlock;
	}

	public Spell[] getSpells() {
		return null;
	}

	public int getStaminaMax() {
		return (config().getInt("stats.warrior") * 5)
				+ (config().getInt("stats.cleric") * 4)
				+ (config().getInt("stats.warlock") * 3);
	}

	public int getStaminaRegen() {
		return (config().getInt("stats.rogue") * 1)
				+ (config().getInt("stats.ranger") * 1)
				+ (config().getInt("stats.mage") * 1);

	}

	private void increaseStat(final String atr, final int amount,
			final boolean override) {
		if ((Integer) getAttribute(atr) < plugin.getConfig().getInt("statmax")) {
			if (override) {

				modifyAttribute(atr, amount);
			} else if ((Integer) getAttribute("skillpoints") > 0) {
				modifyAttribute(atr, amount);
				modifyAttribute("skillpoints", -1);
			}
		}
		config.save();
	}

	public void inventoryLoad() {
		buildInventory.setContents(config.loadInventory(player).getContents());
		if (this.getExoGameMode() == ExoGameMode.BUILD) {
			player.getInventory().setContents(buildInventory.getContents());
		}
	}

	public boolean inventorySave() {
		if (config.saveInventory(buildInventory)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFilter() {
		return filter;
	}

	public boolean isShowingSpawners() {
		return showSpawners;
	}

	public void levelUp() {

		this.level++;

		player.sendMessage("Congratulations! You've reached level " + level
				+ "!");

		new PlayerLevelUpEffect(this.player);

		setMaxHP(calculateHealth());

		setAttribute("level", this.level);
		modifyAttribute("skillpoints", 4);
		config.save();
	}

	public void modifyAttribute(final String path, final int value) {
		final int oldvalue = config.getConfig().getInt(path);
		final int newvalue = oldvalue + value;
		config.getConfig().set(path, newvalue);
	}

	public void onDamage(Entity damager) {
		CustomItem i = new CustomItem(player.getInventory().getItemInHand());
		if (CustomItemHandler.isCustomItem(i)) {
			i.onDamage(this.player, damager);
		}
	}

	public void onHit(LivingEntity entity) {
		CustomItem i = new CustomItem(player.getInventory().getItemInHand());
		if (CustomItemHandler.isCustomItem(i)) {
			i.onHit(this.player, entity);
		}
	}

	public void openStatsMenu() {
		refreshOptions();
		statsMenu.open(this.player);
	}

	public void recalcXPBar() {
		final int level = (Integer) getAttribute("level");
		if (player.getLevel() != level) {
			player.setLevel(level);
		}
		player.setExp(ExperienceHandler.getXPBarPercentage(this));
	}

	public void refreshOptions() {

		HashMap<Race, Integer> races = getRacesByRep();
		Race[] racelist = (Race[]) races.keySet().toArray(new Race[8]);

		statsMenu

				//Misc. Stats
				.setOption(
						//Stats Head
						11,
						new ItemStack(Material.SKULL_ITEM, 1, (short) 3),
						"Level " + getAttribute("level"),
						"Race: " + getAttribute("race"),
						"HP: " + player.getHealth() + "/"
								+ player.getMaxHealth(),
						"XP: "
								+ ExperienceHandler.XPThisLevel(
										(Integer) getAttribute("xp"),
										(Integer) getAttribute("level"))
								+ "/"
								+ ExperienceHandler
										.totalXPThisLevel((Integer) getAttribute("level")))
				.setOption(
						//Skillpoints
						16,
						new ItemStack(Material.BOOK,
								(Integer) getAttribute("skillpoints")),
						"Skill Points - " + getAttribute("skillpoints"),
						"Gain these as you level up,", "Spend them on stats!")

				//Skill Stats
				.setOption(
						//Warrior
						4,
						new ItemStack(Material.IRON_CHESTPLATE, (Integer) this
								.getAttribute("stats.warrior")), "Warrior",
						"Attune to the Warrior to", "hit harder with melee,",
						"equip better armor,", "and tank enemies!")
				.setOption(
						//Rogue
						13,
						new ItemStack(Material.IRON_SWORD, (Integer) this
								.getAttribute("stats.rogue")), "Rogue",
						"The Rogue is a master", "of melee damage and",
						"deception.")
				.setOption(
						//Ranger
						22,
						new ItemStack(Material.BOW, (Integer) this
								.getAttribute("stats.ranger")), "Ranger",
						"Rangers fight from a", "distance and can",
						"lay traps!")

				.setOption(
						//Cleric
						31,
						new ItemStack(Material.NETHER_STAR, (Integer) this
								.getAttribute("stats.cleric")), "Cleric",
						"The Cleric heals and", "cleanses players.")
				.setOption(
						//Mage
						40,
						new ItemStack(Material.BLAZE_POWDER, (Integer) this
								.getAttribute("stats.mage"), (short) 3),
						"Mage", "Mages unleash a fury of",
						"magic onto their enemies!")
				.setOption(
						//Warlock
						49,
						new ItemStack(Material.EYE_OF_ENDER, (Integer) this
								.getAttribute("stats.warlock")), "Warlock",
						"The Warlock practices dark", "magic and can raise",
						"enemies from the dead.")

				//Allegiance
				.setOption(
						10,
						new ItemStack(racelist[0].getBlock(), 1, racelist[0]
								.getBlockData()), racelist[0].getName(),
						"Reputation: " + races.get(racelist[0]))
				.setOption(
						19,
						new ItemStack(racelist[1].getBlock(), 1, racelist[1]
								.getBlockData()), racelist[1].getName(),
						"Reputation: " + races.get(racelist[1]))
				.setOption(
						28,
						new ItemStack(racelist[2].getBlock(), 1, racelist[2]
								.getBlockData()), racelist[2].getName(),
						"Reputation: " + races.get(racelist[2]))
				.setOption(
						37,
						new ItemStack(racelist[3].getBlock(), 1, racelist[3]
								.getBlockData()), racelist[3].getName(),
						"Reputation: " + races.get(racelist[3]));

		//Increase Stat Icon
		if ((Integer) getAttribute("skillpoints") > 0) {
			if ((Integer) getAttribute("stats.warrior") < plugin.getConfig()
					.getInt("statmax", 64)) {
				statsMenu
						.setOption(
								5,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Warrior",
								"Click to increase your",
								"Warrior to "
										+ ((Integer) this
												.getAttribute("stats.warrior") + 1));
			} else {
				statsMenu.setOption(5, new ItemStack(Material.AIR));
			}

			if ((Integer) getAttribute("stats.rogue") < plugin.getConfig()
					.getInt("statmax", 64)) {

				statsMenu
						.setOption(
								14,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Rogue",
								"Click to increase your",
								"Rogue to "
										+ ((Integer) this
												.getAttribute("stats.rogue") + 1));
			} else {
				statsMenu.setOption(14, new ItemStack(Material.AIR));
			}

			if ((Integer) getAttribute("stats.ranger") < plugin.getConfig()
					.getInt("statmax", 64)) {
				statsMenu
						.setOption(
								23,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Ranger",
								"Click to increase your",
								"Ranger to "
										+ ((Integer) this
												.getAttribute("stats.ranger") + 1));
			} else {
				statsMenu.setOption(23, new ItemStack(Material.AIR));
			}

			if ((Integer) getAttribute("stats.cleric") < plugin.getConfig()
					.getInt("statmax", 64)) {
				statsMenu
						.setOption(
								32,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Cleric",
								"Click to increase your",
								"Cleric to "
										+ ((Integer) this
												.getAttribute("stats.cleric") + 1));
			} else {
				statsMenu.setOption(32, new ItemStack(Material.AIR));
			}

			if ((Integer) getAttribute("stats.mage") < plugin.getConfig()
					.getInt("statmax", 64)) {
				statsMenu
						.setOption(
								41,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Mage",
								"Click to increase your",
								"Mage to "
										+ ((Integer) this
												.getAttribute("stats.mage") + 1));
			} else {
				statsMenu.setOption(41, new ItemStack(Material.AIR));
			}

			if ((Integer) getAttribute("stats.warlock") < plugin.getConfig()
					.getInt("statmax", 64)) {
				statsMenu
						.setOption(
								50,
								new ItemStack(Material.INK_SACK, 1, (short) 10),
								"Increase Warlock",
								"Click to increase your",
								"Warlock to "
										+ ((Integer) this
												.getAttribute("stats.warlock") + 1));
			} else {
				statsMenu.setOption(50, new ItemStack(Material.AIR));
			}
		} else { //If Skill Points 0, set all to air
			statsMenu.setOption(5, new ItemStack(Material.AIR))
					.setOption(14, new ItemStack(Material.AIR))
					.setOption(23, new ItemStack(Material.AIR))
					.setOption(32, new ItemStack(Material.AIR))
					.setOption(41, new ItemStack(Material.AIR))
					.setOption(50, new ItemStack(Material.AIR));
		}

		//Melee weapon
		if (getEquippedmelee() != null && getEquippedmelee().getTypeId() != 0) {
			statsMenu.setOption(20, getEquippedmelee());
		} else { //No melee equipped
			statsMenu.setOption(20, new ItemStack(Material.IRON_SWORD),
					"No Melee Weapon Equipped!");
		}

		//Ranged weapon
		if (getEquippedranged() != null && getEquippedranged().getTypeId() != 0) {
			statsMenu.setOption(29, getEquippedranged());
		} else {
			statsMenu.setOption(29, new ItemStack(Material.BOW),
					"No Ranged Weapon Equipped!");
		}
	}

	public boolean savePlayer() {
		if (inventorySave()) { //Saving inventory also saves config
			Logger.getLogger("minecraft").info("Player " + this.player.getName() + " saved");
			return true;
		} else {
			return false;
		}
	}

	public void setAttribute(final String path, final Object value) {
		config.getConfig().set(path, value);
	}

	public void setEditingBlock(DungeonBlock editingBlock) {
		this.editingBlock = editingBlock;
	}

	public void setEquippedarrow(CustomItem equippedarrow) {
		this.equippedarrow = equippedarrow;
	}

	public void setEquippedmelee(CustomItem equippedmelee) {
		this.equippedmelee = equippedmelee;
	}

	public void setEquippedranged(CustomItem equippedranged) {
		this.equippedranged = equippedranged;
	}
	
	public ExoGameMode getExoGameMode() {
		return this.exoGameMode;
	}
	
	public void setExoGameMode(ExoGameMode e) {
		//Save
		switch (this.getExoGameMode()) {
		case BUILD: {
			inventorySave();
			break;
		}
		case COMBAT: {
			sendMessage("EXITING COMBAT. Double-tap shift to enter.");
			break;
		}
		case DBEDITOR: {
			sendMessage("Exiting DungeonBlocks Editor Mode.");
			player.getInventory().setContents(buildInventory.getContents());
			if (player.getGameMode() == GameMode.SURVIVAL) {
				player.setAllowFlight(false);
				break;
			}
		}
		}
		
		//Load
		switch (e) {
		case BUILD: {
			exoGameMode = ExoGameMode.BUILD;
			player.getInventory().setContents(buildInventory.getContents());
			config().set("ExoGameMode", ExoGameMode.BUILD.toString());
			break;
		}
		case COMBAT: {
			exoGameMode = ExoGameMode.COMBAT;
			sendMessage("ENTERING COMBAT! Double-tap shift to exit.");
			player.getInventory().clear();
			
			player.getInventory().setItem(0, getEquippedmelee());
			player.getInventory().setItem(1, getEquippedranged());
			player.getInventory().setItem(2, getEquippedarrow());

			ArrayList<Spell> spells = spellbook.getSpells();

			for (int i = 0; i < 34 && i < spells.size(); i++) {
				player.getInventory().setItem(i + 3, spells.get(i).getItemStack());
			}
			config().set("ExoGameMode", ExoGameMode.COMBAT.toString());
			break;
		}
		case DBEDITOR: {
			exoGameMode = ExoGameMode.DBEDITOR;
			sendMessage("Entering DungeonBlocks Editor Mode.");
			inventorySave();
			player.getInventory().clear();

			player.getInventory().setContents(DBInventory.getInventory().getContents());
			player.setGameMode(GameMode.SURVIVAL);
			player.setAllowFlight(true);
			
			config().set("ExoGameMode", ExoGameMode.DBEDITOR.toString());
			break;
		}
		}
	}
	
	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public void setMaxHP(int value) {
		player.setMaxHealth(value);
		player.setHealth(value);
		config().set("maxhp", value);
		config().set("hp", value);
		config.save();
	}

	public void setMelee(CustomItem item) {
		this.setEquippedmelee(item);
		config.setEquippedItem("equippedmelee", item);
		player.sendMessage("Melee Weapon Equipped!");
		config.save();
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void setRace(String string) {
		if (plugin.getConfig().contains("races." + string)) {
			setAttribute("race", string);
			this.namecolor = ChatColor.getByChar(plugin.getConfig().getString(
					"races." + string + ".color", "f"));
			TagAPI.refreshPlayer(player);
			config.save();
			player.sendMessage("Your race is now the " + string);
		} else {
			player.sendMessage("Race " + string + " does not exist!");
		}

	}

	public void setRanged(final CustomItem item) {
		this.setEquippedranged(new CustomItem(item));
		config.setEquippedItem("equippedranged", item);
		player.sendMessage("Ranged Weapon Equipped!");
	}

	public void setSelectedBlock(DungeonBlock selectedBlock) {
		this.selectedBlock = selectedBlock;
	}

	public void setShowSpawners(boolean showSpawners) {
		this.showSpawners = showSpawners;
	}
	
	public void sendMessage(String s) {
		this.getPlayer().sendMessage(s);
	}
	
	public void sendMessage(String[] s) {
		this.getPlayer().sendMessage(s);
	}
	
	public static void giveItem(ExoPlayer ep, ItemStack... i) {
		if (ep == null || !ep.getPlayer().isOnline()) {
			
		} else if (ep.getPlayer().isOnline()) {
			HashMap<Integer, ItemStack> couldntGive = ep.getPlayer().getInventory().addItem(i);
			ep.giftsList.addAll(couldntGive.values());
		}
	}
}
