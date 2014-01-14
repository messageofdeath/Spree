package com.nixium.messageofdeath.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class HelmetSelection extends CustomMenu {
		
	public HelmetSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}
	
	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_HELMET)));
		super.setItem(super.getCustomItem(Material.DIAMOND_HELMET, ChatColor.AQUA + "Diamond Helmet", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_HELMET)));
		super.setItem(super.getCustomItem(Material.GOLD_HELMET, ChatColor.GOLD + "Gold Helmet", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_HELMET)));
		super.setItem(super.getCustomItem(Material.IRON_HELMET, ChatColor.WHITE + "Iron Helmet", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.CHAINMAIL_HELMET)));
		super.setItem(super.getCustomItem(Material.CHAINMAIL_HELMET, ChatColor.GRAY + "Chainmail Helmet", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.LEATHER_HELMET)));
		super.setItem(super.getCustomItem(Material.LEATHER_HELMET, ChatColor.DARK_GRAY + "Leather Helmet", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_HELMET), super.getItem(Material.DIAMOND_HELMET));
		super.setAction(super.getItem(Material.GOLD_HELMET), super.getItem(Material.GOLD_HELMET));
		super.setAction(super.getItem(Material.IRON_HELMET), super.getItem(Material.IRON_HELMET));
		super.setAction(super.getItem(Material.CHAINMAIL_HELMET), super.getItem(Material.CHAINMAIL_HELMET));
		super.setAction(super.getItem(Material.LEATHER_HELMET), super.getItem(Material.LEATHER_HELMET));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
