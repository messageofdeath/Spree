package net.mcpolitics.dev.Spree.Listener;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Events.GameDeathEvent;
import net.mcpolitics.dev.Spree.Events.NPCUseEvent;
import net.mcpolitics.dev.Spree.Game.Enumerations.GameStatus;
import net.mcpolitics.dev.Spree.Utils.KitManager.KitManager;
import net.mcpolitics.dev.Spree.Utils.UserManager.User;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EntityListener implements Listener {
	
	private Spree instance;
	
	public EntityListener(Spree instance) {
		this.instance = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onExplode(EntityExplodeEvent event) {
		event.blockList().clear();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSpawn(CreatureSpawnEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNPCUse(EntityDamageByEntityEvent event) {
		if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
			if(event.getDamager() instanceof Player) {
				NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getEntity());
				Player player = (Player)event.getDamager();
				Bukkit.getServer().getPluginManager().callEvent(new NPCUseEvent(player, npc));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
		event.setDeathMessage("");
		if(event.getEntity().getKiller() != null) {
			Bukkit.getPluginManager().callEvent(new GameDeathEvent(event.getEntity(), event.getEntity().getKiller()));	
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent event) {
		if(this.instance.getGame().getGameStatus() != GameStatus.InLobby) {
			User user = new User(event.getPlayer());
			user.setInventory(user.getKit(KitManager.INGAMEKIT));
			this.instance.getGame().worldSpawn(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHealthChange(EntityDamageByEntityEvent event) {
		if(this.instance.getGame().getWorld() != null) {
			if(event.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(this.instance.getGame().getWorld().getWorldName())) {
				if(this.instance.getGame().getGameStatus() != GameStatus.InLobby) {
					if(event.getEntity() instanceof Player) {
						Player victim = (Player)event.getEntity();
						if(!this.instance.getGame().containsPlayer(victim.getName())) {
							return;
						}
						if(victim.getHealth() - event.getDamage() <= 0) {
							Bukkit.getPluginManager().callEvent(new GameDeathEvent(victim, event.getDamager()));
							event.setCancelled(true);
							victim.setFoodLevel(20);
							victim.setHealth(victim.getMaxHealth());
						}
					}
				}else{
					event.setCancelled(true);
				}
			}else{
				event.setCancelled(true);
			}
		}else{
			event.setCancelled(true);
		}
	}
}
