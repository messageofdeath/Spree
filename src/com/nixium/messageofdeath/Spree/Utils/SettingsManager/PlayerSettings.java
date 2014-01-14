package com.nixium.messageofdeath.Spree.Utils.SettingsManager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerSettings {
	
	private String name;
	private HashMap<String, Setting> settings;
	
	public PlayerSettings(String name) {
		this.name = name;
		this.settings = new HashMap<String, Setting>();
		for(SettingType setting : SettingType.values()) {
			this.settings.put(setting.getName(), new Setting());
		}
	}
	
	//****************** Player ********************
	
	public String getName() {
		return this.name;
	}
	
	public Player getPlayer() {
		return (Player)Bukkit.getPlayer(this.name);
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(this.name);
	}
	
	//***************** Stats ******************
	
	public Setting getSetting(SettingType settingType) {
		return this.settings.get(settingType.getName());
	}
}
