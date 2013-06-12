package com.tidesofwaronline.Exodus.Commands;

import java.util.HashMap;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;
import com.tidesofwaronline.Exodus.Races.Races.Race;

public class ComTest extends Command {

	public ComTest(ExoPlayer player, String[] args) {
		
		HashMap<Race, Integer> races = player.getRacesByRep();
		
		player.getPlayer().sendMessage("" + races.size());
	}
}
