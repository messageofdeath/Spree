package net.mcpolitics.dev.Spree.Game;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Database.Configuration.ConfigSettings;
import net.mcpolitics.dev.Spree.Game.Enumerations.EndReason;
import net.mcpolitics.dev.Spree.Game.Enumerations.GameStatus;
import net.mcpolitics.dev.Spree.Utils.KitManager.Kit;
import net.mcpolitics.dev.Spree.Utils.KitManager.KitManager;
import net.mcpolitics.dev.Spree.Utils.StatsManager.StatType;
import net.mcpolitics.dev.Spree.Utils.StreakManager.Streak;
import net.mcpolitics.dev.Spree.Utils.UserManager.User;
import net.mcpolitics.dev.Spree.Utils.WorldManager.World;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.messageofdeath.Blocks.Cuboid;
import me.messageofdeath.Blocks.Vector;

public class Game {

	private Spree instance;
	private GameStatus gameStatus;
	private EndReason endReason;
	private Streak longestStreak;
	private World world;
	private Vector lobbyVector;
	private Cuboid lobbyPortalCuboid;
	private ArrayList<String> players, cameBack;
	private int seconds;
	
	private static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 ");
	private static final int LOBBYTIME = 60 * 2;//60 * 5
	private static final int GAMETIME = 60 * 10;
	private static final int PENDINGENDTIME = 5;
	
	public Game(Spree instance) {
		this.instance = instance;
		this.gameStatus = GameStatus.InLobby;
		this.seconds = Game.LOBBYTIME;
		this.longestStreak = null;
		try {
			this.lobbyVector = Vector.getStringToVector((String)ConfigSettings.LobbySpawnPoint.getSetting());
		}catch(Exception e) {
			this.instance.logError("Game", "Game", "Game Constructor", "Error Converting String to Vector");
		}
		try {
			this.lobbyPortalCuboid = Cuboid.getStringToCuboid((String)ConfigSettings.LobbyPortalCuboid.getSetting());
		}catch(Exception e) {
			this.instance.logError("Game", "Game", "Game Constructor", "Error Converting String to Cuboid");
		}
		this.players = new ArrayList<String>();
		this.cameBack = new ArrayList<String>();
	}
	
	// ************************ Game Status ************************
	
	public GameStatus getGameStatus() {
		return this.gameStatus;
	}
	
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	// *********************** Communication ************************
	
	public void sendMessage(boolean prefix, String message) {
		for(Player player : this.getOnlinePlayers()) {
			this.sendMessage(prefix, player, message);
		}
		this.sendConsoleMessage(message);
	}
	
