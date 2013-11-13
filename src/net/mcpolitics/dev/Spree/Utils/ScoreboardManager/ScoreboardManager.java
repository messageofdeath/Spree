package net.mcpolitics.dev.Spree.Utils.ScoreboardManager;

import net.mcpolitics.dev.Spree.Spree;

import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

	private Spree instance;
	private Scoreboard scoreBoard;
	
	public ScoreboardManager(Spree instance) {
		this.instance = instance;
		this.scoreBoard = this.instance.getServer().getScoreboardManager().getNewScoreboard();
		this.scoreBoard.getClass();
	}
}
