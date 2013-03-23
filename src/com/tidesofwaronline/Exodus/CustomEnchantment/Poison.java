package com.tidesofwaronline.Exodus.CustomEnchantment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class Poison extends CustomEnchantment implements EnchantmentInterface{

	Player player;
	Player target;

	public void onHit(ExoPlayer player, LivingEntity target) {
		target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,
				30));
	}

	public String getName() {
		return "Poison";
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