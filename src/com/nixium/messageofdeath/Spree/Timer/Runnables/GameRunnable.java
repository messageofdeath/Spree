package com.nixium.messageofdeath.Spree.Timer.Runnables;


import org.bukkit.entity.Player;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Game.Game;
import com.nixium.messageofdeath.Spree.Game.Enumerations.GameStatus;
import com.nixium.messageofdeath.Spree.Timer.SpreeRunnable;
import com.nixium.messageofdeath.Spree.Utils.SettingsManager.SettingType;
import com.nixium.messageofdeath.Spree.Utils.StatsManager.StatType;
import com.nixium.messageofdeath.Spree.Utils.UserManager.User;

public class GameRunnable extends SpreeRunnable {
		
	public GameRunnable(Spree instance) {
		super(instance, 1);
	}
	
	public void run() {
		Game game = this.instance.getGame();
		int seconds = game.getSeconds();
		if(game.getGameStatus() == GameStatus.InLobby) {
			if(seconds == 60) {
				game.sendBanner();
        		this.sendCountdown(game, true, "&7[&e1&7] &6minute until game starts!");
        	}else if(seconds%60 == 0) {
        		if(seconds > 0) {
        			game.sendBanner();
        			this.sendCountdown(game, true, "&7[&e"+seconds/60+"&7] &6minutes until game starts!");
        		}
        	}
			if(seconds == 30) {
				this.sendCountdown(game, true, "&7[&e30&7] &6seconds until game starts!");
        	}
        	if(seconds < 11 && seconds > 0) {
        		game.sendMessage(true, "&7[&e"+seconds+"&7] &6second until game starts!");
        	}
        	if(seconds == 0) {
        		game.startGame();
        	}else{
                game.setSeconds(seconds-1);
        	}
		}else if(game.getGameStatus() == GameStatus.InGame) {
			for(Player player : game.getOnlinePlayers()) {
				this.instance.getStatManager().getPlayerStats(player.getName()).getStat(StatType.PlayingTime).addAmount(1);
			}
			 
			if(seconds == 60) {
				game.sendBanner();
				this.sendCountdown(game, true, "&7[&e1&7] &6minute until game ends!");
        	}else if(seconds%60 == 0) {
        		if(seconds > 0) {
        			game.sendBanner();
        			this.sendCountdown(game, true, "&7[&e"+seconds/60+"&7] &6minutes until game ends!");
        		}
        	}
			if(seconds == 30) {
				this.sendCountdown(game, true, "&7[&e30&7] &6seconds until game ends!");
        	}
        	if(seconds < 11 && seconds > 5) {
        		game.sendMessage(true, "&7[&e"+seconds+"&7] &6second until game ends!");
        	}
        	if(seconds == 5) {
        		game.pendingEnd();
        	}else{
                game.setSeconds(seconds-1);
        	}
		}else if(game.getGameStatus() == GameStatus.PendingEnd) {
			if(seconds > 1) {
				game.sendMessage(true, "&7[&e"+seconds+"&7] &6second until game ends!");
				game.setSeconds(seconds-1);
			}
			if(seconds == 1) {
				game.sendMessage(true, "&7[&e"+seconds+"&7] &6second until game ends!");
				game.setSeconds(seconds-1);
			}
			if(seconds == 0) {
				game.endGame();
				game.sendBanner();
			}
		}
	}
	
	public void sendCountdown(Game game, boolean prefix, String message) {
		for(Player player : game.getOnlinePlayers()) {
			User user = new User(player);
			if(user.getPlayerSettings().getSetting(SettingType.ShowCountdown).getValue() == true) {
				game.sendMessage(prefix, player, message);
			}
		}
	}
}
