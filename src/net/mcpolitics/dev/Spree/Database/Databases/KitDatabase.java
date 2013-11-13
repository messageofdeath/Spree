package net.mcpolitics.dev.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import me.messageofdeath.Database.MySQLDatabase;
import me.messageofdeath.Database.YamlDatabase;
import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.DatabaseManager;
import net.mcpolitics.dev.Spree.Database.Types.DatabaseType;
import net.mcpolitics.dev.Spree.Utils.KitManager.Item;
import net.mcpolitics.dev.Spree.Utils.KitManager.Kit;

import org.bukkit.inventory.ItemStack;

public class KitDatabase {
	
	private final String table = "Spree_Kits";
	private DatabaseManager manager;
	private YamlDatabase database;
	private Spree instance;
	
	public KitDatabase(Spree instance) {
		this.instance = instance;
	}

	public void initDatabase() {
		this.manager = this.instance.getDatabaseManager();
		
		if(this.manager.useMySQL(DatabaseType.loadType) || this.manager.useMySQL(DatabaseType.saveType)) {
			if(this.instance.getMySQL().success == true) {
				String columns = "Player VARCHAR(20), KitName VARCHAR(50), Inventory TEXT, Armor TEXT, Hidden BOOLEAN, DeleteOnShutdown BOOLEAN";
				if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
					this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
				}
			}else{
				this.instance.getServer().getLogger().warning("[KitDatabase] Could not load mysql database!");
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType) || this.manager.useYAML(DatabaseType.saveType)) {
			this.database = new YamlDatabase(this.instance, "kits");
			this.database.onStartUp();
		}
	}
	
	public void loadKits(String name) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			String where = "Player = '"+name+"'";
			for(String kits : mdatabase.getStringArray(this.table, "Player = '"+name+"'", "KitName", new ArrayList<String>())) {
				where = "Player = '"+name+"' AND KitName = '"+kits+"'";
				this.instance.getKitManager().addKit(new Kit(this.instance, name, kits, false, false));
				ArrayList<ItemStack> invContents = new ArrayList<ItemStack>();
				for(Item item : Kit.parseStringToItems(mdatabase.getString(this.table, where, "Inventory", ""))) {
					invContents.add(item.getItemStack());
				}
				this.instance.getKitManager().getKit(name, kits).setContents(invContents);
				ArrayList<ItemStack> item = new ArrayList<ItemStack>();
				for(Item i : Kit.parseStringToItems(mdatabase.getString(this.table, where, "Armor", ""))) {
					item.add(i.getItemStack());
				}
				this.instance.getKitManager().getKit(name, kits).setArmorContents(item);
				this.instance.getKitManager().getKit(name, kits).setHidden(mdatabase.getBoolean(this.table, where, "Hidden", false));
				this.instance.getKitManager().getKit(name, kits).setDeleteOnShutdown(mdatabase.getBoolean(this.table, where, "DeleteOnShutdown", false));
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(String kits : this.database.getSection(name + ".Kits")) {
				this.instance.getKitManager().addKit(new Kit(this.instance, name, kits, false, false));
				ArrayList<ItemStack> invContents = new ArrayList<ItemStack>();
				for(Item item : Kit.parseStringToItems(this.database.getString(name + ".Kits." + kits + ".Inventory", ""))) {
					invContents.add(item.getItemStack());
				}
				this.instance.getKitManager().getKit(name, kits).setContents(invContents);
				ArrayList<ItemStack> item = new ArrayList<ItemStack>();
				for(Item i : Kit.parseStringToItems(this.database.getString(name + ".Kits." + kits + ".Armor", ""))) {
					item.add(i.getItemStack());
				}
				this.instance.getKitManager().getKit(name, kits).setArmorContents(item);
			}
		}
	}
	
	public void deleteKit(String name, String kitName) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			if(mdatabase.contains(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'")) {
				mdatabase.delete(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'");
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			this.database.set(name + ".Kits." + kitName, null);
		}
	}
	
	public void saveKits(String name) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(Kit kit : this.instance.getKitManager().getKits(name)) {
				if(this.hasKit(name, kit.getKitName())) {
					//Update
					mdatabase.update(this.table, "Inventory = '"+kit.toStringItem()+"' AND Armor = '"+kit.toStringArmor()+"' AND Hidden = "
							+ kit.isHidden() + ", DeleteOnShutdown = " + kit.isDeleteOnShutdown(), "Player = '"+name+"' AND KitName = '"+kit.getKitName()+"'");
				}else{
					//Insert
					mdatabase.insert(this.table, "Player, KitName, Inventory, Armor, Hidden, DeleteOnShutdown", "'"+name+"', '"+kit.getKitName()
							+"', '"+kit.toStringItem()+"', '"+kit.toStringArmor()+"'"+ ", " + kit.isHidden() + ", " + kit.isDeleteOnShutdown());
				}
			}
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(Kit kit : this.instance.getKitManager().getKits(name)) {
				this.database.set(name + ".Kits." + kit.getKitName() + ".Inventory", kit.toStringItem());
				this.database.set(name + ".Kits." + kit.getKitName() + ".Armor", kit.toStringArmor());
			}
		}
	}
	
	public boolean hasKit(String name, String kitName) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			return mdatabase.contains(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'");
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			return this.database.contains(name + ".Kits." + kitName);
		}
		return false;
	}
	
	public boolean hasKits(String name) {
		if(this.manager.useMySQL(DatabaseType.loadType)) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			return mdatabase.contains(this.table, "Player = '"+name+"'");
		}
		
		if(this.manager.useYAML(DatabaseType.loadType)) {
			for(String namex : this.database.getSection(name + ".Kits")) {
				if(namex.equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
