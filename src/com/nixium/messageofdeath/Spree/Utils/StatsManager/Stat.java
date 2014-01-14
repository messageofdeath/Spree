package com.nixium.messageofdeath.Spree.Utils.StatsManager;

public class Stat {

	private int stat, changed;
	
	public Stat() {}
		
	public double getStat() {
		return this.stat;
	}
	
	public void setStat(int stat) {
		this.stat = stat;
	}
	
	public void addAmount(int amount) {
		this.stat += amount;
		this.changed += amount;
	}
	
	public void subAmount(int amount) {
		this.stat -= amount;
		this.changed -= amount;
	}
	
	public double getAmount() {
		return this.stat;
	}
	
	public double getChanged() {
		return this.changed;
	}
}
