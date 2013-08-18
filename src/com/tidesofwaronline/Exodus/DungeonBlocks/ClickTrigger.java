package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
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
	
/*	public ClickTrigger(Map<String, Object> map) {
		super(SerializableLocation.fromString(map.get("Location").toString()).toLocation(), Integer.parseInt(map.get("ID").toString()));
		
		if (map.containsKey("Enabled")) {
			this.setEnabled(Boolean.parseBoolean(map.get("Enabled").toString()));
		}
	}*/

	@Override
	public void onClickBlock(ExoPlayer exodusPlayer, Block clickedBlock,
			Action action) {
		Player player = exodusPlayer.getPlayer();
		if (this.isEnabled() && player.getItemInHand().getTypeId() == 0) {
			player.sendMessage("Click Triggered");
			triggerLinkedBlocks(new DungeonBlockEvent(this, exodusPlayer.getPlayer()));
		}
	}
}
