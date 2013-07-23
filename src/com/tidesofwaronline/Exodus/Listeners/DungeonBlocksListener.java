package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.tidesofwaronline.Exodus.Commands.ComDBEBlockCommand;
import com.tidesofwaronline.Exodus.DungeonBlocks.DBInventory;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class DungeonBlocksListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (ExoPlayer.getExodusPlayer(event.getPlayer()).isInDBEditorMode()) {
			DungeonBlock.placeBlock(ExoPlayer.getExodusPlayer(event.getPlayer()), event.getItemInHand(), event.getBlock().getLocation());
			event.getPlayer().getInventory().setContents(DBInventory.getInventory().getContents());
			event.getPlayer().updateInventory();
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (ExoPlayer.getExodusPlayer(event.getPlayer()).isInDBEditorMode()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {
		if (event.hasBlock()
				&& ExoPlayer.getExodusPlayer(event.getPlayer())
						.isInDBEditorMode()) {
			DungeonBlock.clickBlockEvent(
					ExoPlayer.getExodusPlayer(event.getPlayer()),
					event.getClickedBlock(), event.getAction());
		}
	}

	@EventHandler
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		DungeonBlock.onRedstoneEventEvent(event);
	}

	@EventHandler
	public void playerPickupBlockEvent(PlayerPickupItemEvent event) {
		if (ExoPlayer.getExodusPlayer(event.getPlayer()).isInDBEditorMode()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		ExoPlayer exop = ExoPlayer.getExodusPlayer(event.getPlayer());
		if (exop.isInDBEditorMode() && exop.getEditingBlock() != null) {
			new ComDBEBlockCommand(exop, event.getMessage());
			event.setCancelled(true);
		}
	}

}
