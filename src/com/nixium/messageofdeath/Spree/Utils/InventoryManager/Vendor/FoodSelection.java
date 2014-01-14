package com.nixium.messageofdeath.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class FoodSelection extends CustomMenu {
	
	public FoodSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLDEN_APPLE, 2)));
		super.setItem(super.getCustomItem(Material.GOLDEN_APPLE, ChatColor.GOLD + "Golden Apple", lore, 2), 2, 5);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.GOLDEN_APPLE), super.getItem(Material.GOLDEN_APPLE));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
