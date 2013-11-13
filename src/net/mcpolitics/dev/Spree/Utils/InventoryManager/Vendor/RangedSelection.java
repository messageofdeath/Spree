package net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomInventory;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class RangedSelection extends CustomMenu {
		
	public RangedSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.BOW, ChatColor.DARK_RED + "Fire Bow", lore, Enchantment.ARROW_FIRE), 2, 2);
		bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.BOW, ChatColor.BLUE + "Infinite Bow", lore, Enchantment.ARROW_INFINITE), 2, 4);
		bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.BOW, ChatColor.GREEN + "Punch Bow", lore, Enchantment.ARROW_KNOCKBACK), 2, 6);
		bow = new ItemStack(Material.FISHING_ROD);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.FISHING_ROD, ChatColor.GRAY + "Fishing Rod", lore), 2, 8);
		bow = new ItemStack(Material.ARROW, 2);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.ARROW, ChatColor.GRAY + "Arrow", lore, 2), 3, 3);
		bow = new ItemStack(Material.ARROW, 16);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.ARROW, ChatColor.GRAY + "Arrow", lore, 16), 3, 5);
		bow = new ItemStack(Material.ARROW, 32);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(bow));
		super.setItem(super.getCustomItem(Material.ARROW, ChatColor.GRAY + "Arrow", lore, 32), 3, 7);
		lore.clear();
		lore.add(ChatColor.RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.DARK_RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 4, 9);
	}

	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.BOW, 0), super.getItem(Material.BOW, 0));
		super.setAction(super.getItem(Material.BOW, 1), super.getItem(Material.BOW, 1));
		super.setAction(super.getItem(Material.BOW, 2), super.getItem(Material.BOW, 2));
		super.setAction(super.getItem(Material.ARROW, 0), super.getItem(Material.ARROW, 0));
		super.setAction(super.getItem(Material.ARROW, 1), super.getItem(Material.ARROW, 1));
		super.setAction(super.getItem(Material.ARROW, 2), super.getItem(Material.ARROW, 2));
		super.setAction(super.getItem(Material.FISHING_ROD), super.getItem(Material.FISHING_ROD));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
