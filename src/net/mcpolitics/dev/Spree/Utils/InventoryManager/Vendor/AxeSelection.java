package net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomInventory;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AxeSelection extends CustomMenu {

	public AxeSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_AXE)));
		super.setItem(super.getCustomItem(Material.DIAMOND_AXE, ChatColor.AQUA + "Diamond Axe", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_AXE)));
		super.setItem(super.getCustomItem(Material.GOLD_AXE, ChatColor.GOLD + "Gold Axe", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_AXE)));
		super.setItem(super.getCustomItem(Material.IRON_AXE, ChatColor.WHITE + "Iron Axe", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.STONE_AXE)));
		super.setItem(super.getCustomItem(Material.STONE_AXE, ChatColor.DARK_GRAY + "Stone Axe", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.WOOD_AXE)));
		super.setItem(super.getCustomItem(Material.WOOD_AXE, ChatColor.DARK_GRAY + "Wood Axe", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_AXE), super.getItem(Material.DIAMOND_AXE));
		super.setAction(super.getItem(Material.GOLD_AXE), super.getItem(Material.GOLD_AXE));
		super.setAction(super.getItem(Material.IRON_AXE), super.getItem(Material.IRON_AXE));
		super.setAction(super.getItem(Material.STONE_AXE), super.getItem(Material.STONE_AXE));
		super.setAction(super.getItem(Material.WOOD_AXE), super.getItem(Material.WOOD_AXE));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
