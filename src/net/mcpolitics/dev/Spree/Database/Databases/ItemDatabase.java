package net.mcpolitics.dev.Spree.Database.Databases;

import java.util.HashMap;

import net.mcpolitics.dev.Spree.Spree;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.messageofdeath.Database.YamlDatabase;

public class ItemDatabase {

	private HashMap<String, ItemStack> itemNames;
	
	private YamlDatabase items;
	
	private Spree instance;
	
	public ItemDatabase(Spree instance) {
		this.instance = instance;
	}
	
	public void initDatabase() {
		this.items = new YamlDatabase(this.instance, "items");
		this.items.onStartUp();
		this.itemNames = new HashMap<String, ItemStack>();
	}
	
	public void loadDatabase() {
		if(this.itemNames == null) {
			this.itemNames = new HashMap<String, ItemStack>();
		}
		for(String key : this.items.getSection("")) {
			String[] value = this.items.getString(key, null).split(",");
			if(value != null) {
				if(!this.itemNames.containsKey(key.toUpperCase())) {
					this.itemNames.put(key.toUpperCase(), new ItemStack(Material.matchMaterial(value[0]), 1, Short.parseShort(value[1])));
				}else{
					this.instance.getServer().getLogger().severe("There is a duplicate custom item name in the items.yml!");
				}
			}
		}
	}
	
	//**************** Methods **************
	
	@SuppressWarnings("deprecation")
	public ItemStack getMaterial(String input) {
		input = input.toUpperCase();
		Material material = null;
		if(!StringUtils.isNumeric(input)) {
			if(input.contains(":")) {				
				String[] split = input.split(":");
				if(!StringUtils.isNumeric(split[1])) {
					return null;
				}
				if(!StringUtils.isNumeric(split[0])) {
					// diamondpick:1   STRING
					if(this.itemNames.containsKey(split[0])) {
						return new ItemStack(this.itemNames.get(split[0]).getType(), 1, Short.parseShort(split[1]));
					}else{
						material = Material.getMaterial(split[0]);
					}
					return new ItemStack(material, 1, Short.parseShort(split[1]));
				}else{
					// 1:1 INT
					material = Material.matchMaterial(split[0]);
					return new ItemStack(material, 1, Short.parseShort(split[1]));
				}
				
			}else{
				if(this.itemNames.containsKey(input)) {
					return new ItemStack(this.itemNames.get(input).getType(), 1, this.itemNames.get(input).getData().getData());
				}else{
					material = Material.matchMaterial(input);
				}
			}
		}else{
			material = Material.getMaterial(input);
		}
		if(material != null)
			return new ItemStack(material, 1);
		return null;
	}
}
