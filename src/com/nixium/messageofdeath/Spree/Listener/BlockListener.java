package com.nixium.messageofdeath.Spree.Listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent event) {
		if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
		}else{
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent event) {
		if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
		}else{
			event.setCancelled(true);
		}
	}
}
