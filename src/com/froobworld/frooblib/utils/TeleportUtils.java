package com.froobworld.frooblib.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class TeleportUtils {

    public static Location findSafeTeleportLocation(Location location) {
        World w = location.getWorld();
        Location finloc = location;

        if (w.getBlockAt(location).getType().isSolid()) {
            for (int i = 0; i < location.getWorld().getMaxHeight() - (int) location.getY(); i++) {
                if (!location.add(0, 1, 0).getBlock().getType().isSolid()) {
                    finloc = finloc.add(0, 1, 0);
                    break;
                }
            }
        } else {
            for (int i = 0; i < (int) location.getY(); i++) {
                if (location.add(0, -1, 0).getBlock().getType().isSolid()) {
                    finloc = finloc.add(0, -1 + 2, 0);
                    break;
                }
            }
        }

        return finloc;
    }

}
