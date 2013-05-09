package com.tidesofwaronline.Exodus.Quests;

public class Objective {
	
	private QuestPhase phase;
	
	public Objective() {
		
	}

	public Objective(QuestPhase phase) {
		this.phase = phase;
	}
	
	public QuestPhase getQuestPhase() {
		return phase;
	}
}
