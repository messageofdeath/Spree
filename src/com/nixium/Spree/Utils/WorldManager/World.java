package com.nixium.Spree.Utils.WorldManager;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.messageofdeath.Blocks.Cuboid;
import me.messageofdeath.Blocks.Vector;

public class World {

	private String worldName;
	private ArrayList<Vector> spawns;
	private Cuboid border;
	
	public World(String worldName, ArrayList<Vector> spawns, Cuboid border) {
		this.worldName = worldName;
		this.spawns = spawns;
		this.border = border;
		
		if(this.spawns == null) {
			this.spawns = new ArrayList<Vector>();
		}
		
		if(this.border == null) {
			this.border = new Cuboid(new Vector(this.worldName, 1,1,1), new Vector(this.worldName, 1,2,1));
		}
	}
	
	public String getWorldName() {
		return this.worldName;
	}
	
	public void updateLocations() {
		for(Vector v : this.getSpawns()) {
			v.updateLocation();
		}
		this.getBorder().updateLocations();
	}
	
	public ArrayList<Vector> getSpawns() {
		return this.spawns;
	}
	
	public void randomSpawn(Player player) {
		if(!this.spawns.isEmpty()) {
			player.teleport(this.spawns.get(new Random().nextInt(this.spawns.size())).getLocation());
		}else{
			player.teleport(Bukkit.getWorld(this.getWorldName()).getSpawnLocation());
		}
	}
	
	public Cuboid getBorder() {
		return this.border;
	}
	
	//Adding and Removing
	
	public void addSpawn(Vector vector) {
		this.spawns.add(vector);
	}
	
	public void removeSpawn(int i) {
		if(i - 1 > -1) {
			this.spawns.remove(i - 1);
		}
	}
	
	public void removeSpawn(Vector vector) {
		this.spawns.remove(vector);
	}
	
	public void setBorder(Vector a, Vector b) {
		this.border = new Cuboid(a,b);
	}
}