package net.mcpolitics.dev.Spree.Database;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Types.DatabaseType;

public class DatabaseManager {
	
	private Spree instance;
	
	public DatabaseManager(Spree instance) {
		this.instance = instance;
	}

	//******************* Methods ************************
	
	public void loadDatabaseType() {
		if(this.useMySQL(DatabaseType.loadType) || this.useMySQL(DatabaseType.saveType)) {
			this.instance.getMySQL().initDatabase();
		}
	}
	
	public boolean useMySQL(DatabaseType type) {
		return type.getDataType() == DatabaseType.MySQL || type.getDataType() == DatabaseType.ALL;
	}
	
	public boolean useYAML(DatabaseType type) {
		return type.getDataType() == DatabaseType.YAML || type.getDataType() == DatabaseType.ALL;
	}
	
	public void saveDatabases() {
		this.instance.getStatDatabase().saveDatabase();
		this.instance.getWorldDatabase().saveWorlds();
		this.instance.getSettingsDatabase().saveDatabase();
	}
}
