package com.tidesofwaronline.Exodus.Spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Spells.Spell;

public class Spellbook {
	
	private ArrayList<Spell> spells = new ArrayList<Spell>();
	private ArrayList<Spell> equippedSpells = new ArrayList<Spell>();
	private Inventory spellbook;
	ExoPlayer exoPlayer;

	public Spellbook(ExoPlayer exoPlayer) {
		spellbook = Bukkit.createInventory(exoPlayer.getPlayer(), 36);
		this.exoPlayer = exoPlayer;
		build();
	}

	public ArrayList<Spell> getSpells() {
		return spells;
	}
	
	public Inventory getSpellBook() {
		return spellbook;
	}
	
	private void build() {
		spellbook.setItem(0, exoPlayer.getEquippedmelee());
		spellbook.setItem(1, exoPlayer.getEquippedranged());
		spellbook.setItem(2, exoPlayer.getEquippedarrow());
		for (int i = 0; i < equippedSpells.size(); i++) {
			spellbook.addItem(equippedSpells.get(i).getItemStack());
		}
		for (int i = 0; i < spells.size(); i++) {
			spellbook.addItem(spells.get(i).getItemStack());
		}
	}
	
	public void addSpell(Spell spell) {
		spells.add(spell);
		build();
	}
}
