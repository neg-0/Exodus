package com.tidesofwaronline.Exodus.DungeonBlocks.Triggers;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ClickTrigger extends DungeonBlock {

	public ClickTrigger(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock,
			Action action) {
		Player player = exodusPlayer.getPlayer();
		if (player.getItemInHand() == null) {
			player.sendMessage("Click Triggered");
			for (DungeonBlock d : this.getLinkedBlocks(this)) {
				d.onTrigger();
			}
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
