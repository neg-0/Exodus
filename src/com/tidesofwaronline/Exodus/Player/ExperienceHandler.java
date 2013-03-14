package com.tidesofwaronline.Exodus.Player;

import java.math.BigDecimal;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.tidesofwaronline.Exodus.Exodus;

public class ExperienceHandler {

	public static int maxLevel;
	public static double xpmultiplier = 1.0;
	public static boolean donordoublexpweekend;
	public static double donorbonus;

	private final Exodus plugin;

	public ExperienceHandler(final Exodus exodus) {
		this.plugin = exodus;
		maxLevel = plugin.getConfig().getInt("maxlevel");
		xpmultiplier = plugin.getConfig().getDouble("xpmultiplier");
		donordoublexpweekend = plugin.getConfig().getBoolean(
				"donordoublexpweekend");
	}

	public static void changeExp(final PlayerExpChangeEvent event) {
		int exp = (int) getMobXP(event.getPlayer().getLevel(), event.getAmount());
		event.setAmount(0);
		final Player player = event.getPlayer();
		final ExoPlayer exoplayer = PlayerIndex.getExodusPlayer(player);
		player.sendMessage("Gained " + exp + " XP");
		exoplayer.addXP(exp);
	}

	public static float getXPBarPercentage(ExoPlayer player) {
		int xp = (Integer) player.getAttribute("xp");
		int level = (Integer) player.getAttribute("level");
		BigDecimal perc = new BigDecimal((float) XPThisLevel(xp, level)
				/ (float) totalXPThisLevel(level));
		BigDecimal rounded = perc.setScale(2, BigDecimal.ROUND_HALF_UP);
		return rounded.floatValue();
	}

	public static double getXPToNextLevel(final int xp, final int level) {
		return totalXPThisLevel(level) - xp;
	}

	public static int XPThisLevel(int xp, int level) {
		return xp - getTotalXP(level - 1);
	}

	public static int totalXPThisLevel(final int level) {
		return (48 + ((int) xpmultiplier * MobXP(level) * level * level));
	}

	public static int getTotalXP(final int level) {
		int totalxp = 0;
		for (int i = 1; i <= level; i++) {
			totalxp += totalXPThisLevel(i);
		}
		return totalxp;
	}

	public static int MobXP(final int level) {
		return level * 6;
	}

	// Mob XP Functions (including Con Colors)
	// Colors will be numbers:
	//  {grey = 0, green = 1, yellow = 2, orange = 3, red = 4, skull = 5}
	// NOTE: skull = red when working with anything OTHER than mobs!

	static double getConColor(int playerlvl, int moblvl) {
		if (playerlvl + 5 <= moblvl) {
			if (playerlvl + 10 <= moblvl) {
				return 5;
			} else {
				return 4;
			}
		} else {
			switch (moblvl - playerlvl) {
			case 4:
			case 3:
				return 3;
			case 2:
			case 1:
			case 0:
			case -1:
			case -2:
				return 2;
			default:
				// More adv formula for grey/green lvls:
				if (playerlvl <= 5) {
					return 1; //All others are green.
				} else {
					if (playerlvl <= 39) {
						if (moblvl <= (playerlvl - 5 - Math
								.floor(playerlvl / 10))) {
							// Its below or equal to the 'grey level':
							return 0;
						} else {
							return 1;
						}
					} else {
						//player over lvl 39:
						if (moblvl <= (playerlvl - 1 - Math
								.floor(playerlvl / 5))) {
							return 0;
						} else {
							return 1;
						}
					}
				}
			}
		}
	}

	static int getZD(int lvl) {
		if (lvl <= 7) {
			return 5;
		}
		if (lvl <= 9) {
			return 6;
		}
		if (lvl <= 11) {
			return 7;
		}
		if (lvl <= 15) {
			return 8;
		}
		if (lvl <= 19) {
			return 9;
		}
		if (lvl <= 29) {
			return 11;
		}
		if (lvl <= 39) {
			return 12;
		}
		if (lvl <= 44) {
			return 13;
		}
		if (lvl <= 49) {
			return 14;
		}
		if (lvl <= 54) {
			return 15;
		}
		if (lvl <= 59) {
			return 16;
		} else {
			return 17; // Approx.
		}
	}

	static double getMobXP(int playerlvl, int moblvl) {
		if (moblvl >= playerlvl) { //Higher level mob
			double temp = (((playerlvl * 6) + 48))
					* (1 + (0.05 * (moblvl - playerlvl)));
			double tempcap = (((playerlvl * 6) + 48) * 1.2)/6;
			if (temp > tempcap) {
				return Math.floor(tempcap);
			} else {
				return Math.floor(temp);
			}
		} else { //Lower level mob
			if (getConColor(playerlvl, moblvl) == 0) { //Grey
				return 0;
			} else {
				return Math.floor(((playerlvl * 6) + 48)
						* (1 - (playerlvl - moblvl) / getZD(playerlvl))/6);
			}
		}
	}

	static double getEliteMobXP(int playerlvl, int moblvl) {
		return getMobXP(playerlvl, moblvl) * 2;
	}

	// Rested Bonus:
	// Restedness is double XP, but if we only have part restedness we must split the XP:

	static double getMobXPFull(int playerlvl, int moblvl, boolean elite, int rest) {
		// rest = how much XP is left before restedness wears off:
		double temp = 0;
		if (elite) {
			temp = getEliteMobXP(playerlvl, moblvl);
		} else {
			temp = getMobXP(playerlvl, moblvl);
		}
		// Now to apply restedness.  temp = actual XP.
		// If restedness is 0...
		if (rest == 0) {
			return temp;
		} else {
			if (rest >= temp) {
				return temp * 2;
			} else {
				//Restedness is partially covering the XP gained.
				// XP = rest + (AXP - (rest / 2))
				return rest + (temp - (rest / 2));
			}
		}
	}

	// Party Mob XP:
	static double getPartyMobXPFull(int playerlvl, int highestlvl, int sumlvls,
			int moblvl, boolean elite, int rest) {
		double temp = getMobXPFull(highestlvl, moblvl, elite, 0);
		// temp = XP from soloing via highest lvl...
		temp = temp * playerlvl / sumlvls;
		if (rest == 0) {
			return temp;
		} else {
			if (rest >= temp) {
				return temp * 2;
			} else {
				//Restedness is partially covering the XP gained.
				// XP = rest + (AXP - (rest / 2))
				return rest + (temp - (rest / 2));
			}
		}
	}

}
