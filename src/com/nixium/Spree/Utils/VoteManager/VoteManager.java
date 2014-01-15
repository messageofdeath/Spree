package com.nixium.Spree.Utils.VoteManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import org.bukkit.ChatColor;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Utils.WorldManager.World;

public class VoteManager {

	private ArrayList<Vote> votes;
	private ArrayList<VoteWorld> voteWorlds;
	private Spree instance;
	
	public VoteManager(Spree instance) {
		this.instance = instance;
		this.resetVotes();
	}
	
	public Vote addVote(Vote vote) {
		if(!this.hasVote(vote.getName())) {
			this.votes.add(vote);
			for(VoteWorld wor : this.voteWorlds) {
				if(wor.getWorldName().equalsIgnoreCase(vote.getWorld().getWorldName())) {
					wor.addVote();
					break;
				}
			}
		}else{
			Vote d = null;
			for(Vote v : this.votes) {
				if(v.getName().equalsIgnoreCase(vote.getName())) {
					if(!v.getWorld().getWorldName().equalsIgnoreCase(vote.getWorld().getWorldName())) {
						d = v;
						this.votes.remove(d);
						vote.setChanged(true);
						this.votes.add(vote);
						for(VoteWorld wor : this.voteWorlds) {
							if(wor.getWorldName().equalsIgnoreCase(d.getWorld().getWorldName())) {
								wor.subVote();
							}
							else if(wor.getWorldName().equalsIgnoreCase(vote.getWorld().getWorldName())) {
								wor.addVote();
							}
						}
						break;
					}
				}
			}
			if(d == null) {
				this.instance.logError("VoteManager", "VoteManager", "addVote(Vote vote)"
						, "Attempted to add a vote under a name already used.");
				return null;
			}
		}
		return this.getVote(vote.getName());
	}
	
	public ArrayList<Vote> getVotes() {
		return this.votes;
	}
	
	public void resetVotes() {
		this.votes = new ArrayList<Vote>();
		this.resetVoteWorlds();
	}
	
	public void resetVoteWorlds() {
		this.voteWorlds = new ArrayList<VoteWorld>();
		for(World world : this.instance.getWorldManager().getWorlds()) {
			this.voteWorlds.add(new VoteWorld(world.getWorldName()));
		}
	}
	
	public World getWorldVote() {
		if(!this.voteWorlds.isEmpty()) {
			this.sortVotes();
			return this.instance.getWorldManager().getWorld(this.voteWorlds.get(0).getWorldName());
		}else{
			if(!this.instance.getWorldManager().getWorlds().isEmpty()) {
				this.resetVotes();
				this.sortVotes();
				return this.instance.getWorldManager().getWorld(this.voteWorlds.get(0).getWorldName());
			}
		}
		return null;
	}
	
	private void sortVotes() {
		Comparator<VoteWorld> compare = new Comparator<VoteWorld>() {
			@Override
			public int compare(VoteWorld world1, VoteWorld world2) {
				if(world1.getTotalVotes() > world2.getTotalVotes()) {
					return 1;
				}
				else if(world1.getTotalVotes() < world2.getTotalVotes()) {
					return -1;
				}else{
					return 0;
				}
			}
		};
		Collections.sort(this.voteWorlds, compare);
		Collections.reverse(this.voteWorlds);
	}
	
	public Vote getVote(String name) {
		for(Vote vote : this.votes) {
			if(name.equalsIgnoreCase(vote.getName())) {
				return vote;
			}
		}
		return null;
	}
	
	public ArrayList<String> getVoteStats() {
		ArrayList<String> stats = new ArrayList<String>();
		int i = 1;
		for(VoteWorld world : this.voteWorlds) {
			stats.add(ChatColor.DARK_PURPLE + "    " + i + ChatColor.DARK_RED +  ") " + ChatColor.GOLD 
					+ world.getWorldName() + ", Votes: " + world.getTotalVotes());
			i++;
		}
		return stats;
	}
	
	public boolean hasVote(String name) {
		return this.getVote(name) != null;
	}
}
