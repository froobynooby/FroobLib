package com.froobworld.frooblib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class LocationUtils {

    public static String serialiseLocation(Location location) {
        if (location == null) {
            return null;
        }

        return location.getWorld().getUID() + ";"
                + location.getX() + ";"
                + location.getY() + ";"
                + location.getZ() + ";"
                + location.getYaw() + ";"
                + location.getPitch();
    }

    public static Location deserialiseLocation(String string) {
        if (string == null) {
            return null;
        }
        String[] split = string.split(";");
        if (split.length != 6) {
            return null;
        }
        World world = Bukkit.getWorld(UUID.fromString(split[0]));
        if (world == null) {
            return null;
        }

        return new Location(world, Double.valueOf(split[1]), Double.valueOf(split[2]),
                Double.valueOf(split[3]), Float.valueOf(split[4]), Float.valueOf(split[5]));
    }

}
