package com.nixium.Spree.Utils.NPCManager;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.nixium.Spree.Spree;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class NPCManager {

	private Spree instance;
	private ArrayList<NPCData> data = new ArrayList<NPCData>();
	
	public NPCManager(Spree instance) {
		this.instance = instance;
	}
	
	public ArrayList<NPCData> getNPCData() {
		return this.data;
	}
	
	public boolean containsID(int id) {
		return this.getNPCData(id) != null;
	}
	
	public NPCData getNPCData(int id) {
		for(NPCData data : this.data) {
			if(data.getNPCID() == id) {
				return data;
			}
		}
		return null;
	}
	
	public NPCType getNPCType(int id) {
		return this.getNPCData(id).getNPCType();
	}
	
	public void removeNPC(int id) {
		this.data.remove(this.getNPCData(id));
		this.instance.getNPCDatabase().deleteNPC(id);
	}
	
	public void registerNPC(NPCType type, int id) {
		if(!this.containsID(id)) {
			this.data.add(new NPCData(type, id));
		}else{
			this.instance.logError("NPC", "NPCManager", "registerNPC(NPCType type, int id)", "Attempted to register a NPC under an id already used!");
		}
	}
	 
	public void createNPC(NPCType type, Location loc) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		NPC npc = registry.createNPC(EntityType.PLAYER, type.name());
		npc.spawn(loc);
		npc.setProtected(true);
		this.data.add(new NPCData(type, npc.getId()));
	}
}
