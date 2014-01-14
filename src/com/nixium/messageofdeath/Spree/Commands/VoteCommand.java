package com.nixium.messageofdeath.Spree.Commands;

import java.util.ArrayList;


import org.bukkit.ChatColor;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Game.Enumerations.GameStatus;
import com.nixium.messageofdeath.Spree.Utils.UserManager.User;
import com.nixium.messageofdeath.Spree.Utils.VoteManager.Vote;
import com.nixium.messageofdeath.Spree.Utils.WorldManager.World;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class VoteCommand extends MessageCommand {
	
	private Spree instance;
	private ArrayList<String> cantVote;
	
	public VoteCommand(Spree instance) {
		this.instance = instance;
		this.cantVote = new ArrayList<String>();
	}

	@Override
	@Command(name = "vote")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		if(!this.instance.getDonatorManager().hasPlayer(cmd.getSender().getName())) {
			super.msgPrefix(cmd, "You must be a donator to use this command!");
			return;
		}
		if(this.instance.getGame().getGameStatus() == GameStatus.InLobby) {
			if(cmd.getLength() == 0)
				this.sendOptions(cmd);
			else if(cmd.getLength() == 1)
				if(cmd.isNumeric(0))
					this.vote(cmd, Integer.parseInt(cmd.getArg(0)));
				else
					super.error(cmd, "You can only use integers for this command.");
			else
				this.sendOptions(cmd);
		}else{
			super.error(cmd, "This command cannot be used during the game!");
		}
	}
	
	public void vote(IssuedCommand cmd, int vote) {
		final User user = new User(cmd.getSender().getName());
		vote -= 1;
		ArrayList<World> worlds = this.instance.getWorldManager().getWorlds();
		if(!(vote >= worlds.size()) && !(vote < 0)) {
			if(!this.cantVote.contains(user.getName())) {
				Vote vot = user.addVote(new Vote(cmd.getSender().getName(), worlds.get(vote)));
				if(vot != null) {
					this.cantVote.add(user.getName());
					if(!vot.isChanged()) {
						super.msgPrefix(cmd, "You successfully voted for " + ChatColor.RED + worlds.get(vote).getWorldName());
						this.instance.getGame().sendMessage(true, user.getName() + " voted for " + ChatColor.RED + worlds.get(vote).getWorldName());
					}else{
						super.msgPrefix(cmd, "You have changed your vote for " + ChatColor.RED + worlds.get(vote).getWorldName());
						this.instance.getGame().sendMessage(true, user.getName() + " has changed his vote for " 
								+ ChatColor.RED + worlds.get(vote).getWorldName());
						this.instance.getGame().sendVoteStats();
					}
					this.instance.getGame().sendVoteStats();
					this.instance.getServer().getScheduler().runTaskLater(this.instance, new Runnable() {
						@Override
						public void run() {
							cantVote.remove(user.getName());
						}
					}, 30 * 20L);
				}else{
					super.error(cmd, "You can only vote once!");
				}
			}else{
				super.error(cmd, "You cannot vote at the moment. Try again in a few seconds.");
			}
		}else{
			super.error(cmd, "You can only use the numbers 1-" + worlds.size());
		}
	}
	
	public void sendOptions(IssuedCommand cmd) {
		int i = 1;
		super.msgPrefix(cmd, "Available Arenas: You can use /vote <#>");
		for(World world : this.instance.getWorldManager().getWorlds()) {
			super.msg(cmd, ChatColor.DARK_PURPLE + "    " + i + ChatColor.DARK_RED + ") " + ChatColor.GOLD + world.getWorldName());
			i++;
		}
	}
}
