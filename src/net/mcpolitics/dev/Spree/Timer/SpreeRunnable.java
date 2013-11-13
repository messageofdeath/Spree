package net.mcpolitics.dev.Spree.Timer;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;

public abstract class SpreeRunnable {
	
	public Spree instance;
	public int seconds;
	public final int secondsRepeat;
	private static ArrayList<SpreeRunnable> runnables = new ArrayList<SpreeRunnable>();
	
	/**
	 * @param instance - Instance of the main class Spree
	 * @param secondsRepeat - Loop. Every time seconds reaches 0 it will run run().
	 */
	
 	public SpreeRunnable(Spree instance, int secondsRepeat) {
		this.instance = instance;
		this.secondsRepeat = secondsRepeat;
		this.seconds = this.secondsRepeat;
		runnables.add(this);
	}

	public abstract void run();
	
	public static ArrayList<SpreeRunnable> getRunnables() {
		return runnables;
	}
	
	public static void incrementSecond() {
		for(SpreeRunnable run : getRunnables()) {
			if(run.seconds == 0) {
				run.run();
				run.seconds = run.secondsRepeat;
			}
		}
	}
}
