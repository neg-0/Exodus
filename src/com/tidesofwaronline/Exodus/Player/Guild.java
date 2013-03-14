package com.tidesofwaronline.Exodus.Player;

import java.util.ArrayList;
import java.util.List;

public class Guild {
	
	public enum GuildAchievement {
		
	}
	
	ExoPlayer guildLeader;
	List<ExoPlayer> members = new ArrayList<ExoPlayer>();
	List<GuildAchievement> achievements = new ArrayList<GuildAchievement>();
	
	Guild() {
		
	}

	public ExoPlayer getGuildLeader() {
		return guildLeader;
	}

	public void setGuildLeader(ExoPlayer guildLeader) {
		this.guildLeader = guildLeader;
	}

	public List<ExoPlayer> getMembers() {
		return members;
	}

	public void addMember(ExoPlayer member) {
		this.members.add(member);
	}

	public List<GuildAchievement> getAchievements() {
		return achievements;
	}

	public void addAchievement(GuildAchievement achievement) {
		this.achievements.add(achievement);
	}

}
