package com.tidesofwaronline.Exodus.Parties;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class Party {
	
	ArrayList<ExoPlayer> party = new ArrayList<ExoPlayer>();
	ArrayList<ExoPlayer> invites = new ArrayList<ExoPlayer>();
	ExoPlayer leader;
	
	Party(ExoPlayer leader) {
		this.leader = leader;
	}
	
	public void addMember(ExoPlayer member) {
		party.add(member);
	}
	
	public void removeMember(ExoPlayer member) {
		party.remove(member);
	}
	
	public void setLeader(ExoPlayer leader) {
		this.leader = leader;
	}
	
	public void disband() {
		
	}
	
	public void distributeExp(int xp) {
		xp = (xp/party.size()) * (1 + party.size()/10);
		for (ExoPlayer e : party) {
			e.addXP(xp);
		}
	}

	public void info(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void invite(ExoPlayer p) {
		invites.add(p);
		p.getPlayer().sendMessage("You've been invited to " + leader.getPlayer().getName() + "\'s party! Type /party accept to join!");
	}
	
	public void partyAccept() {
		
	}
	
	public void partyDeny() {
		
	}
}
