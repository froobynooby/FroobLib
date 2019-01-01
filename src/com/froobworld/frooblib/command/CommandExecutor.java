 package com.froobworld.frooblib.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.froobworld.frooblib.Messages;

public abstract class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public abstract boolean onCommandProcess(CommandSender sender, Command command, String cl, String[] args);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String cl, String[] args) {
		if(!sender.hasPermission(perm())) {
			sender.sendMessage(Messages.NO_PERM);
			return false;
		}
		
		return onCommandProcess(sender, command, cl, args);
	}
	
	public TabCompleter tabCompleter() {
		return new TabCompleter() {

			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String cl, String[] args) {
				
				return tabCompletions(sender, command, cl, args);
			}

		};
	}
	
	public abstract String command();
	public abstract String perm();
	public abstract List<String> tabCompletions(CommandSender sender, Command command, String cl, String[] args);
}
