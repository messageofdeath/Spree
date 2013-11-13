package net.mcpolitics.dev.Spree.Utils.WorldManager;

import java.util.ArrayList;
import java.util.Random;

import net.mcpolitics.dev.Spree.Spree;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class WorldManager {

	private ArrayList<World> worlds;
	private Spree instance;
	
	public WorldManager(Spree instance) {
		this.worlds = new ArrayList<World>();
		this.instance = instance;
	}
	
	public void loadWorld(World world) {
		if(world != null) {
			this.instance.getServer().createWorld(new WorldCreator(world.getWorldName()));
		}else{
			this.instance.logError("World", "WorldManager", "loadWorld(World world)"
					, "Attempted to load a world that doesn't exist.");
		}
	}
	
	public boolean isLoaded(World world) {
		if(world != null) {
			for(org.bukkit.World worlds : Bukkit.getWorlds()) {
				if(worlds.getName().equalsIgnoreCase(world.getWorldName())) {
					return true;
				}
			}
		}else{
			this.instance.logError("World", "WorldManager", "isLoaded(World world)", "Attempted to check if a world existed with world null.");
		}
		return false;
	}
	
	public void unloadWorld(World world) {
		this.instance.getServer().unloadWorld(world.getWorldName(), false);
	}
	
	public ArrayList<World> getWorlds() {
		return this.worlds;
	}
	
	public World getRandomWorld() {
		if(!this.worlds.isEmpty()) {
			return this.worlds.get(new Random().nextInt(this.worlds.size()));
		}else{
			this.instance.logError("World", "WorldManager", "getRandomWorld()"
					, "Attempted to get a world and there are no worlds available.");
			return null;
		}
	}
	
	public World getWorld(String worldName) {
		worldName = worldName.replaceAll("_", " ");
		for(World world : getWorlds()) {
			if(world.getWorldName().equalsIgnoreCase(worldName)) {
				return world;
			}
		}
		return null;
	}
	
	public void addWorld(World world) {
		if(!this.containsWorld(world.getWorldName())) {
			this.worlds.add(world);
		}else{
			this.instance.logError("World", "WorldManager", "addWorld(World world)"
					, "Attempted to add a world under a name that already exists");
		}
	}
	
	public void createWorld(World world) {
		this.instance.getWorldDatabase().saveWorld(world);
		this.addWorld(world);
	}
	
	public void deleteWorld(World world) {
		this.instance.getWorldDatabase().deleteWorld(world);
		this.removeWorld(world.getWorldName());
	}
	
	public void removeWorld(String world) {
		world = world.replaceAll("_", " ");
		World world1 = this.getWorld(world);
		this.worlds.remove(world1);
	}
	
	public boolean containsWorld(String world) {
		world = world.replaceAll("_", " ");
		return this.getWorld(world) != null;
	}
}
