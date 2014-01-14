package com.nixium.messageofdeath.Spree.Database.Databases;

import java.util.logging.Level;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Database.Types.DatabaseType;
import com.nixium.messageofdeath.Spree.Utils.StaffManager.StaffType;


public class StaffDatabase {

	private Spree instance;
	private final String table = "Spree_Staff";
	
	public StaffDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Player VARCHAR(20), StafffType VARCHAR(30)";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[Staff Database] Could not load mysql database!");
			}
        }
	}
	
	public void addStaff(StaffType type, String name) {
		if(this.hasPlayer(name)) {
			this.instance.getMySQL().getInstance().insert(this.table, "Player", name);
		}else{
			this.instance.logError("Staff Database", "StaffDatabase", "addStaff(String name)", "Attempted to add a staff member that is already in use.");
		}
	}
	
	public StaffType getStaffType(String name) {
		return StaffType.valueOf(this.instance.getMySQL().getInstance().getString(this.table, "Player = '"+name+"'", "StaffType", ""));
	}
	
	public boolean hasPlayer(String name) {
		return this.instance.getMySQL().getInstance().contains(this.table, "Player = '"+name+"'");
	}
}
