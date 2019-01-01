package com.froobworld.frooblib;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.froobworld.frooblib.command.CommandExecutor;
import com.froobworld.frooblib.data.Manager;
import com.froobworld.frooblib.uuid.UUIDManager;

public abstract class FroobPlugin extends JavaPlugin {
	private UUIDManager uuidManager = null;
	private boolean loadedUUIDManager = false;
	private ArrayList<Manager> managers;
	private ArrayList<CommandExecutor> commandExecutors;
	
	private void initiateUUIDManager() {
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if(plugin instanceof FroobPlugin) {
				if(((FroobPlugin) plugin).loadedUUIDManager) {
					uuidManager = ((FroobPlugin) plugin).uuidManager();
				}
				if(uuidManager != null) {
					break;
				}
			}
		}
		
		if(uuidManager == null) {
			uuidManager = new UUIDManager(this);
			loadedUUIDManager = true;
		}
	}
	
	public UUIDManager uuidManager() {
		if(uuidManager == null) {
			initiateUUIDManager();
			registerManager(uuidManager);
		}
		
		return uuidManager;
	}
	
	public void registerManager(Manager manager) {
		if(managers == null) {
			managers = new ArrayList<Manager>();
		}
		
		managers.add(manager);
		manager.ini();
	}
	
	public void registerCommand(CommandExecutor executor) {
		if(commandExecutors == null) {
			commandExecutors = new ArrayList<CommandExecutor>();
		}
		
		commandExecutors.add(executor);
		getCommand(executor.command()).setExecutor(executor);
		getCommand(executor.command()).setPermission(executor.perm());
		getCommand(executor.command()).setPermissionMessage(Messages.NO_PERM);
		getCommand(executor.command()).setTabCompleter(executor.tabCompleter());
	}

}