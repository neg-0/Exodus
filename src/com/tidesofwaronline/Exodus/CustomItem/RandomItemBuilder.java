package com.tidesofwaronline.Exodus.CustomItem;

public class RandomItemBuilder {

	public RandomItemBuilder() {

	}
	
	public static CustomItem buildItem(int level) {
		CustomItem item = new CustomItem.CustomItemBuilder().build();
		return item;
	}
}
