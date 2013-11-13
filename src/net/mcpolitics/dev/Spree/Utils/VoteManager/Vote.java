package net.mcpolitics.dev.Spree.Utils.VoteManager;

import net.mcpolitics.dev.Spree.Utils.WorldManager.World;

public class Vote {

	private String name;
	private World world;
	private boolean changed;
	
	public Vote(String name, World world) {
		this.name = name;
		this.world = world;
		this.changed = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public boolean isChanged() {
		return this.changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
}
