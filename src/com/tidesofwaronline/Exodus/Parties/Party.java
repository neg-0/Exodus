package com.tidesofwaronline.Exodus.Parties;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.tidesofwaronline.Exodus.Player.ExoPlayer;

public class Party {
	
	static ArrayList<Party> partyIndex = new ArrayList<Party>();
	ArrayList<ExoPlayer> party = new ArrayList<ExoPlayer>();
	ArrayList<ExoPlayer> invites = new ArrayList<ExoPlayer>();
	ExoPlayer leader;
	boolean isPrivate = false;
	
	public Party(ExoPlayer leader) {
		this.leader = leader;
		party.add(leader);
		leader.setParty(this);
		partyIndex.add(this);
	}
	
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public void addMember(ExoPlayer ep) {
		party.add(ep);
		ep.setParty(this);
	}
	
	public void removeMember(ExoPlayer ep) {
		party.remove(ep);
		ep.setParty(null);
	}
	
	public boolean containsMember(ExoPlayer ep) {
		return party.contains(ep);
	}
	
	public ArrayList<ExoPlayer> getMembers() {
		return party;
	}
	
	public void setLeader(ExoPlayer leader) {
		this.leader = leader;
	}
	
	public ExoPlayer getLeader() {
		return leader;
	}
	
	public boolean isLeader(ExoPlayer ep) {
		return ep.equals(leader);
	}
	
	public void disband() {
		for (ExoPlayer p : party) {
			p.getPlayer().sendMessage("Party disbanded!");
			p.setParty(null);
		}
		
		partyIndex.remove(this);
		
		party = null;
		invites = null;
		leader = null;
	}
	
	public void distributeExp(int xp) {
		xp = (xp/party.size()) * (1 + party.size()/10);
		for (ExoPlayer e : party) {
			e.addXP(xp);
		}
	}

	public void info(Player player) {
		String members = ("Party members: " );
		for (int x = 0; x < party.size(); x++) {
			members += party.get(x).getPlayer().getName();
			if (x < party.size() - 1) {
				members += ", ";
			}
		}
		player.sendMessage(members);
	}

	public void invite(ExoPlayer ep) {
		for (Party p : getParties()) {
			p.removeInvite(ep);
		}
		invites.add(ep);
		ep.getPlayer().sendMessage("You've been invited to " + leader.getPlayer().getName() + "\'s party! Type /party accept to join!");
	}
	
	public void partyAccept(ExoPlayer ep) {
		if (invites.contains(ep)) {
			invites.remove(ep);
			for (ExoPlayer e : getMembers()) {
				e.getPlayer().sendMessage(ep.getPlayer().getName() + " has joined the party!");
			}
			addMember(ep);
			ep.getPlayer().sendMessage("You have joined " + leader.getPlayer().getName() + "'s party!");
		}
		
		for (Party p : getParties()) {
			p.removeInvite(ep);
		}
	}
	
	public void partyDeny(ExoPlayer ep) {
		invites.remove(ep);
	}
	
	public void removeInvite(ExoPlayer ep) {
		invites.remove(ep);
	}
	
	public boolean hasInvite(ExoPlayer ep) {
		return invites.contains(ep);
	}
	
	public static ArrayList<Party> getParties() {
		return partyIndex;
	}
	
	public static Party getPlayerParty(ExoPlayer ep) {
		for (Party p : getParties()) {
			if (p.containsMember(ep)) {
				return p;
			}
		}
		
		return null;
	}
}
