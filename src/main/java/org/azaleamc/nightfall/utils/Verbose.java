package org.azaleamc.nightfall.utils;

import cc.funkemunky.api.utils.TickTimer;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by George on 27/04/2019 at 19:12.
 */
@Getter
@Setter
public class Verbose {

    public TickTimer lastFlag = new TickTimer(10);
    private int verbose = 0;

    public boolean flag(int amount, long reset) {
        if (lastFlag.hasNotPassed((int) reset / 50)) {
            lastFlag.reset();
            return (verbose++) > amount;
        }
        verbose = 0;
        lastFlag.reset();
        return false;
    }

    public void deduct() {
        verbose = verbose > 0 ? verbose - 1 : 0;
    }
}

