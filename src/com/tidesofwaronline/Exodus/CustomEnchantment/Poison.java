package com.tidesofwaronline.Exodus.CustomEnchantment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends CustomEnchantment implements EnchantmentInterface{

	Player player;
	Player target;

	public void onHit(Player player, LivingEntity target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,
				30));
	}

	public String getName() {
		return "Poison";
	}
}