package org.azaleamc.nightfall.listeners;

import cc.funkemunky.api.utils.Init;
import org.azaleamc.nightfall.Nightfall;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;

/**
 * Created by George on 27/04/2019 at 18:57.
 */
@Init
public class BukkitListeners implements Listener {

    @EventHandler
    public void onEvent(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerData data = Nightfall.getInstance().getDataManager().getPlayerData(event.getEntity().getUniqueId());

            if (data != null) {
                callChecks(data, event);
            }
        }
    }

    private void callChecks(PlayerData data, Event event) {
        data.getBukkitChecks().getOrDefault(event.getClass(), new ArrayList<>()).stream().filter(Check::isEnabled).forEach(check -> check.onBukkitEvent(event));
    }
}
