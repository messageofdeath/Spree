package net.mcpolitics.dev.Spree.Timer.Runnables;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Configuration.ConfigSettings;
import net.mcpolitics.dev.Spree.Timer.SpreeRunnable;

public class DatabaseRunnable extends SpreeRunnable {
	
	public DatabaseRunnable(Spree instance) {
		super(instance, (Integer)ConfigSettings.DatabaseSaveInterval.getSetting() * 60);
	}

	@Override
	public void run() {
		this.instance.getDatabaseManager().saveDatabases();
	}
}
