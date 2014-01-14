package com.nixium.messageofdeath.Spree.Utils.InventoryManager;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import com.nixium.messageofdeath.Spree.Spree;


public abstract class CustomInventory {

	protected Spree instance;
	private String inventoryName;
	protected ArrayList<CustomMenu> inventories;
	
	public CustomInventory(Spree instance, String inventoryName) {
		this.instance = instance;
		this.inventoryName = inventoryName;
		this.inventories = new ArrayList<CustomMenu>();
		Bukkit.getScheduler().runTaskLater(this.instance, new Runnable() {
			@Override
			public void run() {
				setupMenus();
			}
		}, 1 * 20L);
	}
	
	public String getInventoryName() {
		return this.inventoryName;
	}
	
	public abstract void setupMenus();

	public CustomMenu getMenuByIcon(Material icon) {
		for(CustomMenu menu : this.inventories) {
			if(!menu.isMainMenu()) {
				if(menu.getIcon() == icon) {
					return menu;
				}
			}
		}
		return this.getMainMenu();
	}
	
	public boolean hasMenu(Inventory inv) {
		return this.getMenu(inv) != null;
	}
	
	public CustomMenu getMainMenu() {
		for(CustomMenu menu : this.inventories) {
			if(menu.isMainMenu()) {
				return menu;
			}
		}
		return null;
	}
	
	public CustomMenu getMenu(Inventory inv) {
		for(CustomMenu inv1 : this.inventories) {
			if(inv1.getInventory().getTitle().equalsIgnoreCase(inv.getTitle())) {
				return inv1;
			}
		}
		return null;
	}
}
