package com.nixium.Spree.Utils.StreakManager;

public class Streak {

	private String name;
	private int streak, lastAnnouceStreak;
	
	public Streak(String name, int streak) {
		this.name = name;
		this.streak = streak;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getStreak() {
		return this.streak;
	}
	
	public int getLastAnnouncedStreak() {
		return this.lastAnnouceStreak;
	}
	
	public void setAnnouncedStreak(int streak) {
		this.lastAnnouceStreak = streak;
	}
	
	public void setStreak(int streak) {
		this.streak = streak;
	}
}
