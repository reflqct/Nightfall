package org.azaleamc.nightfall.checks;

import lombok.Getter;
import lombok.Setter;
import org.azaleamc.nightfall.checks.impl.movement.fly.Fly;
import org.azaleamc.nightfall.checks.impl.movement.liquidwalk.LiquidWalk;
import org.azaleamc.nightfall.checks.impl.movement.speed.Speed;
import org.azaleamc.nightfall.checks.impl.player.regen.Regen;
import org.azaleamc.nightfall.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 27/04/2019 at 18:09.
 */
@Getter
@Setter
public class CheckManager {

    private List<Check> checks;

    public CheckManager() {
        checks = loadChecks();
    }

    public List<Check> loadChecks() {
        List<Check> checks = new ArrayList<>();
        checks.add(new Regen("Regen"));
        checks.add(new Fly("Fly"));
        checks.add(new Speed("Speed"));
        checks.add(new LiquidWalk("LiquidWalk"));

        return checks;
    }

    public void loadChecksIntoData(PlayerData data) {
        List<Check> checks = loadChecks();

        data.getChecks().clear();

        checks.forEach(check -> check.setData(data));

        data.setChecks(checks);
    }

    public Check getCheck(String name) {
        return checks.stream().filter(check -> check.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
