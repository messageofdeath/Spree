package com.nixium.messageofdeath.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomInventory;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class MainMenu extends CustomMenu {
		
	public MainMenu(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}
	
	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a helmet that fits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_HELMET, ChatColor.AQUA + "Helmets", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a chestplate that fits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_CHESTPLATE, ChatColor.AQUA + "Chestplates", lore), 2, 4);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a sword that suits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Swords", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a legging that fits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_LEGGINGS, ChatColor.AQUA + "Leggings", lore), 2, 6);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a pair of boots that fits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_BOOTS, ChatColor.AQUA + "Boots", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Feeling the magic? Try some potions.");
		lore.add(ChatColor.AQUA + "Unavailable");
		super.setItem(super.getCustomItem(Material.BREWING_STAND_ITEM, ChatColor.DARK_RED + "Potions", lore), 3, 4);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a bow that suits you!");
		super.setItem(super.getCustomItem(Material.BOW, ChatColor.DARK_RED + "Bows", lore), 4, 5);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Pick a Axe that suits you!");
		super.setItem(super.getCustomItem(Material.DIAMOND_AXE, ChatColor.AQUA + "Axes", lore), 3, 5);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "You're stomach growling? Pick some food.");
		super.setItem(super.getCustomItem(Material.COOKED_BEEF, ChatColor.DARK_RED + "Food", lore), 3, 6);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 5, 9);
	}
	
	public void setupKeys() {
		CustomInventory manager = this.getParentInventory();
		super.setAction(Material.DIAMOND_HELMET, manager.getMenuByIcon(Material.DIAMOND_HELMET).getInventory());
		super.setAction(Material.DIAMOND_CHESTPLATE, manager.getMenuByIcon(Material.DIAMOND_CHESTPLATE).getInventory());
		super.setAction(Material.DIAMOND_SWORD, manager.getMenuByIcon(Material.DIAMOND_SWORD).getInventory());
		super.setAction(Material.DIAMOND_LEGGINGS, manager.getMenuByIcon(Material.DIAMOND_LEGGINGS).getInventory());
		super.setAction(Material.DIAMOND_BOOTS, manager.getMenuByIcon(Material.DIAMOND_BOOTS).getInventory());
		super.setAction(Material.DIAMOND_AXE, manager.getMenuByIcon(Material.DIAMOND_AXE).getInventory());
		//super.setAction(Material.BREWING_STAND_ITEM, manager.getMenuByIcon(Material.BREWING_STAND_ITEM).getInventory());
		super.setAction(Material.BOW, manager.getMenuByIcon(Material.BOW).getInventory());
		super.setAction(Material.COOKED_BEEF, manager.getMenuByIcon(Material.COOKED_BEEF).getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
