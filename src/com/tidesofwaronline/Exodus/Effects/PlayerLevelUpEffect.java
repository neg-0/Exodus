package com.tidesofwaronline.Exodus.Effects;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Util.FireworkEffectPlayer;

public class PlayerLevelUpEffect {

	public PlayerLevelUpEffect(Player player) {
		player.getWorld().playSound(player.getLocation(),
				Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
		player.getWorld().strikeLightningEffect(player.getLocation());
		FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		FireworkEffect fe = FireworkEffect.builder().with(Type.BURST)
				.withColor(Color.YELLOW).withFlicker().withTrail().build();
		try {
			for (int i = 0; i < 5; i++) {
				fplayer.playFirework(player.getWorld(), player.getLocation(),
						fe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
