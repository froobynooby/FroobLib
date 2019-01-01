package com.froobworld.frooblib.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.froobworld.frooblib.Messages;

public abstract class PlayerCommandExecutor extends CommandExecutor {
	
	
	public abstract boolean onPlayerCommandProcess(Player player, Command command, String cl, String[] args);
	
	@Override
	public boolean onCommandProcess(CommandSender sender, Command command, String cl, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.NO_CONSOLE);
			return false;
		}
		
		return onPlayerCommandProcess((Player) sender, command, cl, args);
	}
	
	@Override
	public List<String> tabCompletions(CommandSender sender, Command command, String cl, String[] args) {
		if(!(sender instanceof Player)) {
			return new ArrayList<String>();
		}
		
		return tabCompletions((Player) sender, command, cl, args);
	}
	
	public abstract List<String> tabCompletions(Player player, Command command, String cl, String[] args);

}
