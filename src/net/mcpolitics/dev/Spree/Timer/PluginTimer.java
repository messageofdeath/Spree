package net.mcpolitics.dev.Spree.Timer;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Timer.Runnables.DatabaseRunnable;
import net.mcpolitics.dev.Spree.Timer.Runnables.GameRunnable;

public class PluginTimer implements Runnable {
	
	private Spree instance;
	private boolean start = true;
	
	public PluginTimer(Spree instance) {
		this.instance = instance;
	}
	
	@Override
	public void run() {
		if(start == true) {
			new GameRunnable(this.instance);
			new DatabaseRunnable(this.instance);
			start = false;
		}
		for(SpreeRunnable run : SpreeRunnable.getRunnables()) {
			run.run();
		}
	}
}
