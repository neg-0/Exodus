package com.tidesofwaronline.Exodus.DungeonBlocks;

public class PseudoDungeonBlock extends DungeonBlock {
	
	String name;
	
	public PseudoDungeonBlock(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
