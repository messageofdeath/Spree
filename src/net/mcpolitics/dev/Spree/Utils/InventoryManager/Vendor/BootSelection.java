package net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomInventory;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BootSelection extends CustomMenu {
		
	public BootSelection(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		super(instance, parentInventory, icon, title, isMainMenu, numOfRows);
	}

	@Override
	public void setupInventory() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.DIAMOND_BOOTS)));
		super.setItem(super.getCustomItem(Material.DIAMOND_BOOTS, ChatColor.AQUA + "Diamond Boots", lore), 2, 1);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.GOLD_BOOTS)));
		super.setItem(super.getCustomItem(Material.GOLD_BOOTS, ChatColor.GOLD + "Gold Boots", lore), 2, 3);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.IRON_BOOTS)));
		super.setItem(super.getCustomItem(Material.IRON_BOOTS, ChatColor.WHITE + "Iron Boots", lore), 2, 5);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.CHAINMAIL_BOOTS)));
		super.setItem(super.getCustomItem(Material.CHAINMAIL_BOOTS, ChatColor.GRAY + "Chainmail Boots", lore), 2, 7);
		lore.clear();
		lore.add(ChatColor.LIGHT_PURPLE + "Points: " + super.getPointValue(new ItemStack(Material.LEATHER_BOOTS)));
		super.setItem(super.getCustomItem(Material.LEATHER_BOOTS, ChatColor.DARK_GRAY + "Leather Boots", lore), 2, 9);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Back to the main menu.");
		super.setItem(super.getCustomItem(Material.APPLE, ChatColor.RED + "Back", lore), 1, 1);
		lore.clear();
		lore.add(ChatColor.DARK_RED + "Exit the menu.");
		super.setItem(super.getCustomItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Exit", lore), 3, 9);
	}
	
	@Override
	public void setupKeys() {
		super.setAction(super.getItem(Material.DIAMOND_BOOTS), super.getItem(Material.DIAMOND_BOOTS));
		super.setAction(super.getItem(Material.GOLD_BOOTS), super.getItem(Material.GOLD_BOOTS));
		super.setAction(super.getItem(Material.IRON_BOOTS), super.getItem(Material.IRON_BOOTS));
		super.setAction(super.getItem(Material.CHAINMAIL_BOOTS), super.getItem(Material.CHAINMAIL_BOOTS));
		super.setAction(super.getItem(Material.LEATHER_BOOTS), super.getItem(Material.LEATHER_BOOTS));
		super.setAction(Material.APPLE, this.getParentInventory().getMainMenu().getInventory());
		super.setAction(Material.REDSTONE_BLOCK, super.getExitInventory());
	}
}
