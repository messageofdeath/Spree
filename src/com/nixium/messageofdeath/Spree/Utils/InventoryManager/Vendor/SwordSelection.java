package com.nixium.messageofdeath.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class SwordSelection extends CustomMenu {
	
	public SwordSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_SWORD)));
		super.setItem(super.getCustomItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Diamond Sword", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_SWORD)));
		super.setItem(super.getCustomItem(Material.GOLD_SWORD, ChatColor.GOLD + "Gold Sword", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_SWORD)));
		super.setItem(super.getCustomItem(Material.IRON_SWORD, ChatColor.WHITE + "Iron Sword", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.STONE_SWORD)));
		super.setItem(super.getCustomItem(Material.STONE_SWORD, ChatColor.DARK_GRAY + "Stone Sword", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.WOOD_SWORD)));
		super.setItem(super.getCustomItem(Material.WOOD_SWORD, ChatColor.DARK_GRAY + "Wood Sword", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_SWORD), super.getItem(Material.DIAMOND_SWORD));
		super.setAction(super.getItem(Material.GOLD_SWORD), super.getItem(Material.GOLD_SWORD));
		super.setAction(super.getItem(Material.IRON_SWORD), super.getItem(Material.IRON_SWORD));
		super.setAction(super.getItem(Material.STONE_SWORD), super.getItem(Material.STONE_SWORD));
		super.setAction(super.getItem(Material.WOOD_SWORD), super.getItem(Material.WOOD_SWORD));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
