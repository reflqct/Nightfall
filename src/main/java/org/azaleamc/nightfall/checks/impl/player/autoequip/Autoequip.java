package org.azaleamc.nightfall.checks.impl.player.autoequip;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.azaleamc.nightfall.Nightfall;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Verbose;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Check to detect whether player is using AutoEquip (equipping armor at unnaturally fast speeds)
 * @author Ruby
 */
@BukkitEvents(events={PlayerArmorChangeEvent.class})
public class Autoequip extends Check implements Listener {
    private final Verbose verbose = new Verbose();

    public Autoequip(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {

    }

    @Override
    public void onBukkitEvent(Event event) {
        PlayerArmorChangeEvent e = (PlayerArmorChangeEvent) event;

    }
}
