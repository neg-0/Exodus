package com.tidesofwaronline.Exodus.Quests;

import java.util.ArrayList;

public class QuestPhase {
	
	private String name;
	private ArrayList<Objective> objectives = new ArrayList<Objective>();
	
	public QuestPhase() {
		
	}
	
	public QuestPhase(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	public ArrayList<Objective> getObjectives() {
		return objectives;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addObjective(Objective objective) {
		this.objectives.add(objective);
	}
}
