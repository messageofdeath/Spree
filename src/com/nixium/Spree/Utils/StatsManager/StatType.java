package com.nixium.Spree.Utils.StatsManager;

public enum StatType {
	
	Rank(),
	
	LongestSpree(),
	
	Shutdowns(),
	
	Kills(),
		
	DeathsByPlayers(),//KD Ratio   Kills / (DeathsByPlayers + DeathsByEnviroment)
	
	DeathsByEnviroment(),//KK Ratio    Kills / DeathsByPlayers
	
	GamesWon(),
	
	GamesLost(),
	
	PlayingTime();
	
	
	public String getName() {
		return this.toString();
	}
	
	public String getType() {
		return "INT";
	}
}
