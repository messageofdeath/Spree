package com.nixium.messageofdeath.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class LeggingSelection extends CustomMenu {
	
	public LeggingSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_LEGGINGS)));
		super.setItem(super.getCustomItem(Material.DIAMOND_LEGGINGS, ChatColor.AQUA + "Diamond Leggings", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_LEGGINGS)));
		super.setItem(super.getCustomItem(Material.GOLD_LEGGINGS, ChatColor.GOLD + "Gold Leggings", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_LEGGINGS)));
		super.setItem(super.getCustomItem(Material.IRON_LEGGINGS, ChatColor.WHITE + "Iron Leggings", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.CHAINMAIL_LEGGINGS)));
		super.setItem(super.getCustomItem(Material.CHAINMAIL_LEGGINGS, ChatColor.GRAY + "Chainmail Leggings", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.LEATHER_LEGGINGS)));
		super.setItem(super.getCustomItem(Material.LEATHER_LEGGINGS, ChatColor.DARK_GRAY + "Leather Leggings", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_LEGGINGS), super.getItem(Material.DIAMOND_LEGGINGS));
		super.setAction(super.getItem(Material.GOLD_LEGGINGS), super.getItem(Material.GOLD_LEGGINGS));
		super.setAction(super.getItem(Material.IRON_LEGGINGS), super.getItem(Material.IRON_LEGGINGS));
		super.setAction(super.getItem(Material.CHAINMAIL_LEGGINGS), super.getItem(Material.CHAINMAIL_LEGGINGS));
		super.setAction(super.getItem(Material.LEATHER_LEGGINGS), super.getItem(Material.LEATHER_LEGGINGS));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
