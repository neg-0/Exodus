package com.tidesofwaronline.Exodus.DungeonBlocks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(hasInput = false, hasOutput = true, name = "Click Trigger", material = "BEDROCK", description = "Pulses when clicked with a bare hand.")
public class ClickTrigger extends DungeonBlock {

	public ClickTrigger(Location loc) {
		super(loc);
	}

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock,
			Action action) {
		Player player = exodusPlayer.getPlayer();
		if (player.getItemInHand().getTypeId() == 0) {
			player.sendMessage("Click Triggered");
			triggerLinkedBlocks();
		}
	}
}