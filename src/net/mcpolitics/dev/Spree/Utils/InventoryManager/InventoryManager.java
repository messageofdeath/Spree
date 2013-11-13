package net.mcpolitics.dev.Spree.Utils.InventoryManager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor.VendorInventory;
import net.mcpolitics.dev.Spree.Utils.NPCManager.NPCType;

public class InventoryManager {

	private Spree instance;
	private ArrayList<CustomInventory> inventories;
	
	public InventoryManager(Spree instance) {
		this.instance = instance;
		this.inventories = new ArrayList<CustomInventory>();
		Bukkit.getScheduler().runTaskLater(this.instance, new Runnable() {
			@Override
			public void run() {
				setupInventories();
			}
		}, 1L);
	}
	
	public void setupInventories() {
		this.inventories.add(new VendorInventory(this.instance, NPCType.Vendor.name()));
	}
	
	public boolean hasMenu(Inventory inv) {
		return this.getMenu(inv) != null;
	}
	
	public CustomMenu getMenu(Inventory inv) {
		return this.getInventory(inv).getMenu(inv);
	}
	
	public CustomInventory getInventory(String name) {
		for(CustomInventory invs : this.inventories) {
			if(invs.getInventoryName().equalsIgnoreCase(name)) {
				return invs;
			}
		}
		return null;
	}
	
	public CustomInventory getInventory(Inventory inv) {
		for(CustomInventory invs : this.inventories) {
			if(invs.hasMenu(inv)) {
				return invs;
			}
		}
		return null;
	}
}
