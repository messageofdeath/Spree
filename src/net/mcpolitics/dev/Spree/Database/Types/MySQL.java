package net.mcpolitics.dev.Spree.Database.Types;

import java.sql.SQLException;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Configuration.MySQLSettings;
import me.messageofdeath.Database.MySQLDatabase;

public class MySQL {

	private MySQLDatabase mdatabase;
	public boolean success = false;
	private Spree instance;
	
	public MySQL(Spree instance) {
		this.instance = instance;
	}
	
	//***************** OnStartUp Methods *********************
	
	public void initDatabase() {
    	mdatabase = new MySQLDatabase(this.instance,
    			MySQLSettings.Host.getSetting(),
    			MySQLSettings.Port.getSetting(),
    			MySQLSettings.Database.getSetting(),
    			MySQLSettings.Username.getSetting(),
    			MySQLSettings.Password.getSetting());
    	
    	boolean success = true;
    	try{
    		mdatabase.onStartUp();
    	}catch(SQLException e) {
    		this.instance.logError("MySQL", "MySQL", "initDatabase()", "Coult not connect to database! Changing to YAML!");
    		if(DatabaseType.loadType.getDataType() == DatabaseType.MySQL)
    			DatabaseType.loadType.setDataType(DatabaseType.YAML);
    		if(DatabaseType.saveType.getDataType() == DatabaseType.MySQL)
    			DatabaseType.saveType.setDataType(DatabaseType.YAML);
    		success = false;
    	}
    	this.success = success;
	}
	
	//****************** Methods ************************

	public MySQLDatabase getInstance() {
		return mdatabase;
	}
}
