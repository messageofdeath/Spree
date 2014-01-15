package com.nixium.Spree.Utils.PointManager;

import java.util.ArrayList;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Utils.DonatorManager.DonationType;
import com.nixium.Spree.Utils.StaffManager.StaffType;

public class PointManager {

	private Spree instance;
	private ArrayList<PointData> items;
	
	public PointManager(Spree instance) {
		this.instance = instance;
		this.items = new ArrayList<PointData>();
	}
	
	public void addItem(PointData value) {
		if(!this.hasPointData(value.getItem())) {
			this.items.add(value);
		}else{
			this.instance.logError("Points", "PointManager", "addItem(PointData)", "Attempted to add a item that already exists ("+value.getItem().getType().name()+")");
		}
	}
	
	public int getMaxPoints(String name) {
		if(this.instance.getDonatorDatabase().hasPlayer(name)) {
			if(this.instance.getDonatorDatabase().getDonationType(name) == DonationType.Alpha
					|| this.instance.getStaffDatabase().getStaffType(name) == StaffType.President 
					|| this.instance.getStaffDatabase().getStaffType(name) == StaffType.VicePresident) {
				return 1500;
			}
			else if(this.instance.getDonatorDatabase().getDonationType(name) == DonationType.Republican
				|| this.instance.getDonatorDatabase().getDonationType(name) == DonationType.Democrat) {
				return 1150;
			}else{
				return 1000;
			}
		}else{
			return 1000;
		}
	}
	
	public ArrayList<PointData> getPointDatas() {
		return this.items;
	}
	
	public int calculatePoints(PlayerInventory inv) {
		int totalPoints = 0;
		for(ItemStack item : inv.getContents()) {
			if(item != null) {
				if(this.hasPointData(item)) {
					totalPoints += this.getPointValue(item);
				}
			}
		}
		for(ItemStack item : inv.getArmorContents()) {
			if(item != null) {
				if(this.hasPointData(item)) {
					totalPoints += this.getPointValue(item);
				}
			}
		}
		return totalPoints;
	}
	
	public int calculatePoints(PlayerInventory inv, ItemStack items) {
		int totalPoints = this.calculatePoints(inv);
		if(this.hasPointData(items)) {
			totalPoints += this.getPointValue(items);
		}
		return totalPoints;
	}
	
	public PointData getPointData(ItemStack item) {
		for(PointData i : this.items) {
			if(i.getItem().getType() == item.getType()) {
				if(i.getItem().getEnchantments().keySet().equals(item.getEnchantments().keySet())) {
					return i;
				}
			}
		}
		return null;
	}
	
	public boolean hasPointData(ItemStack item) {
		return this.getPointData(item) != null;
	}
	
	public int getPointValue(ItemStack item) {
		if(this.hasPointData(item)) {
			return this.getPointData(item).getPointValue() * item.getAmount();	
		}else{
			return 0;
		}
	}
}
