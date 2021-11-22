package org.azaleamc.nightfall.checks.impl.pvp.reach;

import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Check to detect whether player is using Reach (unusually high amount of reach)
 * @author Ruby
 */
@BukkitEvents(events = {EntityDamageByEntityEvent.class})
public class Reach extends Check {
    public Reach(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {

    }

    @Override
    public void onBukkitEvent(Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if (e.getDamager().getLocation().distance(e.getEntity().getLocation()) < 3.5) {
            if (!e.getDamager().hasPermission("nightfall.bypass.reach")) {
                flag("" + e.getDamager().getLocation().distance(e.getEntity().getLocation()));
            }
        }
    }
}
