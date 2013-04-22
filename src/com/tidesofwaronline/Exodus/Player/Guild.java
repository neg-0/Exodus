package com.tidesofwaronline.Exodus.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Guild {
	
	public enum GuildAchievement {
		
	}
	
	ExoPlayer leader;
	List<ExoPlayer> members = new ArrayList<ExoPlayer>();
	List<GuildAchievement> achievements = new ArrayList<GuildAchievement>();
	ArrayList<ExoPlayer> invites = new ArrayList<ExoPlayer>();
	
	Guild() {
		
	}

	public ExoPlayer getGuildLeader() {
		return leader;
	}

	public void setGuildLeader(ExoPlayer guildLeader) {
		this.leader = guildLeader;
	}

	public List<ExoPlayer> getMembers() {
		return members;
	}
	
	public void info(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void addMember(ExoPlayer member) {
		this.members.add(member);
	}
	
	public void removeMember(ExoPlayer member) {
		this.members.remove(member);
	}
	
	public void invite(ExoPlayer p) {
		invites.add(p);
		p.getPlayer().sendMessage("You've been invited to " + leader.getPlayer().getName() + "\'s party! Type /party accept to join!");
	}

	public List<GuildAchievement> getAchievements() {
		return achievements;
	}

	public void addAchievement(GuildAchievement achievement) {
		this.achievements.add(achievement);
	}

}
