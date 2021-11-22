package org.azaleamc.nightfall.listeners;

import cc.funkemunky.api.event.custom.PacketReceiveEvent;
import cc.funkemunky.api.event.system.EventMethod;
import cc.funkemunky.api.event.system.Listener;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.funkemunky.api.utils.Init;
import org.azaleamc.nightfall.Nightfall;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;

/**
 * Created by George on 27/04/2019 at 18:37.
 */
@Init
public class PacketListeners implements Listener {

    @EventMethod
    public void onEvent(PacketReceiveEvent event) {
        if (event.getPlayer() == null) {
            return;
        }

        PlayerData data = Nightfall.getInstance().getDataManager().getPlayerData(event.getPlayer().getUniqueId());
        Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());

        if (data != null) {
            switch (event.getType()) {
                case Packet.Client.POSITION:
                case Packet.Client.POSITION_LOOK:
                case Packet.Client.LOOK:
                case Packet.Client.LEGACY_POSITION:
                case Packet.Client.FLYING:
                case Packet.Client.LEGACY_POSITION_LOOK:
                case Packet.Client.LEGACY_LOOK: {
                    WrappedInFlyingPacket packet = new WrappedInFlyingPacket(event.getPacket(), player);

                    data.getMovementProcessor().update(data, packet);
                    break;
                }
            }

            hopper(event.getPacket(), event.getType(), event.getTimeStamp(), data);
        }
    }

    private void hopper(Object packet, String packetType, long timeStamp, PlayerData data) {
        data.getPacketChecks().getOrDefault(packetType, new ArrayList<>()).stream().filter(Check::isEnabled).forEach(check -> check.onPacket(packet, packetType, timeStamp));
    }
}
