package com.nixium.messageofdeath.Spree.Utils.StreakManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import org.bukkit.ChatColor;

import com.nixium.messageofdeath.Spree.Spree;

public class StreakManager {
	
	private ArrayList<Streak> streaks;
	private Spree instance;
	
	public StreakManager(Spree instance) {
		this.streaks = new ArrayList<Streak>();
		this.instance = instance;
	}
	
	public Streak createStreak(String name) {
		if(getStreak(name) == null) {
			this.streaks.add(new Streak(name, 0));
		}else{
			this.instance.logError("StreakManager", "StreakManager", "createStreak(String name)"
					, "Attempted to add a streak under a name already used.");
		}
		return this.getStreak(name);
	}
	
	public void resetStreaks() {
		this.streaks = new ArrayList<Streak>();
	}
	
	public ArrayList<Streak> getStreaks() {
		return this.streaks;
	}
	
	public Streak getStreak(String name) {
		for(Streak streak : this.streaks) {
			if(name.equalsIgnoreCase(streak.getName())) {
				return streak;
			}
		}
		return null;
	}
	
	public Streak updateStreak() {
		Comparator<Streak> compare = new Comparator<Streak>() {
			@Override
			public int compare(Streak s1, Streak s2) {
				if(s1.getStreak() > s2.getStreak()) {
					return 1;
				}
				else if(s1.getStreak() < s2.getStreak()) {
					return -1;
				}else{ 
					return 0;
				}
			}
		};
		Collections.sort(this.streaks, compare);
		Collections.reverse(this.streaks);
		if(this.instance.getGame().getLongestStreak() != null) {
			if(this.streaks.get(0).getStreak() > this.instance.getGame().getLongestStreak().getStreak()) {
				this.instance.getGame().sendMessage(true, ChatColor.BLUE + this.streaks.get(0).getName() + ChatColor.GOLD + " overtook " 
						+ ChatColor.BLUE + this.instance.getGame().getLongestStreak().getName() + ChatColor.GOLD + " with a streak of " 
						+ ChatColor.DARK_RED + this.streaks.get(0).getStreak() + ChatColor.GOLD + "!");
				this.instance.getGame().setLongestStreak(this.streaks.get(0));
			}else{
				if(this.instance.getGame().getLongestStreak().getStreak() != this.streaks.get(0).getLastAnnouncedStreak()) {
					if(this.instance.getGame().getLongestStreak().getStreak() % 5 == 0 
							&& this.instance.getGame().getLongestStreak().getStreak() != 0) {
						this.instance.getGame().sendMessage(true, ChatColor.BLUE + this.streaks.get(0).getName() + ChatColor.GOLD + " has a streak of " 
							+ ChatColor.DARK_RED + this.instance.getGame().getLongestStreak().getStreak() + ChatColor.GOLD + "!");
						this.streaks.get(0).setAnnouncedStreak(this.streaks.get(0).getStreak());
					}
				}
			}
		}else{
			this.instance.getGame().sendMessage(true, ChatColor.BLUE + this.streaks.get(0).getName() 
					+ ChatColor.GOLD + " has a streak of " + ChatColor.DARK_RED + this.streaks.get(0).getStreak() + ChatColor.GOLD + "!");
			this.instance.getGame().setLongestStreak(this.streaks.get(0));
		}
		return null;
	}
	/*boolean overTaken = false;
	for(Streak s : this.instance.getStreakManger().getStreaks()) {
		if(this.getLongestStreak() != null) {
			if(s.getStreak() > this.getLongestStreak().getStreak()) {
				this.sendMessage(true, s.getName() + " has overtaken " + this.getLongestStreak().getName() 
						+ " with a streak of " + ChatColor.DARK_RED + this.getLongestStreak().getStreak());
				this.setLongestStreak(s);
				overTaken = true;
			}
			overTaken = true;
		}else{
			Streak longestStreak = null;
			for(Streak st : this.instance.getStreakManger().getStreaks()) {
				if(longestStreak != null) {
					if(st.getStreak() > longestStreak.getStreak()) {
						longestStreak = st;
					}
				}else{
					longestStreak = st;
				}
			}
			this.setLongestStreak(longestStreak);
			break;
		}
	}
	if(overTaken == false) {
		int streak = this.getLongestStreak().getStreak();
		if(streak % 5 == 0 && streak != 0) {
			this.sendMessage(true, this.getLongestStreak().getName() + " has a streak of " + streak);
		}
	}*/
	public boolean hasStreak(String name) {
		return this.getStreak(name) != null;
	}
}
