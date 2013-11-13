package net.mcpolitics.dev.Spree.Listener;

import me.messageofdeath.Events.PlayerMovedBlockEvent;
import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Events.GameDeathEvent;
import net.mcpolitics.dev.Spree.Game.Enumerations.GameStatus;
import net.mcpolitics.dev.Spree.Utils.KitManager.KitManager;
import net.mcpolitics.dev.Spree.Utils.SettingsManager.SettingType;
import net.mcpolitics.dev.Spree.Utils.StatsManager.StatType;
import net.mcpolitics.dev.Spree.Utils.StreakManager.Streak;
import net.mcpolitics.dev.Spree.Utils.UserManager.User;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	
	private Spree instance;
	
	public PlayerListener(Spree instance) {
		this.instance = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent event) {
		//****************** Game Related *********************
		this.instance.getGame().addPlayer(event.getPlayer().getName());
		this.instance.getGame().sendToLobby(event.getPlayer());
		//****************** Regular Stuff *********************
		event.setJoinMessage("");
		String message = "";
		User user = new User(event.getPlayer());
		if(user.isLeft()) {
			message = event.getPlayer().getName() + " has come back.";
			user.removeLeft();
		}else{
			message = event.getPlayer().getName() + " has joined the game!";
		}
		user.reset();
		user.loadKits();
		for(Player player : this.instance.getGame().getOnlinePlayers()) {
			User users = new User(player);
			if(users.getPlayerSettings().getSetting(SettingType.JoinMessages).getValue() == true) {
				this.instance.getGame().sendMessage(true, player, message);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDrop(final PlayerDropItemEvent event) {
		this.instance.getServer().getScheduler().runTaskLater(this.instance, new Runnable() {
			@Override
			public void run() {
				//****************** Removes all items from the world *********************
				for(Entity entity : event.getPlayer().getWorld().getEntities()) {
					if(entity instanceof Item) {
						entity.remove();
					}
				}
			}
		}, 1L);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		final User user = new User(event.getPlayer());
		if(!user.isLeft()) {
			user.addLeft();
		}
		event.setQuitMessage("");
		for(Player player : this.instance.getGame().getOnlinePlayers()) {
			User users = new User(player);
			if(users.getPlayerSettings().getSetting(SettingType.LeaveMessages).getValue() == true) {
				this.instance.getGame().sendMessage(true, player, event.getPlayer().getName() + " has left the game!");
			}
		}
		this.instance.getServer().getScheduler().runTaskLater(this.instance, new Runnable() {
			@Override
			public void run() {
				if(!user.isOnline()) {
					if(user.isLeft()) {
						instance.getGame().removePlayer(user.getName());
						user.removeLeft();
						instance.getGame().sendMessage(true, user.getName() + "'s progress has been lost. Did not join back.");
						user.reset();
						user.unloadKits();
					}
				}
			}
		}, 60 * 2 * 20L);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMoveBlock(PlayerMovedBlockEvent event) {
		if(this.instance.getGame().isInPortal(event.getPlayer())) {
			if(this.instance.getGame().getGameStatus() != GameStatus.InLobby) {
				this.instance.getGame().usePortal(event.getPlayer());
			}else{
				this.instance.getGame().sendMessage(true, event.getPlayer(), "Please wait for the game to start.");
			}
		}
		if(this.instance.getGame().getGameStatus() == GameStatus.InGame) {
			if(!this.instance.getGame().isInBorder(event.getPlayer())) {
				if(event.getLocation().getWorld().getName().equalsIgnoreCase(this.instance.getGame().getWorld().getWorldName())) {
					this.instance.getGame().sendMessage(true, event.getPlayer(), "You have reached the edge of the world.");
					//event.getPlayer().setVelocity(event.getPlayer().getVelocity().multiply(-1)); //TODO Test this code to see if it works
				}	
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(GameDeathEvent event) {
		/************************ Run Death Methods *****************************/
		User user = new User(event.getVictim());
		if(event.getAttacker() instanceof Player) {
			this.deathMethod((Player)event.getAttacker(), StatType.Kills);
			this.deathMethod(event.getVictim(), StatType.DeathsByPlayers);
		}else{
			this.deathMethod(event.getVictim(), StatType.DeathsByEnviroment);
		}
		Player player = event.getVictim();
		Player attacker = (Player)event.getAttacker();
		attacker.setHealth(attacker.getMaxHealth());
		/****************** Reset victim's streak *********************/
		Streak streak = user.getStreak();
		streak.setStreak(0);
		/****************** Communication *********************/
		for(Player players : this.instance.getGame().getOnlinePlayers()) {
			User users = new User(players);
			if(!players.getName().equalsIgnoreCase(player.getName()) || !attacker.getName().equalsIgnoreCase(player.getName())) {
				if(users.getPlayerSettings().getSetting(SettingType.AllDeathMessages).getValue() == true) {
					this.instance.getGame().sendMessage(true, players, attacker.getName() + " killed " + player.getName());
				}
			}
		}
		if(user.getPlayerSettings().getSetting(SettingType.OwnDeathMessages).getValue() == true
				|| user.getPlayerSettings().getSetting(SettingType.AllDeathMessages).getValue() == true) {
			this.instance.getGame().sendMessage(true, player, "You died by " + ((Player)event.getAttacker()).getName());
		}
		user = new User(attacker);
		if(user.getPlayerSettings().getSetting(SettingType.OwnDeathMessages).getValue() == true
				|| user.getPlayerSettings().getSetting(SettingType.AllDeathMessages).getValue() == true) {
			this.instance.getGame().sendMessage(true, attacker, "You killed " + player.getName());
		}
		/****************** Streaks *********************/
		if(this.instance.getGame().getLongestStreak() != null) {
			if(this.instance.getGame().getLongestStreak().getName().equalsIgnoreCase(player.getName())) {
				user.getPlayerStats().getStat(StatType.Shutdowns).addAmount(1);
			}
		}
		/****************** Fix Victims Armor/Tools *********************/
		user = new User(player);
		user.setInventory(user.getKit(KitManager.INGAMEKIT));
		/****************** Check Streak *********************/
		this.instance.getGame().worldSpawn(player);
		this.instance.getGame().checkStreak();
	}
	
	public void deathMethod(Player player, StatType type) {
		User user = new User(player);
		if(user.hasStreak()) {
			Streak streak = user.getStreak();
			streak.setStreak(streak.getStreak() + 1);
		}else{
			Streak streak = user.createStreak();
			streak.setStreak(streak.getStreak() + 1);
		}
		user.getPlayerStats().getStat(type).addAmount(1);
	}
}
