package com.tidesofwaronline.Exodus.Quests;

import java.util.PriorityQueue;
import java.util.Queue;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class Quest {
	
	private String name;
	private int level;
	private int minLevel;
	private int maxLevel;
	private String description;
	private Queue<QuestPhase> phases = new PriorityQueue<QuestPhase>();
	
	private ExoPlayer exoplayer;
	private Player player;
	
	public Quest(String name) {
		setName(name);
	}
	
	public void alert(Player player) {
		player.sendMessage("New quest accepted: " + this.getName());
	}

	public void addPhase(QuestPhase phase) {
		this.phases.add(phase);
	}
	
	public String getDescription() {
		return description;
	}

	public ExoPlayer getExoplayer() {
		return exoplayer;
	}
	public int getLevel() {
		return level;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public String getName() {
		return name;
	}
	public Queue<QuestPhase> getPhases() {
		return phases;
	}
	public Player getPlayer() {
		return player;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setExoplayer(ExoPlayer exoplayer) {
		this.exoplayer = exoplayer;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
