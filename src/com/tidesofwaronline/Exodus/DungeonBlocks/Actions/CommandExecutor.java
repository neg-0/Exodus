package com.tidesofwaronline.Exodus.DungeonBlocks.Actions;

import org.bukkit.Location;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlockInfo;

@DungeonBlockInfo(hasInput = false, hasOutput = false, name = "Command Executor", material = "COAL_BLOCK")
public class CommandExecutor extends DungeonBlock {

	public CommandExecutor(Location loc) {
		super(loc);
	}
}
