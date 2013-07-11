package com.tidesofwaronline.Exodus.Commands;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class ComDBEBlockCommand {

	public ComDBEBlockCommand(ExoPlayer exop, String message) {
		if (message.equalsIgnoreCase("delete")) {
			exop.editingBlock.delete();
			exop.getPlayer().sendMessage(exop.editingBlock.toString() + " has been deleted and removed.");
			exop.editingBlock = null;
		} else if (message.equalsIgnoreCase("exit")) {
			exop.getPlayer().sendMessage("No longer editing " + exop.editingBlock.toString());
			exop.editingBlock = null;
		} else {
			exop.getPlayer().sendMessage("Command " + message + " not found!");
		}
	}

}
