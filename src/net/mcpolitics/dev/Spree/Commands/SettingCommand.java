package net.mcpolitics.dev.Spree.Commands;

import java.util.ArrayList;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Utils.SettingsManager.SettingType;
import net.mcpolitics.dev.Spree.Utils.UserManager.User;

import org.bukkit.ChatColor;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class SettingCommand extends MessageCommand {
	
	private Spree instance;

	public SettingCommand(Spree instance) {
		this.instance = instance;
	}

	@Override
	@Command(name = "settings")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		
		if(cmd.getLength() == 0)
			this.help(cmd);
		else if(cmd.getLength() == 1)
			if(cmd.getArg(0).equalsIgnoreCase("set"))
				this.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("get"))
				this.wrongArgs(cmd);
			else
				this.help(cmd);
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("set"))
				this.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("get"))
				this.getSetting(cmd, cmd.getArg(1));
			else
				this.help(cmd);
		else if(cmd.getLength() == 3)
			if(cmd.getArg(0).equalsIgnoreCase("set"))
				this.setSetting(cmd, cmd.getArg(1), cmd.getArg(2));
			else if(cmd.getArg(0).equalsIgnoreCase("get"))
				this.wrongArgs(cmd);
			else
				this.help(cmd);
		else
			this.help(cmd);
	}
	
	private void getSetting(IssuedCommand cmd, String setting) {
		if(SettingType.valueOf(setting) != null) {
			User user = new User(cmd.getSender().getName());
			super.msgPrefix(cmd, "The value of " + setting + " is " + user.getPlayerSettings().getSetting(SettingType.valueOf(setting)).getValue());
		}else{
			super.error(cmd, "That setting does not exist.");
		}
	}
	
	private void setSetting(IssuedCommand cmd, String setting, String value) {
		if(SettingType.valueOf(setting) != null) {
			if(Boolean.valueOf(value) != null) {
				User user = new User(cmd.getSender().getName());
				boolean set = Boolean.valueOf(value);
				user.getPlayerSettings().getSetting(SettingType.valueOf(setting)).setValue(set);
				this.instance.getSettingsDatabase().saveDatabase();
				super.msgPrefix(cmd, "Setting '"+setting+"' has been set to "+value+".");
			}else{
				super.error(cmd, "That is not a applical setting must be true/false.");
			}
		}else{
			super.error(cmd, "That setting does not exist.");
		}
	}
	
	private void help(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Commands:");
		String dud = ChatColor.DARK_GRAY + "    - ";
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("/settings set <Setting> <Value> - Set a value");
		msg.add("/settings get <Setting> - Get a value");
		msg.add("Available Settings:");
		for(SettingType type : SettingType.values()) {
			msg.add(type.getName());
		}
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
