package com.tidesofwaronline.Exodus.CustomEnchantment;

import java.util.HashMap;

import org.apache.commons.lang.WordUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Util.RomanNumeral;

public class CustomEnchantment {

	public static HashMap<String, CustomEnchantment> enchants = new HashMap<String, CustomEnchantment>();

	public static CustomEnchantment POISON = register(new Poison());
	public static CustomEnchantment FREEZE = register(new Freeze());
	public static CustomEnchantment FIREAURA = register(new FireAura());

	public CustomEnchantment ce;
	public Enchantment e;
	public int level = 1;

	CustomEnchantment() {
	}

	public CustomEnchantment(CustomEnchantment ce) {
		this.ce = ce;
	}

	public CustomEnchantment(CustomEnchantment ce, int level) {
		this.ce = ce;
		this.level = level;
	}

	public CustomEnchantment(Enchantment e) {
		this.e = e;
	}

	public CustomEnchantment(Enchantment e, int level) {
		this.e = e;
		this.level = level;
	}

	public static CustomEnchantment valueOf(String s) {
		String[] name = s.split("-");
		int lvl = 1;
		if (name.length > 1 && !name[1].isEmpty()) {
			lvl = Integer.valueOf(name[1]);
		}

		Enchantment en = Enchantment.getByName(name[0]);
		CustomEnchantment ce = enchants.get(name[0]);

		if (en != null) {
			return new CustomEnchantment(en, lvl);
		} else if (ce != null) {
			return new CustomEnchantment(ce, lvl);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		if (ce != null) {
			return ce.getName() + "-" + level;
		} else if (e != null) {
			return e.getName() + "-" + level;
		} else {
			return null;
		}
	}

	public String display() {
		if (ce != null) {
			return ce.getName() + " " + RomanNumeral.convertToRoman(level);
		} else if (e != null) {
			return WordUtils.capitalizeFully(e.getName().replace("_", " "))
					+ " " + RomanNumeral.convertToRoman(level);
		} else {
			return null;
		}
	}

	public String getName() {
		if (e != null)
			return e.getName().replace("_", " ");
		if (ce != null)
			return ce.getName();
		return null;
	}

	public void onHit(Player player, LivingEntity target) {
	}

	public Enchantment getEnchantment() {
		if (e != null)
			return this.e;
		else
			return null;
	}

	public CustomEnchantment getCustomEnchantment() {
		if (ce != null)
			return this.ce;
		else
			return null;
	}

	public int getLevel() {
		return this.level;
	}

	private static CustomEnchantment register(CustomEnchantment ce) {
		enchants.put(ce.getName().toUpperCase().replace(" ", ""), ce);
		return ce;
	}
}
