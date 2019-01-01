package com.froobworld.frooblib.data;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class TaskManager extends Manager {

    private Plugin plugin;

    public TaskManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addTask(long iniDelay, long delta, Runnable runnable) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, runnable, iniDelay, delta);
    }

}
