package com.froobworld.frooblib.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ContainerCommandExecutor extends CommandExecutor {
	
	public ArrayList<SubCommand> subCommands;
	
	public ContainerCommandExecutor() {
		subCommands = new ArrayList<SubCommand>();
	}
	
	@Override
	public boolean onCommandProcess(CommandSender sender, Command command, String cl, String[] args) {
		for(SubCommand subCommand : subCommands) {
			if(subCommand.getArgIndex() < args.length) {
				for(String arg : subCommand.getArgs()) {
					if(arg.equalsIgnoreCase(args[subCommand.argIndex])) {
						return subCommand.getExecutor().onCommand(sender, command, cl, args);
					}
				}
				
			}
		}
		
		for(SubCommand subCommand : subCommands) {
			if(sender.hasPermission(subCommand.getExecutor().perm())) {
				sender.sendMessage(subCommand.getUsage());
			}
		}
		return false;
	}
	
	public void addSubCommand(CommandExecutor executor, int argIndex, String arg, String usage) {
		subCommands.add(new SubCommand(executor, argIndex, arg, usage));
	}
	
	public void addSubCommand(CommandExecutor executor, int argIndex, String[] args, String usage) {
		subCommands.add(new SubCommand(executor, argIndex, args, usage));
	}
	
	public ArrayList<SubCommand> getSubCommands(){
		
		return subCommands;
	}
	
	public class SubCommand{
		private CommandExecutor executor;
		private int argIndex;
		private String[] args;
		private String usage;
		
		public SubCommand(CommandExecutor executor, int argIndex, String arg, String usage) {
			this.executor = executor;
			this.argIndex = argIndex;
			this.args = new String[] {arg};
			this.usage = usage;
		}
		
		public SubCommand(CommandExecutor executor, int argIndex, String[] args, String usage) {
			this.executor = executor;
			this.argIndex = argIndex;
			this.args = args;
			this.usage = usage;
		}
		
		
		public CommandExecutor getExecutor() {
			
			return executor;
		}
		
		public int getArgIndex() {
			
			return argIndex;
		}
		
		public String getArg() {
			
			return args[0];
		}
		
		public String[] getArgs() {
			
			return args;
		}
		
		public String getUsage() {
			
			return usage;
		}
	}
}
