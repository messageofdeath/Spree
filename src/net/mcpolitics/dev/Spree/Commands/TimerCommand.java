package net.mcpolitics.dev.Spree.Commands;

import net.mcpolitics.dev.Spree.Spree;
import net.mcpolitics.dev.Spree.Game.Enumerations.GameStatus;

import org.bukkit.ChatColor;

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
			else
				this.helpMsg(cmd);
		else if(cmd.getLength() == 2)
			if(cmd.getArg(0).equalsIgnoreCase("force"))
				if(cmd.getArg(1).equalsIgnoreCase("start"))
					this.forceStart(cmd);
				else if(cmd.getArg(1).equalsIgnoreCase("end"))
					this.forceEnd(cmd);
				else
					this.helpMsg(cmd);
			else
				this.helpMsg(cmd);
		else
			this.helpMsg(cmd);
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
		super.msgPrefix(cmd, "You can use (force start | force end)");
	}
}
