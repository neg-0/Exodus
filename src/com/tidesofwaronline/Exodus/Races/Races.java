package com.tidesofwaronline.Exodus.Races;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Races {
	
	public enum Race {
		VENTURI("Venturi", ChatColor.DARK_BLUE, Material.GOLD_BLOCK),
		DIAAB("Dia\'ab", ChatColor.DARK_RED, Material.WOOL, DyeColor.RED),
		NORDIC("Nordic", ChatColor.WHITE, Material.SNOW_BLOCK),
		ELVEN("Elven", ChatColor.YELLOW, Material.GLOWSTONE),
		ABRAXIAN("Abraxian", ChatColor.DARK_PURPLE, Material.NETHER_BRICK),
		NAGRATH("Nagrath", ChatColor.DARK_AQUA, Material.WOOL, DyeColor.BLUE),
		SCIENCE("Science", ChatColor.GOLD, Material.IRON_BLOCK),
		DWARVES("Dwarves", ChatColor.GRAY, Material.OBSIDIAN);
		
		final String name;
		final ChatColor color;
		final Material block;
		DyeColor woolColor = null;
		
		Race(String name, ChatColor color, Material block, DyeColor woolColor) {
			this.name = name;
			this.color = color;
			this.block = block;
			this.woolColor = woolColor;
		}
		
		Race(String name, ChatColor color, Material block) {
			this.name = name;
			this.color = color;
			this.block = block;
			this.woolColor = DyeColor.WHITE;
		}

		public final String getName() {
			return name;
		}

		public final ChatColor getColor() {
			return color;
		}

		public final Material getBlock() {
			return block;
		}
		
		@SuppressWarnings("deprecation")
		public final byte getBlockData() {
			if (woolColor != null) {
				return woolColor.getWoolData();
			} else {
				return 0;
			}
		}

		public final DyeColor getWoolColor() {
			return woolColor;
		}
	}
}
