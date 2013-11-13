package net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomInventory;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChestplateSelection extends CustomMenu {
	
	public ChestplateSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_CHESTPLATE)));
		super.setItem(super.getCustomItem(Material.DIAMOND_CHESTPLATE, ChatColor.AQUA + "Diamond Chestplate", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_CHESTPLATE)));
		super.setItem(super.getCustomItem(Material.GOLD_CHESTPLATE, ChatColor.GOLD + "Gold Chestplate", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_CHESTPLATE)));
		super.setItem(super.getCustomItem(Material.IRON_CHESTPLATE, ChatColor.WHITE + "Iron Chestplate", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.CHAINMAIL_CHESTPLATE)));
		super.setItem(super.getCustomItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GRAY + "Chainmail Chestplate", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.LEATHER_CHESTPLATE)));
		super.setItem(super.getCustomItem(Material.LEATHER_CHESTPLATE, ChatColor.DARK_GRAY + "Leather Chestplate", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_CHESTPLATE), super.getItem(Material.DIAMOND_CHESTPLATE));
		super.setAction(super.getItem(Material.GOLD_CHESTPLATE), super.getItem(Material.GOLD_CHESTPLATE));
		super.setAction(super.getItem(Material.IRON_CHESTPLATE), super.getItem(Material.IRON_CHESTPLATE));
		super.setAction(super.getItem(Material.CHAINMAIL_CHESTPLATE), super.getItem(Material.CHAINMAIL_CHESTPLATE));
		super.setAction(super.getItem(Material.LEATHER_CHESTPLATE), super.getItem(Material.LEATHER_CHESTPLATE));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
