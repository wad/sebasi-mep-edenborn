package org.sebasi.mep.tool.datastructure.v1.util;

import org.sebasi.mep.tool.datastructure.v1.Ticker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tickers extends HelperHolder {

    long tickCounter;

    Map<TickPriority, Set<Ticker>> tickersByPriority;

    public Tickers(Helper helper) {
        super(helper);

        tickCounter = 0L;

        tickersByPriority = new HashMap<>(TickPriority.values().length);
        for (TickPriority tickPriority : TickPriority.values()) {
            tickersByPriority.put(tickPriority, new HashSet<>());
        }
    }

    public void registerTicker(
            TickPriority tickPriority,
            Ticker ticker) {
        tickersByPriority.get(tickPriority).add(ticker);
    }

    public void tick() {

        if (getHelper().shouldShowLogMessages()) {
            getHelper().getMessageDisplay().show("Tick " + tickCounter);
        }

        for (TickPriority tickPriority : TickPriority.values()) {
            Set<Ticker> tickers = tickersByPriority.get(tickPriority);
            for (Ticker ticker : tickers) {
                ticker.tick();
            }
        }

        tickCounter++;
    }
}
