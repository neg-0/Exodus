package com.tidesofwaronline.Exodus.CustomEnchantment;

import java.util.Random;

import org.bukkit.entity.LivingEntity;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class FireAura extends CustomEnchantment implements EnchantmentInterface {

	Random random = new Random();

	public String getName() {
		return "Fire Aura";
	}

	public void onHit(ExoPlayer player, LivingEntity target) {
		if (random.nextInt(100) < super.getLevel() * 5)
			target.setFireTicks(super.getLevel() * 40);
	}

	@Override
	public void onDamage(ExoPlayer player, LivingEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(ExoPlayer player) {
		// TODO Auto-generated method stub

	}

}
