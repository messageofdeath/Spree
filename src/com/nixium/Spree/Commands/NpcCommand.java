package com.nixium.Spree.Commands;

import java.util.ArrayList;


import org.bukkit.ChatColor;

import com.nixium.Spree.Spree;
import com.nixium.Spree.Utils.NPCManager.NPCType;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class NpcCommand extends MessageCommand {
	
	private Spree instance;
	
	public NpcCommand(Spree instance) {
		this.instance = instance;
	}

	@Override
	@Command(name = "npcs", permission = "Spree.Admin.Npc")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		
		if(cmd.getLength() == 0)
			this.help(cmd);
		else if(cmd.getLength() == 1)
			if(cmd.getArg(0).equalsIgnoreCase("spawn"))
				super.wrongArgs(cmd);
			else
				this.help(cmd);
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("spawn"))
				if(cmd.getArg(1).equalsIgnoreCase("vendor"))
					this.spawnVendor(cmd);
				else
					this.help(cmd);
			else
				this.help(cmd);
		else
			this.help(cmd);
	}
	
	private void spawnVendor(IssuedCommand cmd) {
		if(cmd.isPlayer()) {
			this.instance.getNPCManager().createNPC(NPCType.Vendor, cmd.getPlayer().getLocation());
			super.msgPrefix(cmd, "You have successfully spawned a vendor!");
		}else{
			super.error(cmd, "You must be in-game to use this command!");
		}
	}
	
	private void help(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Commands:");
		String dud = ChatColor.DARK_GRAY + "    - ";
		ArrayList<String> msg = new ArrayList<String>();
		
		msg.add("/npcs spawn vendor - Spawns a npc as a vendor.");
		
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
