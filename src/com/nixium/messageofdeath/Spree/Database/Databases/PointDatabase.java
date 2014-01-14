package com.nixium.messageofdeath.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Database.Types.DatabaseType;
import com.nixium.messageofdeath.Spree.Utils.PointManager.PointData;

public class PointDatabase {
	
	private Spree instance;
	private YamlDatabase database;
	private final String table = "Spree_Points";
	
	public PointDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType) || this.instance.getDatabaseManager().useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Item VARCHAR(50), Enchantment VARCHAR(50), PointValue INT";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[PointDatabase] Could not load mysql database!");
			}
        }
		if(this.instance.getDatabaseManager().useYAML(DatabaseType.loadType) || this.instance.getDatabaseManager().useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "points");
			this.database.onStartUp();
		}
	}
	
	public void loadDatabase() {
		MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
		if(this.instance.getDatabaseManager().useMySQL(DatabaseType.loadType)) {
			for(String item : mdatabase.getStringArray(this.table, "Item", new ArrayList<String>())) {
				ItemStack i = this.instance.getItemDatabase().getMaterial(item);
				int values = this.getAmountOfValues(item);
				if(values <= 1) {
					String enchantment = mdatabase.getString(this.table, "Item = '"+item+"'", "Enchantment", null);
					if(enchantment != null) {
						i.addEnchantment(Enchantment.getByName(enchantment), 1);
					}
					int pointValue = mdatabase.getInteger(this.table, "Item = '"+item+"'", "PointValue", 0);
					this.instance.getPointManager().addItem(new PointData(i, pointValue));
				}else{
					int amount = 0;
					for(PointData data : this.instance.getPointManager().getPointDatas()) {
						if(data.getItem().getType() == i.getType()) {
							amount++;
						}
					}
					amount++;
					String enchantment = this.getEnchantmentWithEnchantment(item, amount);
					if(enchantment != null) {
						i.addEnchantment(Enchantment.getByName(enchantment), 1);
					}
					int pointValue = this.getPointValueWithEnchantment(item, amount);
					this.instance.getPointManager().addItem(new PointData(i, pointValue));
				}
			}
		}
	}
	
	public int getAmountOfValues(String item) {
		return this.instance.getMySQL().getInstance().getAmountOfValues(this.table, "Item = '"+item+"'", 1);
	}
	
	public String getEnchantmentWithEnchantment(String item, int value) {
		MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
		int maxValue = this.getAmountOfValues(item);
		if(value <= maxValue) {
			return mdatabase.getStringSpecific(this.table, "Item = '"+item+"'", "Enchantment", value, null);
		}
		return null;
	}
	
	public int getPointValueWithEnchantment(String item, int value) {
		MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
		int maxValue = this.getAmountOfValues(item);
		if(value <= maxValue) {
			String enchantment = mdatabase.getStringSpecific(this.table, "Item = '"+item+"'", "Enchantment", value, null);
			if(enchantment != null) {
				return mdatabase.getInteger(this.table, "Item = '"+item+"' AND Enchantment = '"+enchantment+"'", "PointValue", 0); 
			}
		}
		return 0;
	}
}
