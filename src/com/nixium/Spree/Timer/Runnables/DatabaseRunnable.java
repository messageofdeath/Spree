package com.nixium.Spree.Timer.Runnables;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Database.Configuration.ConfigSettings;
import com.nixium.Spree.Timer.SpreeRunnable;

public class DatabaseRunnable extends SpreeRunnable {
	
	public DatabaseRunnable(Spree instance) {
		super(instance, (Integer)ConfigSettings.DatabaseSaveInterval.getSetting() * 60);
	}

	@Override
	public void run() {
		this.instance.getDatabaseManager().saveDatabases();
	}
}
