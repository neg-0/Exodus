package com.tidesofwaronline.Exodus.CustomEnchantment;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class Freeze extends CustomEnchantment implements Runnable,
		EnchantmentInterface {

	ArrayList<Block> blocks = new ArrayList<Block>();

	public String getName() {
		return "Freeze";
	}

	public void onHit(ExoPlayer player, LivingEntity target) {

		target.getLocation().getBlock().setTypeId(Material.ICE.getId(), false);
		target.getLocation().add(0, 1, 0).getBlock()
				.setTypeId(Material.ICE.getId(), false);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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
