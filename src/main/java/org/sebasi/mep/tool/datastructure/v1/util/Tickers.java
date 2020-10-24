package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.Ticker;

import java.util.HashSet;
import java.util.Set;

public class Tickers extends HelperHolder {

    // todo: Add tick priority groups, so order is deterministic.

    long tickCounter;
    Set<Ticker> tickers;

    public Tickers(Helper helper) {
        super(helper);
        tickCounter = 0L;
        tickers = new HashSet<>();
    }

    public void registerTicker(Ticker ticker) {
        tickers.add(ticker);
    }

    public void tick() {

        if (getHelper().shouldShowLogMessages()) {
            getHelper().getMessageDisplay().show("Tick " + tickCounter);
        }

        for (Ticker ticker : tickers) {
            ticker.tick();
        }

        tickCounter++;
    }
}
