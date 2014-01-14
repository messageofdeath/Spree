package com.nixium.messageofdeath.Spree.Timer;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Timer.Runnables.DatabaseRunnable;
import com.nixium.messageofdeath.Spree.Timer.Runnables.GameRunnable;

public class PluginTimer implements Runnable {
	
	private Spree instance;
	private boolean start = true, run = true;
	
	public PluginTimer(Spree instance) {
		this.instance = instance;
	}
	
	@Override
	public void run() {
		if(run == true) {
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
	
	public void setRun(boolean run) {
		this.run = run;
	}
}
