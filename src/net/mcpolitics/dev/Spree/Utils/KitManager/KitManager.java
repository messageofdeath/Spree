package net.mcpolitics.dev.Spree.Utils.KitManager;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;

public class KitManager {
	
	private Spree instance;
	private ArrayList<Kit> kits;
	public static final String INGAMEKIT = "In-Game-Kit-ONLY";
	
	
	public KitManager(Spree instance) {
		this.kits = new ArrayList<Kit>();
		this.instance = instance;
	}
	
	public ArrayList<Kit> getKits() {
		ArrayList<Kit> kits = new ArrayList<Kit>();
		for(Kit kit : this.kits) {
			kits.add(kit);
		}
		return this.kits;
	}
	
	public ArrayList<Kit> getKits(String name) {
		ArrayList<Kit> kits = new ArrayList<Kit>();
		for(Kit kit : this.getKits()) {
			if(kit.getPlayerName().equalsIgnoreCase(name)) {
				kits.add(kit);
			}
		}
		return kits;
	}
	
	public Kit getKit(String name, String kitName) {
		for(Kit kit : this.getKits(name)) {
			if(kit.getKitName().equalsIgnoreCase(kitName)) {
				return kit;
			}
		}
		return null;
	}
	
	public void addKit(Kit kit) {
		if(this.instance.getDonatorManager().hasPlayer(kit.getPlayerName())) {
			if(!this.hasKit(kit.getPlayerName(), kit.getKitName())) {
				this.kits.add(kit);
			}else{
				this.instance.logError("Kits", "KitManager", "addKit(Kit kit)", "Attempted to add a kit under a kitName already used for the player.");
			}
		}else{
			if(!this.hasKits(kit.getPlayerName())) {
				this.kits.add(kit);
			}else{
				this.instance.logError("Kits", "KitManager", "addKit(Kit kit)", "Attempted to add a kit under a player that is not a donator. Already has one.");
			}
		}
	}
	
	public void unloadKits(String name) {
		for(Kit kit : this.getKits(name)) {
			this.removeKit(name, kit.getKitName());
		}
	}
	
	public void removeKit(String name, String kitName) {
		if(this.hasKits(name)) {
			this.kits.remove(this.getKit(name, kitName));
		}else{
			this.instance.logError("Kits", "KitManager", "removeKit(String name)", "Attempted to remove a kit that doesn't exist");
		}
	}
	
	public void deleteKit(String name, String kitName) {
		this.instance.getKitDatabase().deleteKit(name, kitName);
		this.removeKit(name, kitName);
	}
	
	public void deleteKitsOnShutDown() {
		for(Kit kit : this.getKits()) {
			if(kit.isDeleteOnShutdown()) {
				this.deleteKit(kit.getPlayerName(), kit.getKitName());
				this.removeKit(kit.getPlayerName(), kit.getKitName());
			}
		}
	}
	
	public void loadKits(String name) {
		this.instance.getKitDatabase().loadKits(name);
	}
	
	public boolean hasKits(String name) {
		return !this.getKits(name).isEmpty();
	}
	
	public boolean hasKit(String name, String kitName) {
		return this.getKit(name, kitName) != null;
	}
}
