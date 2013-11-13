package net.mcpolitics.dev.Spree.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.WorldManager.World;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.messageofdeath.Blocks.Vector;
import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class WorldCommand extends MessageCommand {
	
	private Spree instance;
	private HashMap<String, Vector> vector;
	
	public WorldCommand(Spree instance) {
		this.instance = instance;
	}

	@Override
	@Command(name = "world", permission = "Spree.Admin.World")
	public void issue(IssuedCommand cmd) {
		if(this.vector == null) {
			this.vector = new HashMap<String, Vector>();
		}
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		// /world
		if(cmd.getLength() == 0)
			this.help(cmd);
		// /world add
		else if(cmd.getLength() == 1)
			if(cmd.getArg(0).equalsIgnoreCase("add"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("remove"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("list"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("set"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("tele"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("load"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("unload"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("save"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("butcher"))
				super.wrongArgs(cmd);
			else
				this.help(cmd);
		// /world add world
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("add"))
				if(cmd.getArg(1).equalsIgnoreCase("world"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("remove"))
				if(cmd.getArg(1).equalsIgnoreCase("world"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("list"))
				if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("world"))
					this.listWorld(cmd);
				else
					super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("set"))
				if(cmd.getArg(1).equalsIgnoreCase("pos1"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("pos2"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("tele"))
				if(!cmd.isNumeric(1))
					this.teleWorld(cmd, cmd.getArg(1));
				else
					super.error(cmd, "You must enter a world name!");
			else if(cmd.getArg(0).equalsIgnoreCase("load"))
				this.loadWorld(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("unload"))
				this.unloadWorld(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("save"))
				this.saveWorld(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("butcher"))
				this.butcherWorld(cmd, cmd.getArg(1));
			else
				this.help(cmd);
		// /world add world <worldName>
		else if(cmd.getLength() == 3)
			if(cmd.getArg(0).equalsIgnoreCase("add"))
				if(cmd.getArg(1).equalsIgnoreCase("world"))
					this.addWorld(cmd, cmd.getArg(2));
				else if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					this.addSpawn(cmd, cmd.getArg(2));
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("remove"))
				if(cmd.getArg(1).equalsIgnoreCase("world"))
					this.removeWorld(cmd, cmd.getArg(2));
				else if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("list"))
				if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					this.listSpawn(cmd, cmd.getArg(2));
				else if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("set"))
				if(cmd.getArg(1).equalsIgnoreCase("pos1"))
					this.setPosition1(cmd, cmd.getArg(2));
				else if(cmd.getArg(1).equalsIgnoreCase("pos2"))
					this.setPosition2(cmd, cmd.getArg(2));
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("tele"))
				if(cmd.isNumeric(1))
					this.teleSpawn(cmd, Integer.parseInt(cmd.getArg(1)), cmd.getArg(2));
				else
					super.error(cmd, "You can only use integers!");
			else if(cmd.getArg(0).equalsIgnoreCase("load"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("unload"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("save"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("butcher"))
				super.wrongArgs(cmd);
			else
				this.help(cmd);
		else if(cmd.getLength() == 4)
			if(cmd.getArg(0).equalsIgnoreCase("add"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("remove"))
				if(cmd.getArg(1).equalsIgnoreCase("spawn"))
					if(cmd.isNumeric(2))
						this.removeSpawn(cmd, Integer.parseInt(cmd.getArg(2)), cmd.getArg(3));
					else
						super.error(cmd, "You can only use integers!");
				else if(cmd.getArg(1).equalsIgnoreCase("world"))
					super.wrongArgs(cmd);
				else
					this.help(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("list"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("set"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("tele"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("load"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("unload"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("save"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("butcher"))
				super.wrongArgs(cmd);
			else
				this.help(cmd);
		else
			this.help(cmd);
	}
	
	private void listWorld(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Worlds:");
		int i = 1;
		for(World world : this.instance.getWorldManager().getWorlds()) {
			super.msg(cmd, "    " + ChatColor.DARK_PURPLE + i + ChatColor.DARK_RED + ") " + ChatColor.GOLD + world.getWorldName());
			i++;
		}
	}
	
	private void addWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(!this.instance.getWorldManager().containsWorld(worldName)) {
			this.instance.getWorldManager().createWorld(new World(worldName, null, null));
			super.msgPrefix(cmd, "You have successfully created the world '"+worldName+"'!");
		}else{
			super.error(cmd, "That world already exists!");
		}
	}
	
	private void removeWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			this.instance.getWorldManager().deleteWorld(this.instance.getWorldManager().getWorld(worldName));
			super.msgPrefix(cmd, "You have successfully removed the world '"+worldName+"'!");
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void teleSpawn(IssuedCommand cmd, int spawn, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(cmd.isPlayer()) {
			if(this.instance.getWorldManager().containsWorld(worldName)) {
				World world = this.instance.getWorldManager().getWorld(worldName);
				spawn--;
				if(!(spawn >= world.getSpawns().size()) && !(spawn < 0)) {
					if(!this.instance.getWorldManager().isLoaded(world)) {
						this.instance.getWorldManager().loadWorld(world);
					}
					cmd.getPlayer().teleport(world.getSpawns().get(spawn).getLocation());
					super.msgPrefix(cmd, "Welcome to spawn #" + spawn++ + " in the world '"+worldName+"'!");
				}else{
					super.error(cmd, "Please use a number between 1-" + world.getSpawns().size());
				}
			}else{
				super.error(cmd, "That world does not exist!");
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void teleWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(cmd.isPlayer()) {
			if(this.instance.getWorldManager().containsWorld(worldName)) {
				World world = this.instance.getWorldManager().getWorld(worldName);
				if(!this.instance.getWorldManager().isLoaded(world)) {
					this.instance.getWorldManager().loadWorld(world);
				}
				cmd.getPlayer().teleport(Bukkit.getWorld(worldName).getSpawnLocation());
				super.msgPrefix(cmd, "Welcome to the world '"+worldName+"'!");
			}
		}
	}
	
	private void listSpawn(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			World world = this.instance.getWorldManager().getWorld(worldName);
			super.msgPrefix(cmd, "Available Spawns:");
			if(!world.getSpawns().isEmpty()) {
				int i = 1;
				for(Vector v : world.getSpawns()) {
					super.msg(cmd, "    " + ChatColor.DARK_PURPLE + i + ChatColor.DARK_RED + ") " 
							+ ChatColor.GOLD + "x = " + v.getBlockX() + " | y = " + v.getBlockY() + " | z = " + v.getBlockZ());
					i++;
				}
			}else{
				super.msg(cmd, ChatColor.RED + "    There are no available spawns.");
			}
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void addSpawn(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(cmd.isPlayer()) {
			if(this.instance.getWorldManager().containsWorld(worldName)) {
				if(cmd.getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) {
					Location loc = cmd.getPlayer().getLocation();
					this.instance.getWorldManager().getWorld(worldName).addSpawn(new Vector(worldName, loc.getX(), loc.getY(), loc.getZ()
							, loc.getYaw(), loc.getPitch()));
					super.msgPrefix(cmd, "You have successfully set a spawn!");
				}else{
					super.error(cmd, "You must be in the world of the spawn will be!");
				}
			}else{
				super.error(cmd, "That world does not exist!");
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void removeSpawn(IssuedCommand cmd, int spawn, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			World world = this.instance.getWorldManager().getWorld(worldName);
			spawn--;
			if(!(spawn >= world.getSpawns().size()) && !(spawn < 0)) {
				world.removeSpawn(spawn);
				super.msgPrefix(cmd, "You have successfully removed the spawn '" + spawn++ + "'!");
			}else{
				super.error(cmd, "Please use a number between 1-" + world.getSpawns().size());
			}
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void setPosition1(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(cmd.isPlayer()) {
			if(this.instance.getWorldManager().containsWorld(worldName)) {
				if(cmd.getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) {
					this.vector.remove(cmd.getPlayer().getName());
					Location loc = cmd.getPlayer().getLocation();
					this.vector.put(cmd.getPlayer().getName(), new Vector(worldName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
					super.msgPrefix(cmd, "Position 1 set!");
					super.msgPrefix(cmd, "Now please use the command /world set pos2 " + worldName + ".");
				}else{
					super.error(cmd, "You must be in the world of the spawn will be!");
				}
			}else{
				super.error(cmd, "That world does not exist!");
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void setPosition2(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(cmd.isPlayer()) {
			if(this.instance.getWorldManager().containsWorld(worldName)) {
				if(cmd.getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) {
					if(this.vector.containsKey(cmd.getPlayer().getName())) {
						Location loc = cmd.getPlayer().getLocation();
						this.instance.getWorldManager().getWorld(worldName).setBorder(this.vector.get(cmd.getSender().getName())
								, new Vector(worldName, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
						this.vector.remove(cmd.getPlayer().getName());
						super.msgPrefix(cmd, "Congraulations the border is now set!");
					}else{
						super.msgPrefix(cmd, "Please use the command /world set pos1 " + worldName + " first before this command.");
					}
				}else{
					super.error(cmd, "You must be in the world of the spawn will be!");
				}
			}else{
				super.error(cmd, "That world does not exist!");
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void loadWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			if(!this.instance.getWorldManager().isLoaded(this.instance.getWorldManager().getWorld(worldName))) {
				this.instance.getWorldManager().loadWorld(this.instance.getWorldManager().getWorld(worldName));
				super.msgPrefix(cmd, "The world has been loaded successfully.");
			}else{
				super.error(cmd, "That world is already loaded!");
			}
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void unloadWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			if(this.instance.getWorldManager().isLoaded(this.instance.getWorldManager().getWorld(worldName))) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.getLocation().getWorld().getName().equalsIgnoreCase(worldName)) {
						this.instance.getGame().sendToLobby(player);
						this.instance.getGame().sendMessage(true, player, "The world is being unloaded.");
					}
				}
				this.instance.getWorldManager().unloadWorld(this.instance.getWorldManager().getWorld(worldName));
				super.msgPrefix(cmd, "The world has been unloaded successfully.");
			}else{
				super.error(cmd, "That world is already unloaded!");
			}
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void saveWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			this.instance.getWorldDatabase().saveWorld(this.instance.getWorldManager().getWorld(worldName));
			super.msgPrefix(cmd, "The world '"+worldName+"' has been saved!");
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void butcherWorld(IssuedCommand cmd, String worldName) {
		worldName = worldName.replaceAll("_", " ");
		int i = 0;
		if(this.instance.getWorldManager().containsWorld(worldName)) {
			for(Entity entity : Bukkit.getWorld(worldName).getEntities()) {
				if(entity instanceof LivingEntity && !(entity instanceof Player)) {
					entity.remove();
					i++;
				}
			}
			super.msgPrefix(cmd, "Successfully removed '"+i+"' entities!");
		}else{
			super.error(cmd, "That world does not exist!");
		}
	}
	
	private void help(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Commands:");
		String dud = ChatColor.DARK_GRAY + "    - ";
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("/world list world - List all worlds");
		msg.add("/world add world <worldName> - Add a world");
		msg.add("/world remove world <worldName> - Remove a world");
		msg.add("/world list spawn <worldName> - List all available spawns.");
		msg.add("/world tele <id> <worldName> - Teleports you to a spawn in a world.");
		msg.add("/world tele <worldName> - Teleports you to the spawn location of a world");
		msg.add("/world add spawn <worldName> - Add a spawn at your location.");
		msg.add("/world remove spawn <id> <worldName> - Remove a spawn by ID");
		msg.add("/world set pos1 <worldName> - Set position 1 for world border.");
		msg.add("/world set pos2 <worldName> - Set position 2 for world border.");
		msg.add("/world load <worldName> - Loads a world.");
		msg.add("/world unload <worldName> - Unloads a world.");
		msg.add("/world save <worldName> - Saves a world in DB.");
		msg.add("/world butcher <worldName> - Kills all mobs in a World.");
		int i = 1;
		for(String m : msg) {
			super.msg(cmd, dud + (i == 1 ? ChatColor.LIGHT_PURPLE : ChatColor.AQUA) + m);
			if(i == 1) {
				i = 2;
			}else{
				i = 1;
			}
		}
	}
}
