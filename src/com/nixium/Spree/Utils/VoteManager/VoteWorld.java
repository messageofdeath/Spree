package com.nixium.Spree.Utils.VoteManager;

public class VoteWorld {

	private String world;
	private int votes;
	
	public VoteWorld(String world) {
		this.world = world;
		this.votes = 0;
	}
	
	public String getWorldName() {
		return this.world;
	}
	
	public int getTotalVotes() {
		return this.votes;
	}
	
	public void resetVotes() {
		this.votes = 0;
	}
	
	public void addVote() {
		this.votes += 1;
	}
	
	public void subVote() {
		this.votes -= 1;
	}
}
