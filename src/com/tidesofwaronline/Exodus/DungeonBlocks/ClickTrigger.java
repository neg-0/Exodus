package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;
import com.tidesofwaronline.Exodus.Player.ExoPlayer;

@DungeonBlockInfo(hasInput = false, hasOutput = true, name = "Click Trigger", material = "BEDROCK", description = "Pulses when clicked with a bare hand.")
public class ClickTrigger extends DungeonBlock {

	public ClickTrigger(Location loc) {
		super(loc);
	}
	
	public ClickTrigger(Map<String, Object> map) {
		super(map);
	}
	
	@Override
	public void onTrigger(DungeonBlockEvent event) {
		if (this.isEnabled()) {
			triggerLinkedBlocks(event);
		}
	}

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Location clickedBlock,
			Action action) {
		Player player = exodusPlayer.getPlayer();
		if (this.isEnabled() && player.getItemInHand().getType() != Material.BLAZE_ROD && player.getItemInHand().getType() != Material.WOOD_AXE) {
			player.sendMessage("Click Triggered");
			triggerLinkedBlocks(new DungeonBlockEvent(this, exodusPlayer.getPlayer()));
		}
	}

	@Override
	public List<String> getAdditionalInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
