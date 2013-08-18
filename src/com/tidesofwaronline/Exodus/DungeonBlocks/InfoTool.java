package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.Map;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonToolInfo;

@DungeonToolInfo(description = { "Displays info on a block.", "Left click for block info.", "Right click to Edit a block.", "Shift-Left Click to select a block.", "Shift-Right Click to link a block." }, material = "BLAZE_ROD", name = "Info Tool")
public class InfoTool extends DungeonBlock {

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
