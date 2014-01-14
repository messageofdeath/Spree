package com.nixium.messageofdeath.Spree.Listener;

import me.messageofdeath.Logging.Messenger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.CustomMenu;

public class InventoryListener implements Listener {
	
	private Spree instance;
	
	public InventoryListener(Spree instance) {
		this.instance = instance;
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player) {
			if(this.instance.getInventoryManager().hasInventory(event.getInventory())) {
				if(this.instance.getInventoryManager().hasMenu(event.getInventory())) {
					if(event.getSlot() != -999)/*To prevent null pointer (This is off the inventory screen click)*/ {
						if(event.getRawSlot() < event.getView().getTopInventory().getSize()) {
							CustomMenu inv = this.instance.getInventoryManager().getMenu(event.getInventory());
							if(event.getCurrentItem() != null) {
								if(event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem() != null) {
									inv.doAction((Player)event.getWhoClicked(), event.getCurrentItem());
									event.setCancelled(true);
								}else{
									event.setCancelled(true);
								}
							}
							inv.checkInventory();
						}else{
							event.setCancelled(true);
							Messenger msg = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
							if(event.getWhoClicked() instanceof Player) {
								msg.sendMessage((Player)event.getWhoClicked(), ChatColor.BLACK + "[" + ChatColor.DARK_RED + "Error" + ChatColor.BLACK + "] " + ChatColor.RED 
										+ "You cannot edit your inventory while interacting with the Vendor!", false);
							}
						}
					}
				}
			}
		}
	}
}
