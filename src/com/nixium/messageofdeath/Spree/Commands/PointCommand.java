package com.nixium.messageofdeath.Spree.Commands;


import org.bukkit.ChatColor;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Utils.UserManager.User;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class PointCommand extends MessageCommand {
	
	//private Spree instance;
	
	public PointCommand(Spree instance) {
		//this.instance = instance;
	}

	@Override
	@Command(name = "points")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		this.sendPoints(cmd);
	}
	
	private void sendPoints(IssuedCommand cmd) {
		if(cmd.isPlayer()) {
			User user = new User(cmd.getSender().getName());
			super.msgPrefix(cmd, "You have " + (user.getMaxPoints() - user.getCalculatedPoints()) + "/" + user.getMaxPoints());
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
}
