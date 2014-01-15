package com.nixium.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Database.DatabaseManager;
import com.nixium.Spree.Database.Configuration.ConfigSettings;
import com.nixium.Spree.Database.Types.DatabaseType;
import com.nixium.Spree.Utils.WorldManager.World;
import com.nixium.Spree.Utils.WorldManager.WorldManager;


import me.messageofdeath.Blocks.Cuboid;
import me.messageofdeath.Blocks.Vector;
import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;

public class WorldDatabase {
	
	private final String table = "Spree_Worlds";
	private DatabaseManager manager;
	private YamlDatabase database;
	private Spree instance;
	
	public WorldDatabase(Spree instance) {
		this.instance = instance;
	}

	public void initDatabase() {
		this.manager = this.instance.getDatabaseManager();
		
		if(this.manager.useMySQL(DatabaseType.loadType) || this.manager.useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "WorldName VARCHAR(50), Spawns TEXT, Border TEXT";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[WorldDatabase] Could not load mysql database!");
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType) || this.manager.useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "worlds");
			this.database.onStartUp();
		}
	}
	
	public void loadDatabase() {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(String worldName : mdatabase.getStringArray(this.table, "WorldName", new ArrayList<String>())) {
				String where = "WorldName = '"+worldName+"'";
				ArrayList<Vector> spawns = this.instance.getParser()
						.parseStringToSpawns(mdatabase.getString(this.table, where, "Spawns", ""));
				String stringCuboid = mdatabase.getString(this.table, where, "Border", "");
				Cuboid border = null;
				if(stringCuboid != null) {
					border = Cuboid.getStringToCuboid(stringCuboid);
				}else{
					this.instance.logError("Loading World Database", "WorldDatabase", "loadDatabase()", "There is no String to convert to Cuboid");
					border = Cuboid.getStringToCuboid((String)ConfigSettings.LobbyPortalCuboid.getDefaultSetting());//Gets default cuboid (no null)
				}
				WorldManager manage = this.instance.getWorldManager();
				if(!manage.containsWorld(worldName)) {
					manage.addWorld(new World(worldName, spawns, border));
				}
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			if(this.database.getSection("Worlds") != null) {
				for(String worldName : this.database.getSection("Worlds")) {
					String prefix = "Worlds." + worldName + ".";
					ArrayList<Vector> spawns = this.instance.getParser()
							.parseStringToSpawns(this.database.getString(prefix + "Spawns", ""));
					Cuboid border = Cuboid.getStringToCuboid(this.database.getString(prefix + "Border", ""));
					WorldManager manage = this.instance.getWorldManager();
					if(!manage.containsWorld(worldName)) {
						manage.addWorld(new World(worldName, spawns, border));
					}
				}
			}
		}
	}
	
	public boolean containsWorld(String world) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(String worldName : mdatabase.getStringArray(this.table, "WorldName", new ArrayList<String>())) {
				if(worldName.equalsIgnoreCase(world)) {
					return true;
				}
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(String worldName : this.database.getSection("Worlds")) {
				if(worldName.equalsIgnoreCase(world)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void saveWorlds() {
		for(World world : this.instance.getWorldManager().getWorlds()) {
			world.updateLocations();
			this.saveWorld(world);
		}
	}
	
	public void deleteWorld(World world) {
		if(this.manager.useMySQL(DatabaseType.saveType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			if(this.containsWorld(world.getWorldName())) {
				String where = "WorldName = '" + world.getWorldName() + "'";
				mdatabase.delete(this.table, where);
			}
		}
		
		if(this.manager.useYAML(DatabaseType.saveType)) {
			
		}
	}
	
	public void saveWorld(World world) {
		if(this.manager.useMySQL(DatabaseType.saveType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			if(this.containsWorld(world.getWorldName())) {
				String where = "WorldName = '" + world.getWorldName() + "'";
				world.updateLocations();
				mdatabase.update(this.table, "Spawns = '" + this.instance.getParser().parseSpawnsToString(world.getSpawns()) + "'", where);
				mdatabase.update(this.table, "Border = '" + Cuboid.getCuboidToString(world.getBorder()) + "'", where);
			}else{
				mdatabase.insert(this.table, "WorldName, Spawns, Border",  "'"+world.getWorldName()+"', '"
						+this.instance.getParser().parseSpawnsToString(world.getSpawns())+"', '"
						+Cuboid.getCuboidToString(world.getBorder())+"'");
			}
		}
		
		if(this.manager.useYAML(DatabaseType.saveType)) {
			
		}
	}
}
