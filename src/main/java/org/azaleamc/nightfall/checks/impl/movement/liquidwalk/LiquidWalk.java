package org.azaleamc.nightfall.checks.impl.movement.liquidwalk;

import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.BlockUtils;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.processors.MovementProcessor;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Packets;
import org.azaleamc.nightfall.utils.Verbose;
import org.bukkit.event.Event;

/**
 * Check to detect whether player is using LiquidWalk (walking on water without a speed difference)
 * @author George
 */
@Packets(packets = {Packet.Client.POSITION_LOOK,
        Packet.Client.POSITION,
        Packet.Client.LEGACY_POSITION_LOOK,
        Packet.Client.LEGACY_POSITION})
public class LiquidWalk extends Check {

    private final Verbose verbose = new Verbose();

    public LiquidWalk(String name) {
        super(name);
    }

    @Override
    public void onPacket(Object packet, String packetType, long timeStamp) {
        MovementProcessor move = getData().getMovementProcessor();

        if (BlockUtils.isLiquid(getData().getPlayer().getLocation().subtract(0, 0.1, 0).getBlock())
                && !BlockUtils.isLiquid(getData().getPlayer().getLocation().clone().add(0, 0.2, 0).getBlock()) && move.getWebTicks() == 0) {
            if (!move.isServerOnGround() && verbose.flag(10, 250L)) {
                if (!getData().getPlayer().hasPermission("nightfall.bypass.liquidwalk")) {
                    flag(verbose.getVerbose() + " > 10");
                }
            }
        }
    }

    @Override
    public void onBukkitEvent(Event event) {

    }
}
