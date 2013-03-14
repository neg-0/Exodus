package com.tidesofwaronline.Exodus.CustomEnchantment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface EnchantmentInterface {
	public void onHit(Player player, LivingEntity target);
	public String getName();
}
