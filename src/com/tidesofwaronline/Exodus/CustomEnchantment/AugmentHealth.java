package com.tidesofwaronline.Exodus.CustomEnchantment;

import org.bukkit.entity.LivingEntity;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class AugmentHealth extends CustomEnchantment implements
		EnchantmentInterface {

	private int HP = 0;

	public AugmentHealth(int HP) {
		this.setHP(HP);
	}

	public int getHP() {
		return HP;
	}

	@Override
	public void onDamage(ExoPlayer player, LivingEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHit(ExoPlayer player, LivingEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(ExoPlayer player) {
		// TODO Auto-generated method stub

	}

	public void setHP(int hP) {
		HP = hP;
	}

}
