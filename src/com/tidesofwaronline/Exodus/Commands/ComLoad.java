package com.tidesofwaronline.Exodus.Commands;


public class ComLoad extends Command {
	
	public ComLoad(CommandPackage comPackage) {
		comPackage.getExoPlayer().inventoryLoad();
	}
}