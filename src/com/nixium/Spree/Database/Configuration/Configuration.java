package com.nixium.Spree.Database.Configuration;

import java.util.ArrayList;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Database.Types.DatabaseType;

import me.messageofdeath.Database.YamlDatabase;

public class Configuration {

	private Spree instance;
	private YamlDatabase config;
	
	public Configuration(Spree instance) {
		this.instance = instance;
	}
	
	public void initConfiguration() {
		this.config = new YamlDatabase(this.instance, "config");
		this.config.onStartUp();
	}
	
	public void loadConfiguration() {
		DatabaseType.loadType.setDataType(DatabaseType.parseString(this.config.getString("DatabaseType.Load", "Yaml")));
		DatabaseType.saveType.setDataType(DatabaseType.parseString(this.config.getString("DatabaseType.Save", "Yaml")));
		
		ConfigSettings.DefaultPointLimit.setSetting(this.config.getInteger("defaultPointLimit", 
				(Integer)ConfigSettings.DefaultPointLimit.getDefaultSetting()));
		ConfigSettings.DatabaseSaveInterval.setSetting(this.config.getInteger("DatabaseType.SaveInterval", 
				(Integer)ConfigSettings.DatabaseSaveInterval.getDefaultSetting()));
		ConfigSettings.VotePointAddition.setSetting(this.config.getInteger("VotesAdditionToPoints", 
				(Integer)ConfigSettings.VotePointAddition.getDefaultSetting()));
		ConfigSettings.RequiredAmountOfPlayers.setSetting(this.config.getInteger("RequiredAmountOfPlayers",
				(Integer)ConfigSettings.RequiredAmountOfPlayers.getDefaultSetting()));
		ConfigSettings.LobbySpawnPoint.setSetting(this.config.getString("LobbyVector",
				(String)ConfigSettings.LobbySpawnPoint.getDefaultSetting()));
		ConfigSettings.LobbyPortalCuboid.setSetting(this.config.getString("LobbyPortalCuboid",
				(String)ConfigSettings.LobbyPortalCuboid.getDefaultSetting()));
		
		this.checkConfiguration();
		
		if(DatabaseType.loadType.getDataType() == DatabaseType.MySQL || DatabaseType.saveType.getDataType() == DatabaseType.ALL) {
			for(MySQLSettings settings : MySQLSettings.values()) {
				settings.setSetting(config.getString("Mysql." + settings.getName(), settings.getDefaultSetting()));
			}
		}
	}
	
	private void checkConfiguration() {
		//*********** Database Settings Check **************
		if(DatabaseType.loadType.getDataType() == null || DatabaseType.saveType.getDataType() == null || DatabaseType.loadType.getDataType() == DatabaseType.ALL) {
			if(DatabaseType.loadType.getDataType() == DatabaseType.ALL) {
				this.instance.logError("Configuration", "Configuration", "checkConfiguration()", "The 'load' database feature cannot be the value of Both! Changing to MySQL!");
				DatabaseType.loadType.setDataType(DatabaseType.MySQL);
			}
			if(DatabaseType.loadType.getDataType() == null) {
				this.instance.logError("Configuration", "Configuration", "checkConfiguration()", "The 'load' Database Type must be set! Changing to YAML!");
				DatabaseType.loadType.setDataType(DatabaseType.YAML);
			}
			if(DatabaseType.saveType.getDataType() == null) {
				this.instance.logError("Configuration", "Configuration", "checkConfiguration()", "The 'save' Database Type must be set! Changing to YAML!");
				DatabaseType.loadType.setDataType(DatabaseType.YAML);
			}
		}
		
		this.checkInteger(ConfigSettings.DefaultPointLimit);
		
		this.checkInteger(ConfigSettings.DatabaseSaveInterval);
		
		this.checkInteger(ConfigSettings.RequiredAmountOfPlayers);
		
		this.checkString(ConfigSettings.LobbySpawnPoint);
		
		this.checkString(ConfigSettings.LobbyPortalCuboid);
	}
	
	@SuppressWarnings("unused")
	private void checkArrays(ConfigSettings setting) {
		if(!(setting.getSetting() instanceof ArrayList<?>)) {
			this.instance.logError("Configurtion", "Configuration", "checkArrays(ConfigSettings setting)", "The '"+setting.getName()+"' has to be a List! Changing to default value!");
			setting.setDefaultSetting();
		}
	}
	
	private void checkString(ConfigSettings setting) {
		if(!(setting.getSetting() instanceof String)) {
			this.instance.logError("Configurtion", "Configuration", "checkString(ConfigSettings setting)", "The '"+setting.getName()+"' has to be a String! Changing to default value!");
			setting.setDefaultSetting();
		}
	}
	
	private void checkInteger(ConfigSettings setting) {
		if(!(setting.getSetting() instanceof Integer)) {
			this.instance.logError("Configurtion", "Configuration", "checkInteger(ConfigSettings setting)", "The '"+setting.getName()+"' has to be a Integer! Changing to default value!");
			setting.setDefaultSetting();
		}
	}
}
