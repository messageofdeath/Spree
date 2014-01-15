package com.nixium.Spree.Utils.PointManager;

import org.bukkit.inventory.ItemStack;

public class PointData {

	private ItemStack item;
	private int pointValue;
	
	public PointData(ItemStack item, int pointValue) {
		this.item = item;
		this.pointValue = pointValue;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
	public int getPointValue() {
		return this.pointValue;
	}
}
