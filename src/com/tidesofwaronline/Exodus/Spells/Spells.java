package com.tidesofwaronline.Exodus.Spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Spells {
	
	public enum Spell {
		//ENUM(icon, name, level, mana, damage, heal, knockback, description)
		TEST(Material.REDSTONE_TORCH_OFF, "Test Spell", "A test spell", 1, 5, healSelf(5)),
		HEAL(Material.NETHER_STAR, "Heal", "Heals you!", 5, 5, healSelf(5));
		
		ItemStack spell;
		int cost;
		String name;
		
		Spell(Material mat, String name, String description, int level, int cost, Object effect) {
			spell = new ItemStack(mat);
			this.cost = cost;
			this.name = name;
			ItemMeta im = spell.getItemMeta();
			im.setDisplayName(name);
			
			List<String> lore = new ArrayList<String>();
			
			lore.add("Stamina Cost: " + cost);
			lore.add(description);
			
			im.setLore(lore);
			spell.setItemMeta(im);
			
		}
		
		public ItemStack getItemStack() {
			return spell;
		}
		
		public int getCost() {
			return cost;
		}
		
		public String getName() {
			return name;
		}
		
		public Spell lookUp(String name) {
			if (name != null) {
				for (Spell s: Spell.values()) {
					if (name.equalsIgnoreCase(s.getName())) {
						return s;
					}
				}
			}
			return null;
		}
	}

	public static boolean healSelf(int amount) {
		return true;
	}
	
	public static boolean cast(int currentmana, Spell spell) {
		return false;
	}
}