package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class RedstoneSwitch extends DungeonBlock {

	public RedstoneSwitch(Location loc) {
		super(loc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock,
			Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrigger(DungeonBlockEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
