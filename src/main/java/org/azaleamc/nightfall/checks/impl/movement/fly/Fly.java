package org.azaleamc.nightfall.checks.impl.movement.fly;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.processors.CollisionAssessment;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Packets;
import org.azaleamc.nightfall.utils.Verbose;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * Check to check whether player is using Fly hack
 * @author Ruby
 */
@BukkitEvents(events = {PlayerToggleFlightEvent.class})
@Packets(packets={Packet.Client.FLYING, Packet.Client.POSITION, Packet.Client.LEGACY_POSITION})
public class Fly extends Check {

    private final Verbose verbose = new Verbose();

    public Fly(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {

    }

    @Override
    public void onBukkitEvent(Event event) {
        PlayerToggleFlightEvent e = (PlayerToggleFlightEvent) event;
        if (!e.getPlayer().hasPermission("nightfall.bypass.fly")) {
            flag("");
        }
    }
}
