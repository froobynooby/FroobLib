package com.froobworld.frooblib.uuid;

import com.froobworld.frooblib.FroobPlugin;
import com.froobworld.frooblib.data.Manager;
import com.froobworld.frooblib.data.Storage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class UUIDManager extends Manager {
    private FroobPlugin plugin;

    private Storage storage;

    private ArrayList<UUIDData> uuidData;

    public UUIDManager(FroobPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void ini() {
        storage = new Storage(plugin.getDataFolder().getParentFile().getPath() + "/FroobLib/UUIDs");

        uuidData = new ArrayList<UUIDData>();
        for (File file : storage.listFiles()) {
            UUIDData data = new UUIDData(file);
            uuidData.add(data);
        }

        Bukkit.getPluginManager().registerEvents(new PlayerjoinListener(), plugin);
    }

    public ArrayList<UUIDData> getUUIDData() {

        return uuidData;
    }

    public UUIDData getUUIDData(UUID uuid) {
        for (UUIDData data : uuidData) {
            if (data.uuid.equals(uuid)) {
                return data;
            }
        }

        return null;
    }

    public ArrayList<UUIDData> getUUIDData(String name) {
        ArrayList<UUIDData> dataList = new ArrayList<UUIDData>();
        for (UUIDData data : uuidData) {
            if (data.lastName.equalsIgnoreCase(name)) {
                dataList.add(data);
            }
        }

        return dataList;
    }

    public UUIDData getUUIDDataFull(UUID uuid) {
        ArrayList<String> history = new ArrayList<String>();
        JSONParser parser = new JSONParser();
        JSONArray array = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names").openConnection();
            array = (JSONArray) parser.parse(new InputStreamReader(connection.getInputStream()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        @SuppressWarnings("unchecked")
        Iterator<JSONObject> iterator = array.iterator();
        while (iterator.hasNext()) {
            JSONObject obj = iterator.next();
            history.add((String) obj.get("name"));
        }
        iterator.remove();

        return new UUIDData(uuid, history.get(history.size() - 1), history);
    }

    private class PlayerjoinListener implements Listener {

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerJoin(PlayerJoinEvent e) {
            UUIDData data = getUUIDData(e.getPlayer().getUniqueId());
            if (data == null) {
                data = new UUIDData(storage.getFile(e.getPlayer().getUniqueId().toString()), e.getPlayer());
                uuidData.add(data);
            }

            data.update(e.getPlayer());
        }
    }

    public class UUIDData {
        private File file;

        private UUID uuid;
        private String lastName;
        private ArrayList<String> previousNames;

        private UUIDData(File file) {
            this.file = file;
            load();
        }

        private UUIDData(File file, Player player) {
            this.file = file;
            this.uuid = player.getUniqueId();
            this.lastName = player.getName();
            this.previousNames = new ArrayList<String>();
            previousNames.add(player.getName());

            save();
        }

        private UUIDData(UUID uuid, String lastName, ArrayList<String> previousNames) {
            this.file = null;
            this.uuid = uuid;
            this.lastName = lastName;
            this.previousNames = previousNames;
        }


        private void load() {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            uuid = UUID.fromString(config.getString("uuid"));
            lastName = config.getString("last-name");
            previousNames = (ArrayList<String>) config.getStringList("previous-names");
        }

        private void update(Player player) {
            if (!player.getName().equals(lastName)) {
                lastName = player.getName();
                previousNames.add(player.getName());
                save();
            }
        }

        private void save() {
            YamlConfiguration config = new YamlConfiguration();

            config.set("uuid", uuid.toString());
            config.set("last-name", lastName);
            config.set("previous-names", previousNames);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public UUID getUUID() {

            return uuid;
        }

        public String getLastName() {

            return lastName;
        }

        public ArrayList<String> getPreviousNames() {

            return previousNames;
        }

        @Override
        public String toString() {

            return uuid.toString();
        }
    }

}
