package net.mcpolitics.dev.Spree.Commands;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.KitManager.Kit;
import net.mcpolitics.dev.Spree.Utils.UserManager.User;

import org.bukkit.ChatColor;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class KitCommand extends MessageCommand {
	
	private Spree instance;
	
	public KitCommand(Spree instance) {
		this.instance = instance;
	}

	@Command(name = "kits", permission = "Spree.Anybody.Kit")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		
		if(cmd.getLength() == 0)
			this.getAvailableKits(cmd);
		else if(cmd.getLength() == 1)
			if(cmd.getArg(0).equalsIgnoreCase("list"))
				this.getAvailableKits(cmd);
			else
				this.help(cmd);
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("load"))
				this.loadKit(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("delete"))
				this.deleteKit(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("save"))
				this.saveKit(cmd, cmd.getArg(1));
			else if(cmd.getArg(0).equalsIgnoreCase("list"))
				this.getAvailableKits(cmd);
			else
				this.help(cmd);
		else
			this.help(cmd);
	}
	
	private void getAvailableKits(IssuedCommand cmd) {
		User user = new User(cmd.getSender().getName());
		ArrayList<Kit> kits = user.getKits();
		super.msgPrefix(cmd, "Available Kits:");
		if(!kits.isEmpty()) {
			for(Kit kit : kits) {
				super.msg(cmd, ChatColor.DARK_GRAY + "    - " + ChatColor.GOLD + kit.getKitName());
			}
		}else{
			super.msg(cmd, ChatColor.DARK_RED + "    You have no available kits.");
		}
	}
	
	private void loadKit(IssuedCommand cmd, String kitName) {
		if(cmd.isPlayer()) {
			User user = new User(cmd.getSender().getName());
			if(user.hasKit(kitName)) {
				user.setInventory(user.getKit(kitName));
				super.msgPrefix(cmd, "You have successfully loaded the kit '"+kitName+"'");
			}else{
				super.error(cmd, "You do not have a kit by the name of " + kitName);
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void deleteKit(IssuedCommand cmd, String kitName) {
		if(cmd.isPlayer()) {
			User user = new User(cmd.getSender().getName());
			if(user.hasKit(kitName)) {
				user.deleteKit(kitName);
				super.msgPrefix(cmd, "You have successfully deleted the kit '"+kitName+"'");
			}else{
				super.error(cmd, "You do not have a kit by the name of " + kitName);
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void saveKit(IssuedCommand cmd, String kitName) {
		if(cmd.isPlayer()) {
			User user = new User(cmd.getSender().getName());
			if(!user.hasKit(kitName)) {
				user.addKit(new Kit(this.instance, cmd.getSender().getName(), kitName, false, false));
				super.msgPrefix(cmd, "You have successfully saved the kit '"+kitName+"'");
			}
			else{
				super.error(cmd, "You already have a kit by the name of " + kitName);
			}
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void help(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Commands:");
		String dud = ChatColor.DARK_GRAY + "    - ";
		ArrayList<String> msg = new ArrayList<String>();
		
		msg.add("/kits - Displays your available kits.");
		msg.add("/kits list - Displays your available kits.");
		msg.add("/kits load <Kit> - Loads a kit.");
		msg.add("/kits delete <Kit> - Deletes a kit.");
		msg.add("/kits save <Kit> - Saves a kit.");
		
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
