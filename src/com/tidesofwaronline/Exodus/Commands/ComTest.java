package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import com.tidesofwaronline.Exodus.CustomEnchantment.CustomEnchantment;
import com.tidesofwaronline.Exodus.CustomItem.CustomItem;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler.Type;
import com.tidesofwaronline.Exodus.CustomItem.CustomItemHandler.Tier;

public class ComTest extends Command {

	public static CustomItem CUSTOM_ITEM;

	public ComTest(Player player, String[] args) {
		//Title
		//Tier + Slot
		//Damagemin - Damagemax + "Damage"
		//Enchantment
		//CustomEnchantments
		//Level requirement
		//Additional lore

		CUSTOM_ITEM = CustomItem.customItemBuilder()
				.withName("Golden Phoenix Sword")
				.withColor(ChatColor.GOLD)
				.withMaterial(Material.GOLD_SWORD)
				.withTier(Tier.Rare)
				.withType(Type.Sword)
				.withEnchantment(CustomEnchantment.POISON, 4)
				.withEnchantment(Enchantment.KNOCKBACK, 10)
				.withLore("This is a very long string of lore that hopefully will be word wrapped. We'll see!")
				.withDamageMin(4)
				.withDamageMax(12)
				.withGlow()
				.build();

		player.getInventory().addItem(CUSTOM_ITEM);
	}

}
