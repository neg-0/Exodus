package com.tidesofwaronline.Exodus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandler {

	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = manager.getNewScoreboard();
	Team venturi = board.registerNewTeam("Venturi");
	Team abraxian = board.registerNewTeam("Abraxian");

	public ScoreboardHandler(Player player) {

		player.sendMessage("Creating new scoreboard");

		venturi.addPlayer(Bukkit.getOfflinePlayer("MastaC"));
		venturi.setPrefix("[V]");

		Objective obj = board.registerNewObjective("testobj", "health");
		obj.setDisplayName("TestOBJ");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		for (Player online : Bukkit.getOnlinePlayers()) {
			online.setScoreboard(board);
		}

	}
}
