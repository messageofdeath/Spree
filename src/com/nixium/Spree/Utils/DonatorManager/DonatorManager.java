package com.nixium.Spree.Utils.DonatorManager;

import com.nixium.Spree.Spree;

public class DonatorManager {
	
	private Spree instance;
	
	public DonatorManager(Spree instance) {
		this.instance = instance;
	}
	
	public void addDonator(DonationType type, String name) {
		this.instance.getDonatorDatabase().addDonator(type, name);
	}
	
	public DonationType getDonationType(String name) {
		return this.instance.getDonatorDatabase().getDonationType(name);
	}
	
	public boolean hasPlayer(String name) {
		return this.instance.getDonatorDatabase().hasPlayer(name);
	}
}
