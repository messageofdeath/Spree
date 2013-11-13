package net.mcpolitics.dev.Spree.Events;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCUseEvent extends Event {

	private Player player;
	private NPC npc;
	
	private static final HandlerList handlers = new HandlerList();

	public NPCUseEvent(Player player, NPC npc) {
		this.player = player;
		this.npc = npc;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public NPC getNPC() {
		return this.npc;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
