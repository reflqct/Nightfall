package org.azaleamc.nightfall.data;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by George on 27/04/2019 at 18:34.
 */
@Getter
public class DataManager {
    private Map<UUID, PlayerData> dataObjects = new ConcurrentHashMap<>();

    public DataManager() {
        Bukkit.getOnlinePlayers().forEach(player -> addData(player.getUniqueId()));
    }

    public PlayerData getPlayerData(UUID uuid) {
        return dataObjects.getOrDefault(uuid, null);
    }

    public void addData(UUID uuid) {
        dataObjects.put(uuid, new PlayerData(uuid));
    }

    public void removeData(UUID uuid) {
        dataObjects.remove(uuid);
    }
}
