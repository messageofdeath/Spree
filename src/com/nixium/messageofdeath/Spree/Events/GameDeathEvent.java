package com.nixium.messageofdeath.Spree.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameDeathEvent extends Event {
	
	private Player victim;
	private Entity attacker;
	
	private static final HandlerList handlers = new HandlerList();

	public GameDeathEvent(Player victim, Entity attacker) {
		this.victim = victim;
		this.attacker = attacker;
	}
	
	public Player getVictim() {
		return this.victim;
	}
	
	public Entity getAttacker() {
		return this.attacker;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
