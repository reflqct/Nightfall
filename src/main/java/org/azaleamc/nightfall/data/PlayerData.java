package org.azaleamc.nightfall.data;

import cc.funkemunky.api.utils.BoundingBox;
import lombok.Getter;
import lombok.Setter;
import org.azaleamc.nightfall.Nightfall;
import org.azaleamc.nightfall.checks.Check;
import org.azaleamc.nightfall.processors.MovementProcessor;
import org.azaleamc.nightfall.utils.BukkitEvents;
import org.azaleamc.nightfall.utils.Packets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by George on 27/04/2019 at 18:11.
 */
@Getter
@Setter
public class PlayerData {

    private UUID uuid;
    private Player player;
    private List<Check> checks = new ArrayList<>();
    private Map<String, List<Check>> packetChecks = new HashMap<>();
    private Map<Class, List<Check>> bukkitChecks = new HashMap<>();

    private BoundingBox boundingBox;

    private MovementProcessor movementProcessor;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);

        movementProcessor = new MovementProcessor();

        Nightfall.getInstance().getCheckManager().loadChecksIntoData(this);

        checks.stream().filter(check -> check.getClass().isAnnotationPresent(Packets.class)).forEach(check -> {
            Packets packets = check.getClass().getAnnotation(Packets.class);

            Arrays.stream(packets.packets()).forEach(packet -> {
                List<Check> checks = packetChecks.getOrDefault(packet, new ArrayList<>());

                checks.add(check);

                packetChecks.put(packet, checks);
            });
        });

        checks.stream().filter(check -> check.getClass().isAnnotationPresent(BukkitEvents.class)).forEach(check -> {
            BukkitEvents events = check.getClass().getAnnotation(BukkitEvents.class);

            Arrays.stream(events.events()).forEach(event -> {
                List<Check> checks = bukkitChecks.getOrDefault(event, new ArrayList<>());

                checks.add(check);

                bukkitChecks.put(event, checks);
            });
        });
    }
}
