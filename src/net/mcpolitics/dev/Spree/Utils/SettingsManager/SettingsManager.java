package net.mcpolitics.dev.Spree.Utils.SettingsManager;

import java.util.ArrayList;
import java.util.HashMap;

import net.mcpolitics.dev.Spree.Spree;

public class SettingsManager {
	
	private Spree instance;
	protected HashMap<String, PlayerSettings> settings;
	
	public SettingsManager(Spree instance) {
		this.settings = new HashMap<String, PlayerSettings>();
		this.instance = instance;
	}
		
	public void storePlayerSettings(PlayerSettings setting) {
		this.settings.put(setting.getName(), setting);
	}
	
	public boolean containsPlayerSettings(String name) {
		return this.settings.containsKey(name);
	}
	
	public void createPlayerSettings(String name) {
		if(!this.containsPlayerSettings(name)) {
			if(this.instance.getSettingsDatabase().hasPlayer(name)) {
				this.instance.getSettingsDatabase().loadPlayer(name);
			}else{
				ArrayList<Boolean> stats = new ArrayList<Boolean>();
				for(int i = 0; i < SettingType.values().length; i++) {
					stats.add(true);
				}
				this.createPlayerSettings(name, stats);
			}
		}
	}
	
	public void createPlayerSettings(String name, ArrayList<Boolean> settings) {
		PlayerSettings playerSettings = new PlayerSettings(name);
		int i = 0;
		for(Boolean stat : settings) {
			playerSettings.getSetting(SettingType.values()[i]).setValue(stat);
			i++;
		}
    	
    	this.storePlayerSettings(playerSettings);
	}
	
	public PlayerSettings getPlayerSettings(String name) {
		this.createPlayerSettings(name);
		return this.settings.get(name);
	}
	
	// ALL PLAYER STATS
	
	public ArrayList<String> getAllString() {
		ArrayList<String> settings = new ArrayList<String>();
		for(String set : this.settings.keySet()) {
			settings.add(set);
		}
		return settings;
	}
	
	public ArrayList<PlayerSettings> getAll() {
		ArrayList<PlayerSettings> settings = new ArrayList<PlayerSettings>();
		for(PlayerSettings sta : this.settings.values()) {
			settings.add(sta);
		}
		return settings;
	}
}
