package com.nixium.messageofdeath.Spree.Database.Databases;

import java.util.logging.Level;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Database.Types.DatabaseType;
import com.nixium.messageofdeath.Spree.Utils.DonatorManager.DonationType;


import me.messageofdeath.Database.YamlDatabase;

public class DonatorDatabase {

	private Spree instance;
	private YamlDatabase database;
	private final String table = "Spree_Donators";
	
	public DonatorDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Player VARCHAR(20), DonationType VARCHAR(30)";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[DonatorDatabase] Could not load mysql database!");
			}
        }
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "donators");
			this.database.onStartUp();
		}
	}
	
	public void addDonator(DonationType type, String name) {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.hasPlayer(name)) {
				this.instance.getMySQL().getInstance().insert(this.table, "Player, DonationType", "'"+name+"','"+type.name()+"'");
			}else{
				this.instance.logError("Donator Database", "DonatorDatabase", "addDonator(String name)", "Attempted to add a donator that is already in use.");
			}
		}
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			if(this.hasPlayer(name)) {
				this.database.set(name, type.name());
			}else{
				this.instance.logError("Donator Database", "DonatorDatabase", "addDonator(String name)", "Attempted to add a donator that is already in use.");
			}
		}
	}
	
	public DonationType getDonationType(String name) {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			return DonationType.valueOf(this.instance.getMySQL().getInstance().getString(this.table, "Player = '"+name+"'", "DonationType", ""));	
		}
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			return DonationType.valueOf(this.database.getString(name, ""));
		}
		return null;
		
	}
	
	public boolean hasPlayer(String name) {
		return this.instance.getMySQL().getInstance().contains(this.table, "Player = '"+name+"'");
	}
}
