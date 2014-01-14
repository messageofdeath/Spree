package com.nixium.messageofdeath.Spree.Database.Databases;

import java.util.ArrayList;
import java.util.logging.Level;

import me.messageofdeath.Database.MySQLDatabase;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.KitManager.Item;
import com.nixium.messageofdeath.Spree.Utils.KitManager.Kit;

public class KitDatabase {
	
	private final String table = "Spree_Kits";
	private Spree instance;
	
	public KitDatabase(Spree instance) {
		this.instance = instance;
	}

	public void initDatabase() {		
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			String columns = "Player VARCHAR(20), KitName VARCHAR(50), Inventory TEXT, Armor TEXT, Hidden BOOLEAN, DeleteOnShutdown BOOLEAN";
			if(this.instance.getMySQL().getInstance().createTable(this.table, columns)) {
				this.instance.getServer().getLogger().log(Level.INFO, "Created the table '"+this.table+"'!");
			}
		}else{
			this.instance.getServer().getLogger().warning("[KitDatabase] Could not load mysql database!");
		}
	}
	
	public void loadKits(String name) {
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			String where = "Player = '"+name+"'";
			for(String kits : mdatabase.getStringArray(this.table, "Player = '"+name+"'", "KitName", new ArrayList<String>())) {
				where = "Player = '"+name+"' AND KitName = '"+kits+"'";
				boolean isHidden = mdatabase.getBoolean(this.table, where, "Hidden", false);
				boolean isDeleteOnShutdown = mdatabase.getBoolean(this.table, where, "DeleteOnShutdown", false);
				this.instance.getKitManager().addKit(new Kit(this.instance, name, kits, isHidden, isDeleteOnShutdown));
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
			}
		}
	}
	
	public void deleteKit(String name, String kitName) {
		MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
		if(mdatabase.checkConnection()) {
			if(mdatabase.contains(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'")) {
				mdatabase.delete(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'");
			}
		}
	}
	
	public void saveKits() {
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			for(Player player : this.instance.getGame().getOnlinePlayers()) {
				if(this.hasKits(player.getName())) {
					this.saveKits(player.getName());
				}
			}
		}
	}
	
	public void saveKits(String name) {
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			for(Kit kit : this.instance.getKitManager().getKits(name)) {
				if(this.hasKit(name, kit.getKitName())) {
					//Update
					mdatabase.update(this.table, "Inventory = '"+kit.toStringItem()+"' AND Armor = '"+kit.toStringArmor()
							+"' AND Hidden = "+kit.isHidden()+" AND DeleteOnShutdown = "+kit.isDeleteOnShutdown(), "Player = '"+name+"' AND KitName = '"+kit.getKitName()+"'");
				}else{
					//Insert
					mdatabase.insert(this.table, "Player, KitName, Inventory, Armor, Hidden, DeleteOnShutdown", "'"+name+"', '"+kit.getKitName()
							+"', '"+kit.toStringItem()+"', '"+kit.toStringArmor()+"', " + kit.isHidden() + ", " + kit.isDeleteOnShutdown());
				}
			}
		}
	}
	
	public boolean hasKit(String name, String kitName) {
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			return mdatabase.contains(this.table, "Player = '"+name+"' AND KitName = '"+kitName+"'");
		}
		return false;
	}
	
	public boolean hasKits(String name) {
		if(this.instance.getMySQL().getInstance().checkConnection()) {
			MySQLDatabase mdatabase = this.instance.getMySQL().getInstance();
			return mdatabase.contains(this.table, "Player = '"+name+"'");
		}
		return false;
	}
}
