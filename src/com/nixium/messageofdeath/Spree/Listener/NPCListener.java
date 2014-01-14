package com.nixium.messageofdeath.Spree.Listener;

import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Events.NPCUseEvent;
import com.nixium.messageofdeath.Spree.Utils.NPCManager.NPCType;

public class NPCListener implements Listener {
	
	private Spree instance;
	
	public NPCListener(Spree instance) {
		this.instance = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNPCUse(NPCUseEvent event) {
		if(this.instance.getNPCManager().containsID(event.getNPC().getId())) {
			NPCType type = this.instance.getNPCManager().getNPCType(event.getNPC().getId());
			if(type == NPCType.Vendor) {
				event.getPlayer().openInventory(this.instance.getInventoryManager().getInventory(NPCType.Vendor.name()).getMainMenu().getInventory());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRightClick(NPCRightClickEvent event) {
		if(this.instance.getNPCManager().containsID(event.getNPC().getId())) {
			NPCType type = this.instance.getNPCManager().getNPCType(event.getNPC().getId());
			if(type == NPCType.Vendor) {
				event.getClicker().openInventory(this.instance.getInventoryManager().getInventory(NPCType.Vendor.name()).getMainMenu().getInventory());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNPCRemove(NPCRemoveEvent event) {
		if(this.instance.getNPCManager().containsID(event.getNPC().getId())) {
			this.instance.getNPCManager().removeNPC(event.getNPC().getId());
		}
	}
}