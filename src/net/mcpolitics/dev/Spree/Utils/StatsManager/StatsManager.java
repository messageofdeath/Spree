package net.mcpolitics.dev.Spree.Utils.StatsManager;

import java.util.ArrayList;
import java.util.HashMap;

import net.mcpolitics.dev.Spree.Spree;

public class StatsManager {
	
	private Spree instance;
	protected HashMap<String, PlayerStats> stats;
	
	public StatsManager(Spree instance) {
		this.stats = new HashMap<String, PlayerStats>();
		this.instance = instance;
	}
		
	public void storePlayerStats(PlayerStats statss) {
		this.stats.put(statss.getName(), statss);
	}
	
	public boolean containsPlayerStats(String name) {
		return this.stats.containsKey(name);
	}
	
	public void createPlayerStats(String name) {
		if(!this.containsPlayerStats(name)) {
			if(this.instance.getStatDatabase().hasPlayer(name)) {
				this.instance.getStatDatabase().loadPlayer(name);
			}else{
				ArrayList<Integer> stats = new ArrayList<Integer>();
				for(int i = 0; i < StatType.values().length; i++) {
					stats.add(0);
				}
				this.createPlayerStats(name, stats);
			}
		}
	}
	
	public void createPlayerStats(String name, ArrayList<Integer> stats) {
		PlayerStats playerStats = new PlayerStats(name);
		int i = 0;
		for(Integer stat : stats) {
			playerStats.getStat(StatType.values()[i]).setStat(stat);
			i++;
		}
    	
    	storePlayerStats(playerStats);
	}
	
	public PlayerStats getPlayerStats(String name) {
		this.createPlayerStats(name);
		return this.stats.get(name);
	}
	
	// ALL PLAYER STATS
	
	public ArrayList<String> getAllString() {
		ArrayList<String> stat = new ArrayList<String>();
		for(String sta : this.stats.keySet())
			stat.add(sta);
		return stat;
	}
	
	public ArrayList<PlayerStats> getAll() {
		ArrayList<PlayerStats> stat = new ArrayList<PlayerStats>();
		for(PlayerStats sta : this.stats.values())
			stat.add(sta);
		return stat;
	}
}
