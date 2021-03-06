package com.nixium.Spree.Utils.StaffManager;

import com.nixium.Spree.Spree;

public class StaffManager {
	
	private Spree instance;
	
	public StaffManager(Spree instance) {
		this.instance = instance;
	}
	
	public StaffType getStaffType(String name) {
		return this.instance.getStaffDatabase().getStaffType(name);
	}
	
	public boolean hasPlayer(String name) {
		return this.instance.getStaffDatabase().hasPlayer(name);
	}
}
