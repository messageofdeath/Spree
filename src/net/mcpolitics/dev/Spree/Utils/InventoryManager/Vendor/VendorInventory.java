package net.mcpolitics.dev.Spree.Utils.InventoryManager.Vendor;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomInventory;
import net.mcpolitics.dev.Spree.Utils.InventoryManager.CustomMenu;

public class VendorInventory extends CustomInventory {

	public VendorInventory(Spree instance, String inventoryName) {
		super(instance, inventoryName);
	}

	@Override
	public void setupMenus() {
		this.inventories.add(new MainMenu(this.instance, this, Material.AIR, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Main Menu", true, 5));
		this.inventories.add(new HelmetSelection(this.instance, this, Material.DIAMOND_HELMET, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Helmet Selection", false, 3));
		this.inventories.add(new ChestplateSelection(this.instance, this, Material.DIAMOND_CHESTPLATE, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Chestplate Selection", false, 3));
		this.inventories.add(new SwordSelection(this.instance, this, Material.DIAMOND_SWORD, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Sword Selection", false, 3));
		this.inventories.add(new AxeSelection(this.instance, this, Material.DIAMOND_AXE, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Axe Selection", false, 3));
		this.inventories.add(new LeggingSelection(this.instance, this, Material.DIAMOND_LEGGINGS, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Legging Selection", false, 3));
		this.inventories.add(new BootSelection(this.instance, this, Material.DIAMOND_BOOTS, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Boot Selection", false, 3));
		this.inventories.add(new PotionSelection(this.instance, this, Material.BREWING_STAND_ITEM, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Potion Selection", false, 3));
		this.inventories.add(new RangedSelection(this.instance, this, Material.BOW, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Ranged Selection", false, 4));
		this.inventories.add(new FoodSelection(this.instance, this, Material.COOKED_BEEF, ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Food Selection", false, 3));
		for(CustomMenu menu : this.inventories) {
			menu.setupInventory();
		}
		for(CustomMenu menu : this.inventories) {
			menu.setupKeys();
		}
	}
}
