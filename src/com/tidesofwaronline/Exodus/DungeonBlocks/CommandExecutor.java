package com.tidesofwaronline.Exodus.DungeonBlocks;

import org.bukkit.Location;


@DungeonBlockInfo(hasInput = false, hasOutput = false, name = "Command Executor", material = "COAL_BLOCK", settings = { "" }, description = "Executes a command when triggered.")
public class CommandExecutor extends DungeonBlock {

	public CommandExecutor(Location loc) {
		super(loc);
	}
}
