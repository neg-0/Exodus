package com.tidesofwaronline.Exodus.DungeonBlocks.Triggers;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(hasInput = false, hasOutput = true, name = "Click Trigger", material = "BEDROCK")
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

	@Override
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrigger() {
		// TODO Auto-generated method stub
		
	}

}
