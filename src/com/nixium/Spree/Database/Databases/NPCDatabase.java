package com.nixium.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Database.DatabaseManager;
import com.nixium.Spree.Database.Types.DatabaseType;
import com.nixium.Spree.Utils.NPCManager.NPCData;
import com.nixium.Spree.Utils.NPCManager.NPCType;

import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;

public class NPCDatabase {
	
	private final String table = "Spree_NPC";
	private DatabaseManager manager;
	private YamlDatabase database;
	private Spree instance;
	
	public NPCDatabase(Spree instance) {
		this.instance = instance;
	}

	public void initDatabase() {
		this.manager = this.instance.getDatabaseManager();
		
		if(this.manager.useMySQL(DatabaseType.loadType) || this.manager.useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "NpcID INT, NpcType VARCHAR(30)";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[NPCDatabase] Could not load mysql database!");
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType) || this.manager.useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "npcs");
			this.database.onStartUp();
		}
	}
	
	public void loadDatabase() {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(Object i : mdatabase.getArray(this.table, "NpcID", new ArrayList<Object>())) {
				if(i instanceof Integer) {
					int id = (Integer)i;
					try {
						this.instance.getNPCManager().registerNPC(NPCType.valueOf(mdatabase.getString(this.table, "NpcID = '"+id+"'", "NpcType", "")), id);
					}catch(Exception e) {
						this.instance.logError("NPC", "NPCDatabase", "loadDatabase()", "Unknown value for npc type.");
					}
				}
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(String i : this.database.getSection("")) {
				try {
					int id = Integer.parseInt(i);
					this.instance.getNPCManager().registerNPC(NPCType.valueOf(this.database.getString(id + ".NpcType", "")), id);
				}catch(Exception e) {
					this.instance.logError("NPC", "NPCDatabase", "loadDatabase()", "Unknown value for npc type.");
				}
			}
		}
	}
	
	public boolean containsID(int id) {
		return this.instance.getMySQL().getInstance().contains(this.table, "NpcID = '"+id+"'");
	}
	
	public void deleteNPC(int id) {
		if(this.manager.useMySQL(DatabaseType.loadType) || this.manager.useMySQL(DatabaseType.saveType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			mdatabase.delete(this.table, "NpcID = '"+id+"'");
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			this.database.set(id + "", null);
		}
	}
	
	public void saveDatabase() {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(NPCData data : this.instance.getNPCManager().getNPCData()) {
				if(this.containsID(data.getNPCID())) {
					//Update
					mdatabase.update(this.table, "NpcType = '"+data.getNPCType().name()+"'", "NpcID = '"+data.getNPCID()+"'");
				}else{
					//Insert
					mdatabase.insert(this.table, "NpcID, NpcType", "'"+data.getNPCID()+"', '"+data.getNPCType().name()+"'");
				}
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(NPCData data : this.instance.getNPCManager().getNPCData()) {
				this.database.set(data.getNPCID() + "", data.getNPCType().name());
			}
		}
	}
}
