package com.tidesofwaronline.Exodus.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Spell extends Ability {

		ItemStack spell;
		int cost;
		String name;
		Material icon;
		
		public static Spell FIREBALL = new Fireball(0);
		public static Spell RAISESKELETON = new RaiseSkeleton(1);
		public static Spell HEAL = new Heal(2);
		public static Spell HEALTOUCH = new HealTouch(3);
		public static Spell HEALTARGET = new HealTarget(4);
		
		public Spell() {
			
		}
		
		public Spell(Material mat, String name, String description, int level, int cost, Object effect) {
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
	
	
	public static boolean cast(int currentmana, Spell spell) {
		return false;
	}
}