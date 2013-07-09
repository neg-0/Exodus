package com.tidesofwaronline.Exodus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

import com.tidesofwaronline.Exodus.DungeonBlocks.DBInventory;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class DungeonBlocksListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (ExoPlayer.getExodusPlayer(event.getPlayer()).isInDBEditorMode()) {
			ItemMeta im = event.getItemInHand().getItemMeta();
			if (im != null && im.hasLore()
					&& im.getLore().get(0).equals("§8Dungeon Block")) {
				DungeonBlock.placeBlock(ExoPlayer.getExodusPlayer(event.getPlayer()), event.getItemInHand(), event.getBlock().getLocation());
				event.getPlayer().getInventory().setContents(DBInventory.getInventory().getContents());
			}
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (ExoPlayer.getExodusPlayer(event.getPlayer()).isInDBEditorMode()) {
			if (!DungeonBlock.isDungeonBlock(event.getBlock().getLocation())) {
				event.setCancelled(true);
			}
			DungeonBlock.breakBlockEvent(
					ExoPlayer.getExodusPlayer(event.getPlayer()),
					event.getBlock());
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

}
