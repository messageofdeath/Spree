package com.nixium.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.Spree.Utils.InventoryManager.CustomMenu;

public class PotionSelection extends CustomMenu {

	public PotionSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
