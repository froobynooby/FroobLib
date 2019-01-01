package com.froobworld.frooblib.utils;

import com.froobworld.frooblib.uuid.UUIDManager;
import com.froobworld.frooblib.uuid.UUIDManager.UUIDData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class UUIDUtils {

    public static ArrayList<String> convertToNames(ArrayList<UUID> uuids, UUIDManager uuidManager, boolean useDisplaynames) {
        ArrayList<String> names = new ArrayList<String>();
        for (UUID uuid : uuids) {
            UUIDData data = uuidManager.getUUIDData(uuid);
            if (data != null) {
                if (useDisplaynames) {
                    Player player = Bukkit.getPlayer(data.getUUID());
                    if (player != null) {
                        names.add(player.getDisplayName());
                        continue;
                    }
                }
                names.add(data.getLastName());
            }
        }

        return names;
    }

}