	public void sendMessage(boolean prefix, Player player, String message) {
		player.sendMessage((prefix ? Game.PREFIX : "") + ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public void sendConsoleMessage(String message) {
		this.instance.getServer().getConsoleSender().sendMessage(Game.PREFIX + ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public void sendBanner() {
		String banner = "This game is brought to you by " + ChatColor.DARK_RED + "MC" + ChatColor.DARK_BLUE + "Politics" 
				+ ChatColor.GOLD + "! You can " + ChatColor.DARK_RED + "donate" + ChatColor.GOLD + " at \"mcpolitics.buycraft.net\"";
		for(Player player : this.getOnlinePlayers()) {
			if(!this.instance.getDonatorManager().hasPlayer(player.getName())) {
				this.sendMessage(true, player, banner);
			}
		}
		this.sendConsoleMessage(banner);
	}
	
	public void sendVoteStats() {
		this.sendMessage(true, "Voting Statistics:");
		ArrayList<String> statistics = this.instance.getVoteManager().getVoteStats();
		if(!statistics.isEmpty()) {
			for(String str : statistics) {
				this.sendMessage(false, str);
			}
		}else{
			this.sendMessage(false, ChatColor.DARK_RED + "    There are no worlds to vote for.");
		}
	}
	
	// ********************** Streak *************************
	
	public Streak getLongestStreak() {
		return this.longestStreak;
	}
	
	public void setLongestStreak(Streak longestStreak) {
		this.longestStreak = longestStreak;
	}
	
	public void checkStreak() {
		this.instance.getStreakManager().updateStreak();
	}
	
	// ************************ Worlds ************************
	
	public World getWorld() {
		return this.world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public boolean isInBorder(Player player) {
		return this.getWorld().getBorder().contains(player.getLocation());
	}
	
	// ************************* Players ***************************
	
	public ArrayList<Player> getOnlinePlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for(String player : this.players) {
			if(this.instance.getServer().getOfflinePlayer(player).isOnline()) {
				players.add(this.instance.getServer().getPlayer(player));
			}
		}
		return players;
	}
	
	public boolean containsPlayer(String name) {
		return this.players.contains(name);
	}
	
	public void addPlayer(String name) {
		if(!this.containsPlayer(name)) {
			this.players.add(name);
		}
	}
	
	public void removePlayer(String name) {
		this.players.remove(name);
	}
	
	public void sendToLobby() {
		for(Player player : this.getOnlinePlayers()) {
			this.sendToLobby(player);
		}
	}
	
	public void sendToLobby(Player player) {
		player.teleport(this.lobbyVector.getLocation());
	}
	
	public void usePortal(Player player) {
		User user = new User(player);
		if(!user.hasKit(KitManager.INGAMEKIT)) {
			user.addKit(new Kit(this.instance, user.getName(), KitManager.INGAMEKIT, true, true));
		}else{
			user.deleteKit(KitManager.INGAMEKIT);
			user.addKit(new Kit(this.instance, user.getName(), KitManager.INGAMEKIT, true, true));
		}
		this.worldSpawn(player);
	}
	
	public void worldSpawn(Player player) {
		try {
			this.getWorld().randomSpawn(player);
		}catch(Exception e) {
			this.instance.logError("World Spawn", "Game", "worldSpawn(Player player)", "World not loaded. Attempting to load world.");
			this.instance.getWorldManager().loadWorld(this.getWorld());
		}
	}
	
	public boolean isInPortal(Player player) {
		return this.lobbyPortalCuboid.contains(player.getLocation());
	}
	
	public void addPlayerLeft(String name) {
		if(!this.containsPlayerLeft(name)) {
			this.cameBack.add(name);
		}else{
			this.instance.logError("Players Left", "Game", "addPLayerleft(String)", "Attempted to add a player when they already exist.");
		}
	}
	
	public void removePlayerLeft(String name) {
		this.cameBack.remove(name);
	}
	
	public boolean containsPlayerLeft(String name) {
		return this.cameBack.contains(name);
	}
	
	// ************************ End Reason ************************
	
	public EndReason getEndReason() {
		return this.endReason;
	}
	
	public void setEndReason(EndReason endReason) {
		this.endReason = endReason;
	}
	
	// ********************* Seconds ****************************
	
	public int getSeconds() {
		return this.seconds;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	// ************************* Starting Game **************************
	
	public void pendingStartGame() {
		if(!this.instance.getWorldManager().getWorlds().isEmpty()) {
			if(this.instance.getVoteManager().getVotes().isEmpty()) {
				this.setWorld(this.instance.getWorldManager().getRandomWorld());
			}else{
				this.setWorld(this.instance.getVoteManager().getWorldVote());
				this.instance.getVoteManager().resetVotes();
			}
			this.sendMessage(true, "The map '"+this.getWorld().getWorldName()+"' has been chosen!");
			if(!this.instance.getWorldManager().isLoaded(this.getWorld())) {
				this.instance.getWorldManager().loadWorld(this.getWorld());
				this.getWorld().updateLocations();
			}
		}else{
			this.instance.logError("Pending Start Game", "Game", "pendingStartGame()", "There are no worlds available for use.");
		}
	}
	
	public void startGame() {
		this.pendingStartGame();
		if(this.players.size() < (Integer)ConfigSettings.RequiredAmountOfPlayers.getSetting()) {
			this.sendMessage(true, "There are not enough players to continue....");
			this.instance.logError("Game", "Game", "startGame()", "Not enough players to continue");
			this.endGame();
			return;
		}
		for(Player player : this.getOnlinePlayers()) {
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			this.usePortal(player);
		}
		this.setGameStatus(GameStatus.InGame);
		this.seconds = Game.GAMETIME;
	}
	
	// ************************** Ending Game ************************
	
	public void forceEnd() {
		this.setEndReason(EndReason.Forced);
		this.pendingEnd();
	}
	
	public void pendingEnd() {
		if(this.getEndReason() == EndReason.Forced) {
			this.sendMessage(true, "The game has been forced closed.");
		}
		this.setGameStatus(GameStatus.PendingEnd);
		this.seconds = Game.PENDINGENDTIME;
	}
	
	public void endGame() {
		this.setGameStatus(GameStatus.InLobby);
		this.seconds = Game.LOBBYTIME;
		this.sendToLobby();
		if(this.instance.getWorldManager().isLoaded(this.getWorld())) {
			this.instance.getWorldManager().unloadWorld(this.getWorld());
		}
		this.instance.getStatDatabase().saveDatabase();
		if(this.getEndReason() != EndReason.NotEnoughPlayers) {
			this.resetVariables();
		}else{
			this.endReason = null;
		}
	}
	
	public void announceWinner() {
		this.sendMessage(true, this.getLongestStreak().getName() + " has won with a streak of " 
				+ ChatColor.DARK_RED + this.getLongestStreak().getStreak());
		User user = new User(this.getLongestStreak().getName());
		user.getPlayerStats().getStat(StatType.GamesWon).addAmount(1);
		for(Player player : this.getOnlinePlayers()) {
			User users = new User(player);
			users.getPlayerStats().getStat(StatType.GamesLost).addAmount(1);
		}
	}
	
	public void resetVariables() {
		this.endReason = null;
		this.longestStreak = null;
		this.instance.getStreakManager().resetStreaks();
		for(Player player : this.getOnlinePlayers()) {
			User user = new User(player);
			user.reset();
		}
	}
}
