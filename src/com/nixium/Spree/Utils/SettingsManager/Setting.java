package com.nixium.Spree.Utils.SettingsManager;

public class Setting {
	
	private boolean setting;
	
	public Setting() {}
		
	public boolean getValue() {
		return this.setting;
	}
	
	public void setValue(boolean setting) {
		this.setting = setting;
	}
}
