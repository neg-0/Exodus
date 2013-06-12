package com.tidesofwaronline.Exodus.Locks;

public class RandomLootChest extends Chest {
	
	private int level; //For randomized loot


	public RandomLootChest(int level) {
		this.level = level;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}

}
