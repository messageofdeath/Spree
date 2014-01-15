package com.nixium.Spree.Utils.SettingsManager;

public enum SettingType {
	
	OwnDeathMessages(), // Implemented
	
	NoDeathMessages(), // Implemented
	
	AllDeathMessages(), // Implemented
	 
	JoinMessages(), // Implemented
	
	LeaveMessages(), // Implemented
	
	ShowCountdown(), // Implemented
	
	ShowScoreboard(); // Not Implemented
	
	
	public String getName() {
		return this.toString();
	}
	
	public String getType() {
		return "BOOLEAN";
	}
}
