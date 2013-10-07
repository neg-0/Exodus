package com.tidesofwaronline.Exodus.DungeonBlocks;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.tidesofwaronline.Exodus.Exodus;
import com.tidesofwaronline.Exodus.Commands.CommandPackage;
import com.tidesofwaronline.Exodus.DungeonBlocks.DungeonBlock.DungeonBlockInfo;

@DungeonBlockInfo(description = "Manipulates and reacts to Redstone signals.", hasInput = true, hasOutput = true, material = "REDSTONE_LAMP_OFF", name = "Redstone Switch")
public class RedstoneSwitch extends DungeonBlock {
	
	Mode mode = Mode.PULSE;
	
	public RedstoneSwitch() {
		
	}

	public RedstoneSwitch(Location loc) {
		super(loc);
	}
	
	public RedstoneSwitch(Map<String, Object> map) {
		super(map);
	}

	@Override
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		if (this.isEnabled()) {
			if (this.getLocation().getBlock().getType() == Material.REDSTONE_BLOCK) {
				return;
			}
			if (this.getLocation().getBlock().equals(event.getBlock()) && event.getBlock().isBlockPowered()) {
				this.triggerLinkedBlocks(new DungeonBlockEvent(this));
			}
		}
	}

	@Override
	public void onTrigger(DungeonBlockEvent event) {
		switch(mode) {
		case PULSE: {
			this.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);
			Bukkit.getScheduler().runTaskLater(Exodus.getPlugin(), new Delay(this), 5L);
			break;
		}
		case TOGGLE: {
			if (this.getLocation().getBlock().getType() == Material.REDSTONE_BLOCK) {
				this.getLocation().getBlock().setType(this.getMaterial());
			} else {
				this.getLocation().getBlock().setType(Material.REDSTONE_BLOCK);
			}
			break;
		}
		}
	}
	
	private class Delay extends BukkitRunnable {
		
		RedstoneSwitch redstoneSwitch;

		public Delay(RedstoneSwitch redstoneSwitch) {
			this.redstoneSwitch = redstoneSwitch;
		}

		@Override
		public void run() {
			redstoneSwitch.getLocation().getBlock().setType(redstoneSwitch.getMaterial());
		}
		
	}
	
	@DungeonBlockCommand(description = "Sets a variable.", example = "", syntax = "set <variable> <setting>")
	public String set(CommandPackage cp) {
		if (cp.getArgs()[0].equalsIgnoreCase("Mode")) {
			this.setMode(Mode.valueOf(cp.getArgs()[1].toUpperCase()));
			return "Mode set to " + this.getMode();
		} else return "Avaiable settings: Mode";
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = super.serialize();
		//map.put("Mode", mode);
		return map;
	}
	
	enum Mode {
		PULSE,
		TOGGLE;
	}

	@Override
	public List<String> getAdditionalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
