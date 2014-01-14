package com.nixium.messageofdeath.Spree.Utils.InventoryManager;

import java.util.ArrayList;
import java.util.List;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.nixium.messageofdeath.Spree.Spree;

public abstract class CustomMenu {

	private int numOfRows;
	private Inventory inv;
	protected Spree instance;
	private boolean isMainMenu;
	private Material icon;
	private CustomInventory parentInventory;
	private ArrayList<Integer> registeredSlots = new ArrayList<Integer>();
	private ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
	private ArrayList<MenuData> data = new ArrayList<MenuData>();

	public CustomMenu(Spree instance, CustomInventory parentInventory, Material icon, String title, boolean isMainMenu, int numOfRows) {
		this.inv = Bukkit.createInventory(null, 9 * numOfRows, title);
		this.numOfRows = numOfRows;
		this.parentInventory = parentInventory;
		this.instance = instance;
		this.isMainMenu = isMainMenu;
		this.icon = icon;
	}
	
	public abstract void setupInventory();
	
	public abstract void setupKeys();
	
	public boolean isMainMenu() {
		return this.isMainMenu;
	}
	
	public CustomInventory getParentInventory() {
		return this.parentInventory;
	}
		
	public void doAction(Player player, ItemStack key) {
		if(this.hasMenuData(key)) {
			MenuData data = this.getMenuData(key);
			if(data.isInventory() || data.isItemStackInventory()) {
				player.closeInventory();
				if(data.getInventoryValue() != null) {
					player.openInventory(data.getInventoryValue());
				}
			}else{
				ItemStack items;
				if(data.isMaterial()) {
					items = new ItemStack(data.getMaterialValue());
				}else{
					items = data.getItemStackValue();
				}
				int totalPoints = this.instance.getPointManager().calculatePoints(player.getInventory(), items);
				if(totalPoints <= this.getMaxPoints(player.getName())) {
					player.getInventory().addItem(items);
				}else{
					player.sendMessage(ChatColor.DARK_RED + "You do not have enough points to spend.");
				}
			}
		}
	}
	
	public void checkInventory() {
		for(int i = 0; i < this.inv.getSize(); i++) {
			if(this.inv.getItem(i) != null) {
				if(!this.registeredSlots.contains(i)) {
					this.inv.setItem(i, null);
				}
			}
		}
	}
	
	public Inventory getExitInventory() {
		return null;
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public Material getIcon() {
		return this.icon;
	}
	
	public boolean hasIcon() {
		return this.icon != null;
	}
	
	public void showInventory(Player player) {
		player.closeInventory();
		player.openInventory(this.inv);
	}
	
	public MenuData getMenuData(ItemStack mat) {
		for(MenuData data : this.data) {
			if(data.isInventory() || data.isMaterial()) {
				if(data.getKey() == mat.getType()) {
					return data;
				}
			}
			if(data.isItemStackInventory() || data.isItemStack()) {
				if(data.getItemStackKey().getType() == mat.getType()) {
					if(data.getItemStackKey().getItemMeta().getLore().equals(mat.getItemMeta().getLore())) {
						return data;
					}
				}
			}
		}
		return null;
	}
	
	public boolean hasMenuData(ItemStack mat) {
		return this.getMenuData(mat) != null;
	}
	
	public void setAction(Material mat, Inventory inv) {
		this.data.add(new MenuData(mat, inv));
	}
	
	public void setAction(Material mat, Material mat1) {
		this.data.add(new MenuData(mat, mat1));
	}
	
	public void setAction(ItemStack mat, Inventory inv) {
		this.data.add(new MenuData(mat, inv));
	}
	
	public void setAction(ItemStack mat, ItemStack mat1) {
		this.data.add(new MenuData(mat, mat1));
	}
	
	public int getMaxPoints(String name) {
		return this.instance.getPointManager().getMaxPoints(name);
	}
	
	public int getPointValue(ItemStack itemStack) {
		if(itemStack != null) {
			return this.instance.getPointManager().getPointValue(itemStack);
		}else{
			return 0;
		}
	}
		
	public void setItem(ItemStack key, int row, int slot) {
		if(!(row > this.numOfRows)) {
			this.inv.setItem((row * 9) - 10 + slot, key);
			this.registeredSlots.add((row * 9) - 10 + slot);
			this.stacks.add(key);
		}
	}
	
	public ItemStack getItem(Material item) {
		for(ItemStack i : this.stacks) {
			if(i.getType() == item) {
				return i;
			}
		}
		return null;
	}
	
	public ItemStack getItem(Material item, int value) {
		int amount = -1;
		for(ItemStack i : this.stacks) {
			if(i.getType() == item) {
				amount++;
				if(amount == value) {
					return i;
				}
			}
		}
		return null;
	}
	
	public ItemStack getCustomItem(Material material, String displayName, List<String> lore) {
		ItemStack i = new ItemStack(material);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		i.setItemMeta(meta);
		return i;
	}
	
	public ItemStack getCustomItem(Material material, String displayName, List<String> lore, int amount) {
		ItemStack i = this.getCustomItem(material, displayName, lore);
		i.setAmount(amount);
		return i;
	}
	
	public ItemStack getCustomItem(Material material, String displayName, List<String> lore, Enchantment enchantment) {
		ItemStack i = this.getCustomItem(material, displayName, lore);
		i.addEnchantment(enchantment, 1);
		return i;
	}
	
	public ItemStack getCustomItem(Material material, String displayName, List<String> lore, Enchantment enchantment, int amount) {
		ItemStack i = this.getCustomItem(material, displayName, lore, enchantment);
		i.setAmount(amount);
		return i;
	}
}
