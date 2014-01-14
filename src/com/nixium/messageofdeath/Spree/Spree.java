package com.nixium.messageofdeath.Spree;

import java.util.ArrayList;
import java.util.logging.Logger;

import me.messageofdeath.Commands.NewCommandSystem.CommandManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.nixium.messageofdeath.Spree.Commands.KitCommand;
import com.nixium.messageofdeath.Spree.Commands.NpcCommand;
import com.nixium.messageofdeath.Spree.Commands.PointCommand;
import com.nixium.messageofdeath.Spree.Commands.SettingCommand;
import com.nixium.messageofdeath.Spree.Commands.TimerCommand;
import com.nixium.messageofdeath.Spree.Commands.VoteCommand;
import com.nixium.messageofdeath.Spree.Commands.WorldCommand;
import com.nixium.messageofdeath.Spree.Database.DatabaseManager;
import com.nixium.messageofdeath.Spree.Database.Configuration.Configuration;
import com.nixium.messageofdeath.Spree.Database.Databases.DonatorDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.ItemDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.KitDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.NPCDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.PointDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.SettingsDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.StaffDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.StatDatabase;
import com.nixium.messageofdeath.Spree.Database.Databases.WorldDatabase;
import com.nixium.messageofdeath.Spree.Database.Types.MySQL;
import com.nixium.messageofdeath.Spree.Game.Game;
import com.nixium.messageofdeath.Spree.Listener.BlockListener;
import com.nixium.messageofdeath.Spree.Listener.EntityListener;
import com.nixium.messageofdeath.Spree.Listener.InventoryListener;
import com.nixium.messageofdeath.Spree.Listener.NPCListener;
import com.nixium.messageofdeath.Spree.Listener.PlayerListener;
import com.nixium.messageofdeath.Spree.Timer.PluginTimer;
import com.nixium.messageofdeath.Spree.Utils.Parser;
import com.nixium.messageofdeath.Spree.Utils.DonatorManager.DonatorManager;
import com.nixium.messageofdeath.Spree.Utils.InventoryManager.InventoryManager;
import com.nixium.messageofdeath.Spree.Utils.KitManager.KitManager;
import com.nixium.messageofdeath.Spree.Utils.NPCManager.NPCManager;
import com.nixium.messageofdeath.Spree.Utils.PointManager.PointManager;
import com.nixium.messageofdeath.Spree.Utils.SettingsManager.SettingsManager;
import com.nixium.messageofdeath.Spree.Utils.StaffManager.StaffManager;
import com.nixium.messageofdeath.Spree.Utils.StatsManager.StatsManager;
import com.nixium.messageofdeath.Spree.Utils.StreakManager.StreakManager;
import com.nixium.messageofdeath.Spree.Utils.UserManager.User;
import com.nixium.messageofdeath.Spree.Utils.VoteManager.VoteManager;
import com.nixium.messageofdeath.Spree.Utils.WorldManager.World;
import com.nixium.messageofdeath.Spree.Utils.WorldManager.WorldManager;

public class Spree extends JavaPlugin {
	
	//Managers
	private DatabaseManager databaseManager;
	private StatsManager statsManager;
	private WorldManager worldManager;
	private DonatorManager donatorManager;
	private StreakManager streakManager;
	private VoteManager voteManager;
	private NPCManager npcManager;
	private KitManager kitManager;
	private InventoryManager inventoryManager;
	private PointManager pointManager;
	private StaffManager staffManager;
	private SettingsManager settingManager;
	//Database
	private Configuration configuration;
	private ItemDatabase itemDatabase;
	private StatDatabase statDatabase;
	private WorldDatabase worldDatabase;
	private DonatorDatabase donatorDatabase;
	private KitDatabase kitDatabase;
	private NPCDatabase npcDatabase;
	private PointDatabase pointDatabase;
	private StaffDatabase staffDatabase;
	private SettingsDatabase settingDatabase;
	//Types
	private MySQL mysql;
	//Parser
	private Parser parser;
	//Game
	private Game game;
	//Timer
	private PluginTimer pluginTimer;
	
