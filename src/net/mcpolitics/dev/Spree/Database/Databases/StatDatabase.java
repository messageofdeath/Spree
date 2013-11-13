package net.mcpolitics.dev.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Types.DatabaseType;
import net.mcpolitics.dev.Spree.Utils.StatsManager.PlayerStats;
import net.mcpolitics.dev.Spree.Utils.StatsManager.StatType;

import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;

public class StatDatabase {
	
	private final String table = "Spree_Stats";
	private YamlDatabase database;
	private Spree instance;
	
	public StatDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Player VARCHAR(18),";
		        for(StatType stats : StatType.values()) {
		        	columns += " " + stats.getName() + " " + stats.getType() + ",";
		        }
		        columns = columns.substring(0, columns.length() - 1);
		        if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
		        	this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[StatDatabase] Could not load mysql database!");
			}
        }
		
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "stats");
			this.database.onStartUp();
		}
	}
	
	public void loadPlayer(String name) {
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType)) {
			String prefix = "PlayerStats." + name + ".";
	    	ArrayList<Integer> stats = new ArrayList<Integer>();
	    	
	    	for(StatType type : StatType.values()) {
				stats.add(this.database.getInteger(prefix + type.getName(), 0));
			}
	    	
	    	this.instance.getStatManager().createPlayerStats(name, stats);
		}
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			ArrayList<Integer> stats = new ArrayList<Integer>();
			String where = "Player = '"+name+"'";

			for(StatType type : StatType.values()) {
				stats.add(mdatabase.getInteger(this.table, where, type.getName(), 0));
			}
			
			this.instance.getStatManager().createPlayerStats(name, stats);
		}
    }
	
	public void saveDatabase() {
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			for(PlayerStats player : this.instance.getStatManager().getAll()) {
				for(StatType stats : StatType.values()) {
					this.database.set("PlayerStats." + player.getName() + "." + stats.getName(), player.getStat(stats).getStat());
				}
			}
		}
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			for(PlayerStats player : this.instance.getStatManager().getAll()) {
				if(this.hasPlayer(player.getName())) {
					for(StatType stats : StatType.values())
						this.instance.getMySQL().getInstance()
							.update(this.table, stats.getName() + " = '" + player.getStat(stats).getStat() + "'", "Player = '"+player.getName()+"'");
				}else{
					String columns = "Player,";
					String values = "'" + player.getName() + "',";
					for(StatType stats : StatType.values()) {
						columns += " " + stats.getName() + ",";
						values += " '" + player.getStat(stats).getStat() + "',";
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
			return this.database.contains("PlayerStats." + name);
		return false;
	}
}
