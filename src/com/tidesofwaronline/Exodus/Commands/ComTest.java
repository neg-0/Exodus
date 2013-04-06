package com.tidesofwaronline.Exodus.Commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.tidesofwaronline.Exodus.CustomItem.CustomItem;

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

		/*CUSTOM_ITEM = CustomItem.customItemBuilder()
				.withName("Golden Phoenix Sword")
				.withColor(ChatColor.GOLD)
				.withMaterial(Material.GOLD_SWORD)
				.withTier(Tier.RARE)
				.withType(Type.SWORD)
				.withEnchantment(CustomEnchantment.POISON, 4)
				.withEnchantment(Enchantment.KNOCKBACK, 10)
				.withLore("This is a very long string of lore that hopefully will be word wrapped. We'll see!")
				.withDamageMin(4)
				.withDamageMax(12)
				.withGlow()
				.build();
		*/
		//player.getInventory().addItem(CUSTOM_ITEM);
		//player.getInventory().addItem(CustomItemHandler.getDefinedItem("Sword of Storms"));
		Block block = player.getTargetBlock(null, 100);
		Location loc = player.getTargetBlock(null, 100).getLocation();
		if (block.getType().isSolid()) {
			FallingBlock fb = loc.getWorld().spawnFallingBlock(loc,
					block.getType(), (byte) 0);
			block.setTypeId(0);
			fb.setVelocity(new Vector(0, 0.5, 0));
		}
	}

}
