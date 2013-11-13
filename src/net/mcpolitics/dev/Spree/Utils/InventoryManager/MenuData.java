package net.mcpolitics.dev.Spree.Utils.InventoryManager;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuData {

	private ItemStack item, item1;
	private Material mat, mat1;
	private Inventory inv;
	private boolean isInventory, isMaterial, isItemStack, isItemStackInventory;
	
	public MenuData(Material mat, Material mat1) {
		this.mat = mat;
		this.mat1 = mat1;
		this.isMaterial = true;
		this.isInventory = false;
		this.isItemStack = false;
		this.isItemStackInventory = false;
	}
	
	public MenuData(Material item, Inventory inv) {
		this.mat = item;
		this.inv = inv;
		this.isMaterial = false;
		this.isInventory = true;
		this.isItemStack = false;
		this.isItemStackInventory = false;
	}
	
	public MenuData(ItemStack item, ItemStack item1) {
		this.item = item;
		this.item1 = item1;
		this.isMaterial = false;
		this.isInventory = false;
		this.isItemStack = true;
		this.isItemStackInventory = false;
	}
	
	public MenuData(ItemStack item, Inventory mat1) {
		this.item = item;
		this.inv = mat1;
		this.isMaterial = false;
		this.isInventory = false;
		this.isItemStack = false;
		this.isItemStackInventory = true;
	}
	
	public boolean isInventory() {
		return this.isInventory;
	}
	
	public boolean isMaterial() {
		return this.isMaterial;
	}
	
	public boolean isItemStack() {
		return this.isItemStack;
	}
	
	public boolean isItemStackInventory() {
		return this.isItemStackInventory;
	}
	
	public Material getKey() {
		return this.mat;
	}
	
	public Material getMaterialValue() {
		return this.mat1;
	}
		
	public ItemStack getItemStackKey() {
		return this.item;
	}
	
	public ItemStack getItemStackValue() {
		return this.item1;
	}
	
	public Inventory getInventoryValue() {
		return inv;
	}
}
