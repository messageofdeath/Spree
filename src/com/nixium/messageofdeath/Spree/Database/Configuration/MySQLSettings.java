package com.nixium.messageofdeath.Spree.Database.Configuration;

public enum MySQLSettings {

	Host(),
	
	
	
	Port(),
	
	
	
	Database(),
	
	
	
    Username(),
	
	
	
	Password();
	
	//********* Getting ***************
    
	private String setting;
	
    public String getName() {
        return this.toString();
    }
	
	public String getSetting() {
		return setting;
	}
    
    public void setSetting(String setting) {
        this.setting = setting;
    }
    
    public String getDefaultSetting() {
    	if(this.getName().equalsIgnoreCase(MySQLSettings.Host.getName()))
    		return "localhost";
    	if(this.getName().equalsIgnoreCase(MySQLSettings.Port.getName()))
    		return "3306";
    	if(this.getName().equalsIgnoreCase(MySQLSettings.Database.getName()))
    		return "minecraft";
    	if(this.getName().equalsIgnoreCase(MySQLSettings.Username.getName()))
    		return "root";
    	if(this.getName().equalsIgnoreCase(MySQLSettings.Password.getName()))
    		return "";
    	return "";
    }
}