	@Override
	public void onEnable() {
		//Managers
		this.log("Initiating Managers...");
		User.instance = this;
		this.databaseManager = new DatabaseManager(this);
		this.statsManager = new StatsManager(this);
		this.worldManager = new WorldManager(this);
		this.donatorManager = new DonatorManager(this);
		this.streakManager = new StreakManager(this);
		this.voteManager = new VoteManager(this);
		this.npcManager = new NPCManager(this);
		this.kitManager = new KitManager(this);
		this.inventoryManager = new InventoryManager(this);
		this.pointManager = new PointManager(this);
		this.staffManager = new StaffManager(this);
		this.settingManager = new SettingsManager(this);
		//Databases
		this.log("Initiating Databases...");
		this.configuration = new Configuration(this);
		this.itemDatabase = new ItemDatabase(this);
		this.statDatabase = new StatDatabase(this);
		this.worldDatabase = new WorldDatabase(this);
		this.donatorDatabase = new DonatorDatabase(this);
		this.kitDatabase = new KitDatabase(this);
		this.npcDatabase = new NPCDatabase(this);
		this.pointDatabase = new PointDatabase(this);
		this.staffDatabase = new StaffDatabase(this);
		this.settingDatabase = new SettingsDatabase(this);
		//Parser
		this.log("Initiating Parsers...");
		this.parser = new Parser(this);
		//Timer
		this.pluginTimer = new PluginTimer(this);
		
		// Configuration
		this.log("Initiating Configuration...");
		this.configuration.initConfiguration();
		this.configuration.loadConfiguration();
		// Types
		this.log("Initiating MySQL...");
		this.mysql = new MySQL(this);
		// MySQL or Yaml loading
		this.log("Initiating Database Type...");
		this.databaseManager.loadDatabaseType();
		// Statistics Database (Does not load entire database to prevent unnessary use of RAM)
		this.log("Initiating Stats Database...");
		this.statDatabase.initDatabase();
		// Item Database
		this.log("Initiating and Loading Item Database...");
		this.itemDatabase.initDatabase();
		this.itemDatabase.loadDatabase();
		// World Database
		this.log("Initiating and Loading World Database...");
		this.worldDatabase.initDatabase();
		this.worldDatabase.loadDatabase();
		// Donator Database
		this.log("Initiating Donator Database...");
		this.donatorDatabase.initDatabase();
		// Staff Database
		this.log("Initiating Staff Database...");
		this.staffDatabase.initDatabase();
		// Kit Database
		this.log("Initiating Kit Database...");
		this.kitDatabase.initDatabase();
		// NPC Database
		this.log("Initiating and Loading NPC Database...");
		this.npcDatabase.initDatabase();
		this.npcDatabase.loadDatabase();
		// Point Database
		this.log("Initiating and Loading PointDatabase...");
		this.pointDatabase.initDatabase();
		this.pointDatabase.loadDatabase();
		// Staff Database
		this.staffDatabase.initDatabase();
		// Settings Database
		this.settingDatabase.initDatabase();
		//Game
		this.log("Initiating Game...");
		this.game = new Game(this);
		for(Player player : Bukkit.getOnlinePlayers()) {
			this.getGame().addPlayer(player.getName());
			User user = new User(player);
			user.reset();
		}
		//Commands
		this.log("Registering Commands with Server...");
		CommandManager.register(this, new VoteCommand(this));
		CommandManager.register(this, new TimerCommand(this));
		CommandManager.register(this, new WorldCommand(this));
		CommandManager.register(this, new KitCommand(this));
		CommandManager.register(this, new NpcCommand(this));
		CommandManager.register(this, new PointCommand(this));
		CommandManager.register(this, new SettingCommand(this));
		//Listeners
		this.log("Registering Listeners with Server...");
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
		this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new NPCListener(this), this);
		this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
		//Timer
		this.log("Initiating Game Timer...");
		Bukkit.getScheduler().runTaskTimer(this, this.pluginTimer, 20L, 20L);
		//VoteManager
		this.getVoteManager().resetVotes();
		//KitManager (Make sure the kits were deleted in case of crash of server)
		this.getKitManager().deleteKitsOnShutDown();
	}
	
	@Override
	public void onDisable() {
		this.getKitManager().deleteKitsOnShutDown();
		this.getGame().sendToLobby();
		for(World world : this.getWorldManager().getWorlds()) {
			if(this.getWorldManager().isLoaded(world)) {
				this.getWorldManager().unloadWorld(world);
			}
		}
		this.getGame().sendMessage(true, "The game has ended due to a reload.");
		this.getWorldDatabase().saveWorlds();
		this.getStatDatabase().saveDatabase();
		this.getNPCDatabase().saveDatabase();
	}
	
	public void log(String log) {
		super.getServer().getLogger().info(log);
	}
	
	public void logError(String topic, String classx, String method, String error) {
		Logger log = super.getServer().getLogger();
		final String space = "                                                             ";
		String text = "Topic";
		log.severe("________________________{Spree Error}_______________________");
		log.severe(space.substring((space.length() + text.length()) / 2, space.length()) + text);
		log.severe(space.substring((space.length() + topic.length()) / 2, space.length()) + topic);
		log.severe("");
		for(String s : this.getLines(error, space)) {
			log.severe(space.substring((space.length() + s.length()) / 2, space.length()) + s);
		}
		log.severe("");
		String cl = "Class: " + classx + "   Method: " + method;
		for(String s : this.getLines(cl, space)) {
			log.severe(space.substring((space.length() + s.length()) / 2, space.length()) + s);
		}
		log.severe("");
		log.severe("------------------------{Spree Error}-----------------------");
	}
	
	private ArrayList<String> getLines(String parse, final String space) {
		ArrayList<String> lines = new ArrayList<String>();
		String s = "";
		String[] split = parse.split(" ");
		final int length = split.length;
		for(int i = 0; i < length; i++) {
			if(s.length() + split[i].length() < space.length()) {
				s += split[i] + " ";
			}else{
				lines.add(s);
				s = split[i] + " ";
			}
			if(i + 1 == length) {
				lines.add(s);
			}
		}
		return lines;
	}
		
	//************************************ Managers *******************************************
	
	public DatabaseManager getDatabaseManager() {
		return this.databaseManager;
	}
	
	public StatsManager getStatManager() {
		return this.statsManager;
	}
	
	public WorldManager getWorldManager() {
		return this.worldManager;
	}
	
	public DonatorManager getDonatorManager() {
		return this.donatorManager;
	}
	
	public StreakManager getStreakManager() {
		return this.streakManager;
	}
	
	public VoteManager getVoteManager() {
		return this.voteManager;
	}
	
	public NPCManager getNPCManager() {
		return this.npcManager;
	}
	
	public KitManager getKitManager() {
		return this.kitManager;
	}
	
	public InventoryManager getInventoryManager() {
		return this.inventoryManager;
	}
	
	public PointManager getPointManager() {
		return this.pointManager;
	}
	
	public StaffManager getStaffManager() {
		return this.staffManager;
	}
	
	public SettingsManager getSettingsManager() {
		return this.settingManager;
	}
	
	//************************************ Databases *******************************************
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public ItemDatabase getItemDatabase() {
		return this.itemDatabase;
	}
	
	public StatDatabase getStatDatabase() {
		return this.statDatabase;
	}
	
	public WorldDatabase getWorldDatabase() {
		return this.worldDatabase;
	}
	
	public DonatorDatabase getDonatorDatabase() {
		return this.donatorDatabase;
	}
	
	public KitDatabase getKitDatabase() {
		return this.kitDatabase;
	}
	
	public NPCDatabase getNPCDatabase() {
		return this.npcDatabase;
	}
	
	public PointDatabase getPointDatabase() {
		return this.pointDatabase;
	}
	
	public StaffDatabase getStaffDatabase() {
		return this.staffDatabase;
	}
	
	public SettingsDatabase getSettingsDatabase() {
		return this.settingDatabase;
	}
	
	//************************************ Types *******************************************
	
	public MySQL getMySQL() {
		return this.mysql;
	}
	
	//************************************ Parser *******************************************
	
	public Parser getParser() {
		return this.parser;
	}
	
	//************************************ Game *******************************************
	
	public Game getGame() {
		return this.game;
	}
	
	//************************************ Timer *******************************************
	
	public PluginTimer getPluginTimer() {
		return this.pluginTimer;
	}
}
