package org.azaleamc.nightfall.checks.impl.player.regen;

import cc.funkemunky.api.tinyprotocol.api.ProtocolVersion;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Verbose;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * Check to check whether player has Regen hack (unnaturally fast amounts of Regen)
 * @author George
 */
@BukkitEvents(events = {EntityRegainHealthEvent.class})
public class Regen extends Check {

    private final Verbose verbose = new Verbose();

    public Regen(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {

    }

    @Override
    public void onBukkitEvent(Event event) {
        EntityRegainHealthEvent e = (EntityRegainHealthEvent) event;
        if (e.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)) {
            if (verbose.flag(2, ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9) ? 750L : 450L)) {
                Player p = (Player) e.getEntity();
                if (p.hasPermission("nightfall.bypass.regen")) {
                    flag("" + verbose.flag(2, ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9) ? 750L : 450L));
                }
            }
        }
    }
}
