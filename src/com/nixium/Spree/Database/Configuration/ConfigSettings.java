package com.nixium.Spree.Database.Configuration;

import org.bukkit.Bukkit;

public enum ConfigSettings {
	
	//*********** Database **************
	
	DatabaseSaveInterval(),
	
	
	
	DefaultPointLimit(),
	
	//*************** Vote ***************
	
	VotePointAddition(),
	
	//************ Worlds *****************
	
	LobbySpawnPoint(),
	
	
	LobbyPortalCuboid(),
	
	
	RequiredAmountOfPlayers();
	
	
	
	//Worlds();
	
	//********* Getting ***************
    
	private Object setting;
	
    public String getName() {
        return this.toString();
    }
	
	public Object getSetting() {
		return setting;
	}
    
    public void setSetting(Object setting) {
        this.setting = setting;
    }
    
    public void setDefaultSetting() {
    	this.setting = this.getDefaultSetting();
    }
    
    public Object getDefaultSetting() {
    	if(this.getName().equalsIgnoreCase(ConfigSettings.DatabaseSaveInterval.getName()))
    		return 60*10;
    	else if(this.getName().equalsIgnoreCase(ConfigSettings.DefaultPointLimit.getName()))
    		return 1000;
    	else if(this.getName().equalsIgnoreCase(ConfigSettings.VotePointAddition.getName()))
    		return 20;
    	else if(this.getName().equalsIgnoreCase(ConfigSettings.LobbySpawnPoint.getName()))
    		return Bukkit.getWorlds().get(0).getName() + ",0,0,0,0,0";
    	else if(this.getName().equalsIgnoreCase(ConfigSettings.LobbyPortalCuboid.getName()))
    		return ConfigSettings.LobbySpawnPoint.getDefaultSetting() + ";" + ConfigSettings.LobbySpawnPoint.getDefaultSetting();
    	else if(this.getName().equalsIgnoreCase(ConfigSettings.RequiredAmountOfPlayers.getName()))
    		return 2;
    	else
    		return null;
    }
} 
