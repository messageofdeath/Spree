package com.nixium.messageofdeath.Spree.Utils.ScoreboardManager;


import org.bukkit.scoreboard.Scoreboard;

import com.nixium.messageofdeath.Spree.Spree;

public class ScoreboardManager {

	private Spree instance;
	private Scoreboard scoreBoard;
	
	public ScoreboardManager(Spree instance) {
		this.instance = instance;
		this.scoreBoard = this.instance.getServer().getScoreboardManager().getNewScoreboard();
		this.scoreBoard.getClass();
	}
}
