package net.mcpolitics.dev.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Types.DatabaseType;
import net.mcpolitics.dev.Spree.Utils.SettingsManager.PlayerSettings;
import net.mcpolitics.dev.Spree.Utils.SettingsManager.SettingType;

import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;

public class SettingsDatabase {
	
	private final String table = "Spree_Settings";
	private YamlDatabase database;
	private Spree instance;
	
	public SettingsDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Player VARCHAR(18),";
		        for(SettingType setting : SettingType.values()) {
		        	columns += " " + setting.getName() + " " + setting.getType() + ",";
		        }
		        columns = columns.substring(0, columns.length() - 1);
		        if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
		        	this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[SettingDatabase] Could not load mysql database!");
			}
        }
		
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "settings");
			this.database.onStartUp();
		}
	}
	
	public void loadPlayer(String name) {
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType)) {
			String prefix = "SettingType." + name + ".";
	    	ArrayList<Boolean> settings = new ArrayList<Boolean>();
	    	
	    	for(SettingType type : SettingType.values()) {
				settings.add(this.database.getBoolean(prefix + type.getName(), false));
			}
	    	
	    	this.instance.getSettingsManager().createPlayerSettings(name, settings);
		}
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			ArrayList<Boolean> settings = new ArrayList<Boolean>();
			String where = "Player = '"+name+"'";

			for(SettingType type : SettingType.values()) {
				settings.add(mdatabase.getBoolean(this.table, where, type.getName(), false));
			}
			
			this.instance.getSettingsManager().createPlayerSettings(name, settings);
		}
    }
	
	public void saveDatabase() {
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			for(PlayerSettings player : this.instance.getSettingsManager().getAll()) {
				for(SettingType type : SettingType.values()) {
					this.database.set("PlayerSettings." + player.getName() + "." + type.getName(), player.getSetting(type).getValue());
				}
			}
		}
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			for(PlayerSettings player : this.instance.getSettingsManager().getAll()) {
				if(this.hasPlayer(player.getName())) {
					for(SettingType setting : SettingType.values()) {
						this.instance.getMySQL().getInstance()
							.update(this.table, setting.getName() + " = " + player.getSetting(setting).getValue(), "Player = '"+player.getName()+"'");
					}
				}else{
					String columns = "Player,";
					String values = "'" + player.getName() + "',";
					for(SettingType setting : SettingType.values()) {
						columns += " " + setting.getName() + ",";
						values += " " + player.getSetting(setting).getValue() + ",";
					}
					columns = columns.substring(0, columns.length() - 1);
					values = values.substring(0, values.length() - 1);
					//Insert
					this.instance.getMySQL().getInstance()
						.insert(this.table, columns, values);
				}
			}
		}
	}
	
	//****************** Methods ********************
	
	public boolean hasPlayer(String name) {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType))
			return this.instance.getMySQL().getInstance().contains(this.table, "Player = '"+name+"'");
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType))
			return this.database.contains("PlayerSettings." + name);
		return false;
	}
}
