package com.nixium.messageofdeath.Spree.Timer.Runnables;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Database.Configuration.ConfigSettings;
import com.nixium.messageofdeath.Spree.Timer.SpreeRunnable;

public class DatabaseRunnable extends SpreeRunnable {
	
	public DatabaseRunnable(Spree instance) {
		super(instance, (Integer)ConfigSettings.DatabaseSaveInterval.getSetting() * 60);
	}

	@Override
	public void run() {
		this.instance.getDatabaseManager().saveDatabases();
	}
}
