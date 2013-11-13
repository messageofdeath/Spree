package net.mcpolitics.dev.Spree.Utils.KitManager;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

	private Spree instance;
	private String playerName, kitName;
	private boolean isHidden, deleteOnShutdown;
	private ArrayList<Item> items, armor;
	
	public Kit(Spree instance, String playerName, String kitName, boolean isHidden, boolean deleteOnShutdown) {
		this.playerName = playerName;
		this.kitName = kitName;
		this.isHidden = isHidden;
		this.deleteOnShutdown = deleteOnShutdown;
		this.armor = new ArrayList<Item>();
		this.items = new ArrayList<Item>();
	}
	
	public boolean isHidden() {
		return this.isHidden;
	}
	
	public boolean isDeleteOnShutdown() {
		return this.deleteOnShutdown;
	}
	
	public void setHidden(boolean hidden) {
		this.isHidden = hidden;
	}
	
	public void setDeleteOnShutdown(boolean shutdown) {
		this.deleteOnShutdown = shutdown;
	}
	
	public void setContents(ArrayList<ItemStack> item) {
		for(ItemStack i : item) {
			if(i != null) {
				this.items.add(new Item(i));
			}
		}
	}
	
	public void setArmorContents(ArrayList<ItemStack> item) {
		for(ItemStack i : item) {
			if(i != null) {
				this.armor.add(new Item(i));
			}
		}
	}
	
	public ArrayList<Item> getItems() {
		return this.items;
	}
	
	public ArrayList<Item> getArmor() {
		return this.armor;
	}
	
	public void setKit(Player player) {
		int totalPointValue = this.instance.getPointManager().calculatePoints(player.getInventory());
		if(totalPointValue <= this.instance.getPointManager().getMaxPoints(player.getName())) {
			ArrayList<ItemStack> invContents = new ArrayList<ItemStack>();
			for(ItemStack item: player.getInventory().getContents()) {
				invContents.add(item);
			}
			this.setContents(invContents);
			ArrayList<ItemStack> armorContents = new ArrayList<ItemStack>();
			for(ItemStack item: player.getInventory().getArmorContents()) {
				armorContents.add(item);
			}
			this.setArmorContents(armorContents);
		}
	}
	
	public ItemStack getItem(ItemStack item) {
		for(Item itemx : this.items) {
			if(itemx.getItemStack().isSimilar(item)) {
				return itemx.getItemStack();
			}
		}
		return null;
	}
	
	public boolean hasItem(ItemStack item) {
		return this.getItem(item) != null;
	}
	
	public void removeItem(ItemStack item) {
		if(this.hasItem(item)) {
			this.items.remove(this.getItem(item));
		}else{
			this.instance.logError("Kits", "Kits", "removeItem(ItemStack item)", "Tried to remove an item that doesn't exist");
		}
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public String getKitName() {
		return this.kitName;
	}
	
	public String toStringArmor() {
		String str = "";
		// item|item|item
		ArrayList<Item> items = this.getArmor();
		if(!items.isEmpty()) {
			for(Item item : items) {
				if(str.isEmpty()) {
					str += item.toString();
				}else{
					str += "|" + item.toString();
				}
			}
		}
		return str;
	}
	
	public String toStringItem() {
		String str = "";
				// item|item|item
		ArrayList<Item> items = this.getItems();
		if(!items.isEmpty()) {
			for(Item item : items) {
				if(str.isEmpty()) {
					str += item.toString();
				}else{
					str += "|" + item.toString();
				}
			}
		}
		return str;
	}
	
	public static ArrayList<Item> parseStringToItems(String parse) {
		ArrayList<Item> array = new ArrayList<Item>();
		String[] list = parse.split("\\|");
		for(String p : list) {
			array.add(new Item(Item.parseStringToItemStack(p)));
		}
		return array;
	}
}
