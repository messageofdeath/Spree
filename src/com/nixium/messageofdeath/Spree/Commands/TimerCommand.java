package com.nixium.messageofdeath.Spree.Commands;

import java.util.ArrayList;


import org.bukkit.ChatColor;

import com.nixium.messageofdeath.Spree.Spree;
import com.nixium.messageofdeath.Spree.Game.Enumerations.GameStatus;

import me.messageofdeath.Commands.NewCommandSystem.Command;
import me.messageofdeath.Commands.NewCommandSystem.IssuedCommand;
import me.messageofdeath.Commands.NewCommandSystem.MessageCommand;
import me.messageofdeath.Logging.Messenger;

public class TimerCommand extends MessageCommand {
	
	private Spree instance;
	
	public TimerCommand(Spree instance) {
		this.instance = instance;
	}

	@Override
	@Command(name = "timer", permission = "Spree.Admin.Timer")
	public void issue(IssuedCommand cmd) {
		if(this.messenger == null) {
			this.messenger = new Messenger(ChatColor.translateAlternateColorCodes('&', "&8[&5&lSpree&8]&6 "));
		}
		
		if(cmd.getLength() == 0)
			this.helpMsg(cmd);
		else if(cmd.getLength() == 1)
			if(cmd.getArg(0).equalsIgnoreCase("force"))
				super.wrongArgs(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("stop"))
				this.stopTimer(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("start"))
				this.startTimer(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("resume"))
				this.resumeTimer(cmd);
			else if(cmd.getArg(0).equalsIgnoreCase("pause"))
				this.pauseTimer(cmd);
			else
				this.helpMsg(cmd);
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("force"))
				if(cmd.getArg(1).equalsIgnoreCase("start"))
					this.forceStart(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("end"))
					this.forceEnd(cmd);
				else if(cmd.getArg(0).equalsIgnoreCase("stop"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(0).equalsIgnoreCase("start"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(0).equalsIgnoreCase("resume"))
					super.wrongArgs(cmd);
				else if(cmd.getArg(0).equalsIgnoreCase("pause"))
					super.wrongArgs(cmd);
				else
					this.helpMsg(cmd);
			else
				this.helpMsg(cmd);
		else
			this.helpMsg(cmd);
	}
	
	private void startTimer(IssuedCommand cmd) {
		this.instance.getGame().setGameUp();
		this.instance.getPluginTimer().setRun(true);
		this.instance.getGame().sendMessage(true, "The game timer has been started.");
	}
	
	private void stopTimer(IssuedCommand cmd) {
		GameStatus status = this.instance.getGame().getGameStatus();
		if(status == GameStatus.InGame) {
			this.instance.getGame().pendingEnd();
		}
		if(status == GameStatus.PendingEnd) {
			this.instance.getGame().endGame();
		}
		this.instance.getPluginTimer().setRun(false);
		this.instance.getGame().sendMessage(true, "The game timer has been stopped.");
	}

	private void resumeTimer(IssuedCommand cmd) {
		this.instance.getPluginTimer().setRun(true);
		this.instance.getGame().sendMessage(true, "The game timer has been resumed.");
	}
	
	private void pauseTimer(IssuedCommand cmd) {
		this.instance.getPluginTimer().setRun(false);
		this.instance.getGame().sendMessage(true, "The game timer has been paused.");
	}
	
	private void forceStart(IssuedCommand cmd) {
		if(this.instance.getGame().getGameStatus() == GameStatus.InLobby) {
			this.instance.getGame().setSeconds(10);
			this.instance.getGame().sendMessage(true, "The game has been forced to start!");
		}else{
			super.error(cmd, "The game is not in the Lobby!");
		}
	}
	
	private void forceEnd(IssuedCommand cmd) {
		if(this.instance.getGame().getGameStatus() == GameStatus.InGame) {
			this.instance.getGame().forceEnd();
		}else{
			super.error(cmd, "The game is not in the Game!");
		}
	}
	
	private void helpMsg(IssuedCommand cmd) {
		super.msgPrefix(cmd, "Available Commands:");
		String dud = ChatColor.DARK_GRAY + "    - ";
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("/timer start - Starts the timer when stopped.");
		msg.add("/timer stop - Stops the timer when in progress.");
		msg.add("/timer pause - Pauses the timer (Stays in Game).");
		msg.add("/timer resume - Resumes the timer (Stays in Game).");
		msg.add("/timer force start - Starts the game.");
		msg.add("/timer force end - Ends the game.");
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
