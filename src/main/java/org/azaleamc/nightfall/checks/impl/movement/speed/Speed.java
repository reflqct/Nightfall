package org.azaleamc.nightfall.checks.impl.movement.speed;

import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Verbose;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Check to check whether player is using Speed (unnatural amounts of move speed)
 * @author Ruby
 */
@BukkitEvents(events = {PlayerMoveEvent.class})
public class Speed extends Check {

    private final Verbose verbose = new Verbose();

    public Speed(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {
    }

    @Override
    public void onBukkitEvent(Event event) {
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if (e.getPlayer().getWalkSpeed() > 5.612) {
            if (!e.getPlayer().hasPermission("nightfall.bypass.speed")) {
                flag("" + e.getPlayer().getWalkSpeed());
            }
        }
    }
}
