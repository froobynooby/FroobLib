package com.froobworld.frooblib.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.froobworld.frooblib.uuid.UUIDManager;
import com.froobworld.frooblib.uuid.UUIDManager.UUIDData;

public class CommandUtils {
	
	public static ArrayList<String> tabCompletePlayerList(String partial, boolean includeOffline, boolean prioritiseOnline, UUIDManager uuidManager) {
		ArrayList<String> completions = new ArrayList<String>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			completions.add(player.getName());
		}
		completions = StringUtil.copyPartialMatches(partial, completions, new ArrayList<String>(completions.size()));
		if(includeOffline && ((prioritiseOnline && completions.isEmpty()) || !prioritiseOnline)) {
			for(UUIDData data : uuidManager.getUUIDData()) {
				completions.add(data.getLastName());
			}
			completions = StringUtil.copyPartialMatches(partial, completions, new ArrayList<String>(completions.size()));
			if(completions.isEmpty()) {
				for(UUIDData data : uuidManager.getUUIDData()) {
					completions.add(data.getUUID().toString());
				}
				completions = StringUtil.copyPartialMatches(partial, completions, new ArrayList<String>(completions.size()));
			}
		}
		
		return completions;
	}

}
