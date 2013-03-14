package com.tidesofwaronline.Exodus.Util;

import org.bukkit.Material;

public class MaterialUtil {
	
	public enum RepairMats {
			IRON_INGOT(Material.IRON_INGOT, 4),
			GOLD_INGOT(Material.GOLD_INGOT, 12),
			DIAMOND(Material.DIAMOND, 20),
			OBSIDIAN(Material.OBSIDIAN, 8);
			
			Material mat;
			int amt;
			
			RepairMats(Material mat, int amt) {
				this.mat = mat;
				this.amt = amt;
			}
			
			public static int getAmt(RepairMats rm) {
				return rm.amt;
			}
	}	
}
